package com.mbauer_mdragne.vue_crud;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mbauer_mdragne.vue_crud.Entities.Analysis;
import com.mbauer_mdragne.vue_crud.Entities.Box;
import com.mbauer_mdragne.vue_crud.Entities.BoxPos;
import com.mbauer_mdragne.vue_crud.Entities.BoxPosId;
import com.mbauer_mdragne.vue_crud.Entities.Log;
import com.mbauer_mdragne.vue_crud.Entities.Sample;
import com.mbauer_mdragne.vue_crud.Entities.SampleId;
import com.mbauer_mdragne.vue_crud.Entities.Threshold;
import com.mbauer_mdragne.vue_crud.Errors.BadRequestException;
import com.mbauer_mdragne.vue_crud.Errors.ResourceNotFoundException;
import com.mbauer_mdragne.vue_crud.Repositories.AnalysisRepository;
import com.mbauer_mdragne.vue_crud.Repositories.BoxPosRepository;
import com.mbauer_mdragne.vue_crud.Repositories.BoxRepository;
import com.mbauer_mdragne.vue_crud.Repositories.LogRepository;
import com.mbauer_mdragne.vue_crud.Repositories.SampleRepository;
import com.mbauer_mdragne.vue_crud.Repositories.ThresholdRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.jpa.domain.Specification;
import com.mbauer_mdragne.vue_crud.DTOs.AnalysisFilterDto;
import com.mbauer_mdragne.vue_crud.Repositories.AnalysisSpecifications;

import jakarta.persistence.EntityManager;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;


import java.sql.Timestamp;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api")
public class ZuckerIstDruebenRestController {

    @Autowired private AnalysisRepository analysisRepo;
    @Autowired private SampleRepository sampleRepo;
    @Autowired private BoxRepository boxRepo;
    @Autowired private BoxPosRepository boxPosRepo;
    @Autowired private LogRepository logRepo;
    @Autowired private ThresholdRepository thresholdRepo;

    @Autowired
    private EntityManager em;

    // ------------------ Analysis ------------------

    @GetMapping("/analysis")
    public List<Analysis> getAllAnalysis() {
        if (isResearcher()) {
            return analysisRepo.findAllForResearcher();
        }
        return analysisRepo.findAll();
    }

    @GetMapping("/analysis/filter")
    public ResponseEntity<Page<Analysis>> filterAnalysis( AnalysisFilterDto searchDto, @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Specification<Analysis> spec = AnalysisSpecifications.withDynamicFilter(searchDto);

        if (isResearcher()) {
            spec = spec.and(AnalysisSpecifications.forResearcher());
        }
        
        //Suchen: Gibt immer eine Page zurück, niemals alle Daten auf einmal
        Page<Analysis> result = analysisRepo.findAll(spec, pageable);
        
        return ResponseEntity.ok(result);
    }

    @GetMapping("/analysis/{id}")
    public ResponseEntity<Analysis> getAnalysisById(@PathVariable Long id) {
        Analysis analysis = analysisRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Analysis not found with id=" + id));
        return ResponseEntity.ok(analysis);
    }


    @PostMapping("/analysis")
    public ResponseEntity<Analysis> createAnalysis(@RequestBody Analysis analysis) {

        if (analysis.getS_id() == null) {
            throw new BadRequestException("s_id darf nicht null sein");
        }

        Sample sample = em.createQuery(
                        "SELECT s FROM Sample s WHERE s.s_id = :sid ORDER BY s.s_stamp DESC",
                        Sample.class
                )
                .setParameter("sid", analysis.getS_id())
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElseThrow(() ->
                        new BadRequestException("Kein Sample gefunden für s_id=" + analysis.getS_id())
                );

        analysis.setS_stamp(sample.getS_stamp());

        Analysis saved = analysisRepo.save(analysis);
        System.out.println("NatPost: " + analysis.getNat());
        return ResponseEntity.ok(saved);
    }


    @PutMapping("/analysis/{id}")
    public ResponseEntity<Analysis> updateAnalysis(@PathVariable Long id, @RequestBody Analysis updatedAnalysis) {
        Analysis existing = analysisRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Analysis not found with id=" + id));

        Sample sample = em.createQuery(
                        "SELECT s FROM Sample s WHERE s.s_id = :sid ORDER BY s.s_stamp DESC",
                        Sample.class
                )
                .setParameter("sid", updatedAnalysis.getS_id())
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Kein Sample gefunden für s_id=" + updatedAnalysis.getS_id()));
        existing.setS_id(sample.getS_id());
        existing.setS_stamp(sample.getS_stamp());
        existing.setPol(updatedAnalysis.getPol());
        existing.setNat(updatedAnalysis.getNat());
        existing.setKal(updatedAnalysis.getKal());
        existing.setAn(updatedAnalysis.getAn());
        existing.setGlu(updatedAnalysis.getGlu());
        existing.setDry(updatedAnalysis.getDry());
        existing.setDate_in(updatedAnalysis.getDate_in());
        existing.setDate_out(updatedAnalysis.getDate_out());
        existing.setWeight_mea(updatedAnalysis.getWeight_mea());
        existing.setWeight_nrm(updatedAnalysis.getWeight_nrm());
        existing.setWeight_cur(updatedAnalysis.getWeight_cur());
        existing.setWeight_dif(updatedAnalysis.getWeight_dif());
        existing.setDensity(updatedAnalysis.getDensity());
        existing.setA_flags(updatedAnalysis.getA_flags());
        existing.setLane(updatedAnalysis.getLane());
        existing.setComment(updatedAnalysis.getComment());
        existing.setDate_exported(updatedAnalysis.getDate_exported());

        Analysis saved = analysisRepo.save(existing);
        return ResponseEntity.ok(saved);
    }


    @DeleteMapping("/analysis/{id}")
    public ResponseEntity<Void> deleteAnalysis(@PathVariable Long id) {
        if (!analysisRepo.existsById(id)) {
            throw new ResourceNotFoundException("Analysis not found with id=" + id);
        }
        analysisRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ------------------ Sample ------------------

    @GetMapping("/samples")
    public List<Sample> getAllSamples() {
        if (isResearcher()) {
            return sampleRepo.findAllForResearcher();
        }
        return sampleRepo.findAll();
    }

    @PostMapping("/samples")
    public ResponseEntity<Sample> createSample(@RequestBody Sample sample) {
        if (sample.getS_stamp() == null) {
            sample.setS_stamp(new Timestamp(System.currentTimeMillis()));
        }
        Sample saved = sampleRepo.save(sample);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/samples/{sId}/{sStamp}")
    public ResponseEntity<Sample> updateSample(
        @PathVariable String sId,
        @PathVariable String sStamp,
        @RequestBody Sample updatedSample
        ) {
        try {
            OffsetDateTime odt = OffsetDateTime.parse(sStamp);
            Timestamp ts = Timestamp.from(odt.toInstant());

            SampleId id = new SampleId(sId, ts);

            Sample existing = sampleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Sample not found: sId=" + sId + ", sStamp=" + sStamp));

            updatedSample.setS_id(existing.getS_id());
            updatedSample.setS_stamp(existing.getS_stamp());

            Sample saved = sampleRepo.save(updatedSample);
            return ResponseEntity.ok(saved);

        } catch (DateTimeParseException e) {
            throw new BadRequestException("Invalid timestamp: " + sStamp);
        }
    }

    @DeleteMapping("/samples/{sId}/{sStamp}")
        public ResponseEntity<Void> deleteSample(
            @PathVariable String sId,
            @PathVariable String sStamp
        ) {
        try {
            OffsetDateTime odt = OffsetDateTime.parse(sStamp);
            Timestamp ts = Timestamp.from(odt.toInstant());

            SampleId id = new SampleId(sId, ts);

            if (!sampleRepo.existsById(id)) {
                throw new ResourceNotFoundException("Sample not found: sId=" + sId + ", sStamp=" + sStamp);
            }

            sampleRepo.deleteById(id);
            return ResponseEntity.noContent().build();

        } catch (DateTimeParseException e) {
            throw new BadRequestException("Invalid timestamp: " + sStamp);
        }
    }


    @GetMapping("/samples/{sId}/{sStamp}")
    public ResponseEntity<Sample> getSampleById(@PathVariable String sId, @PathVariable String sStamp) {
        try {
            Timestamp ts = new Timestamp(Long.parseLong(sStamp));
            SampleId id = new SampleId(sId, ts);

            Sample sample = sampleRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Sample not found: sId=" + sId + ", sStamp=" + sStamp));
            return ResponseEntity.ok(sample);

        } catch (NumberFormatException e) {
            throw new BadRequestException("Invalid timestamp: " + sStamp);
        }
    }

    // ------------------ Box ------------------

    @GetMapping("/boxes")
    public List<Box> getAllBoxes() {
        return boxRepo.findAll();
    }

    @GetMapping("/boxes/{bId}")
    public ResponseEntity<Box> getBoxById(@PathVariable String bId) {
        Box box = boxRepo.findById(bId)
                .orElseThrow(() -> new ResourceNotFoundException("Box not found with id=" + bId));
        return ResponseEntity.ok(box);
    }

    @PostMapping("/boxes")
    public ResponseEntity<Box> createBox(@RequestBody Box box) {
        Box saved = boxRepo.save(box);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/boxes/{bId}")
    public ResponseEntity<Box> updateBox(@PathVariable String bId, @RequestBody Box updated) {
        Box existing = boxRepo.findById(bId)
                .orElseThrow(() -> new ResourceNotFoundException("Box not found with id=" + bId));
        updated.setB_id(existing.getB_id());
        Box saved = boxRepo.save(updated);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/boxes/{bId}")
    public ResponseEntity<Void> deleteBox(@PathVariable String bId) {
        if (!boxRepo.existsById(bId)) {
            throw new ResourceNotFoundException("Box not found with id=" + bId);
        }
        boxRepo.deleteById(bId);
        return ResponseEntity.noContent().build();
    }

    // ------------------ BoxPos ------------------

    @GetMapping("/boxpos")
    public List<BoxPos> getAllBoxPos() {
        return boxPosRepo.findAll();
    }

    @GetMapping("/boxpos/{bId}/{bposId}")
    public ResponseEntity<BoxPos> getBoxPosById(@PathVariable String bId, @PathVariable Integer bposId) {
        BoxPosId id = new BoxPosId(bId, bposId);
        BoxPos boxPos = boxPosRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BoxPos not found: bId=" + bId + ", bposId=" + bposId));
        return ResponseEntity.ok(boxPos);
    }

    @PostMapping("/boxpos")
    public ResponseEntity<BoxPos> createBoxPos(@RequestBody BoxPos boxPos) {
         Sample sample = em.createQuery(
                        "SELECT s FROM Sample s WHERE s.s_id = :sid ORDER BY s.s_stamp DESC",
                        Sample.class
                )
                .setParameter("sid", boxPos.getS_id())
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Kein Sample gefunden für s_id=" + boxPos.getS_id()));
                boxPos.setS_stamp(sample.getS_stamp());
        BoxPos saved = boxPosRepo.save(boxPos);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/boxpos/{bId}/{bposId}")
    public ResponseEntity<BoxPos> updateBoxPos(
        @PathVariable String bId,
        @PathVariable Integer bposId,
        @RequestBody BoxPos updated
    ) {
    BoxPosId id = new BoxPosId(bId, bposId);

    BoxPos existing = boxPosRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                    "BoxPos not found: bId=" + bId + ", bposId=" + bposId));

    updated.setB_id(existing.getB_id());
    updated.setBpos_id(existing.getBpos_id());

    Sample sample = em.createQuery(
            "SELECT s FROM Sample s WHERE s.s_id = :sid ORDER BY s.s_stamp DESC",
            Sample.class
    )
    .setParameter("sid", updated.getS_id())
    .setMaxResults(1)
    .getResultStream()
    .findFirst()
    .orElseThrow(() ->
            new BadRequestException("Kein Sample gefunden für s_id=" + updated.getS_id())
    );

    updated.setS_stamp(sample.getS_stamp());

    BoxPos saved = boxPosRepo.save(updated);
    return ResponseEntity.ok(saved);
}


    @DeleteMapping("/boxpos/{bId}/{bposId}")
    public ResponseEntity<Void> deleteBoxPos(
            @PathVariable String bId,
            @PathVariable Integer bposId
    ) {
        BoxPosId id = new BoxPosId(bId, bposId);
        if (!boxPosRepo.existsById(id)) {
            throw new ResourceNotFoundException("BoxPos not found: bId=" + bId + ", bposId=" + bposId);
        }
        boxPosRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ------------------ Log ------------------

    @GetMapping("/logs")
    public List<Log> getAllLogs() {
        return logRepo.findAll();
    }

    @GetMapping("/logs/{logId}")
    public ResponseEntity<Log> getLogById(@PathVariable Long logId) {
        Log log = logRepo.findById(logId)
                .orElseThrow(() -> new ResourceNotFoundException("Log not found with id=" + logId));
        return ResponseEntity.ok(log);
    }

    // ------------------ Threshold ------------------

    @GetMapping("/thresholds")
    public List<Threshold> getAllThresholds() {
        return thresholdRepo.findAll();
    }

    @GetMapping("/thresholds/{thId}")
    public ResponseEntity<Threshold> getThresholdById(@PathVariable String thId) {
        Threshold threshold = thresholdRepo.findById(thId)
                .orElseThrow(() -> new ResourceNotFoundException("Threshold not found with id=" + thId));
        return ResponseEntity.ok(threshold);
    }

    // ------------------ Util Methods ------------------

    private boolean isResearcher() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;
        
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_Researcher"));
    }
}