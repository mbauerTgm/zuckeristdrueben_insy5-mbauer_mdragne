package com.mbauer_mdragne.vue_crud.Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mbauer_mdragne.vue_crud.DTOs.AnalysisFilterDto;
import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
import com.mbauer_mdragne.vue_crud.Entities.Analysis;
import com.mbauer_mdragne.vue_crud.Entities.Sample;
import com.mbauer_mdragne.vue_crud.Entities.SampleId;
import com.mbauer_mdragne.vue_crud.Errors.BadRequestException;
import com.mbauer_mdragne.vue_crud.Errors.ResourceNotFoundException;
import com.mbauer_mdragne.vue_crud.Repositories.AnalysisRepository;
import com.mbauer_mdragne.vue_crud.Repositories.AnalysisSpecifications;
import com.mbauer_mdragne.vue_crud.Repositories.SampleRepository;
import com.mbauer_mdragne.vue_crud.Repositories.SampleSpecifications;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/samples")
public class SampleController {

    @Autowired private SampleRepository sampleRepo;

    // ------------------ Sample ------------------

    @GetMapping
    public List<Sample> getAllSamples(AnalysisGlobalFilterDto globalFilter) {
        Specification<Sample> spec = SampleSpecifications.withGlobalDateFilter(globalFilter);
        if (isResearcher()) {
             return (spec != null) ? sampleRepo.findAll(spec) : sampleRepo.findAllForResearcher();
        }
        return (spec != null) ? sampleRepo.findAll(spec) : sampleRepo.findAll();
    }
    
    @GetMapping("/filter")
    public ResponseEntity<Page<Sample>> filterSamples(
        AnalysisGlobalFilterDto globalFilter,
        @PageableDefault(size = 20, sort = "sId", direction = Sort.Direction.DESC) Pageable pageable) {
        
        Specification<Sample> spec = SampleSpecifications.withGlobalDateFilter(globalFilter);
        
        if (spec == null) {
            if (isResearcher()) return ResponseEntity.ok(sampleRepo.findAllForResearcher(pageable));
            else return ResponseEntity.ok(sampleRepo.findAll(pageable));
        }

        return ResponseEntity.ok(sampleRepo.findAll(spec, pageable));
    }

    @PostMapping
    public ResponseEntity<Sample> createSample(@RequestBody Sample sample) {
        if (sample.getSStamp() == null) {
            sample.setSStamp(new Timestamp(System.currentTimeMillis()));
        }
        Sample saved = sampleRepo.save(sample);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{sId}/{sStamp}")
    public ResponseEntity<Sample> updateSample(
        @PathVariable String sId,
        @PathVariable String sStamp,
        @RequestBody Sample updatedSample) {
        try {
            OffsetDateTime odt = OffsetDateTime.parse(sStamp);
            Timestamp ts = Timestamp.from(odt.toInstant());
            SampleId id = new SampleId(sId, ts);

            Sample existing = sampleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sample not found: sId=" + sId + ", sStamp=" + sStamp));

            updatedSample.setSId(existing.getSId());
            updatedSample.setSStamp(existing.getSStamp());
            Sample saved = sampleRepo.save(updatedSample);
            return ResponseEntity.ok(saved);
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Invalid timestamp: " + sStamp);
        }
    }

    @DeleteMapping("/{sId}/{sStamp}")
        public ResponseEntity<Void> deleteSample(@PathVariable String sId, @PathVariable String sStamp) {
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

    @GetMapping("/{sId}/{sStamp}")
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
    
    @GetMapping("/export")
    public void exportSamplesToCsv(AnalysisGlobalFilterDto globalFilter, HttpServletResponse response) throws IOException {
        Specification<Sample> spec = SampleSpecifications.withGlobalDateFilter(globalFilter);
        List<Sample> list;
    
        if (spec != null) {
             list = sampleRepo.findAll(spec);
        } else {
             list = isResearcher() ? sampleRepo.findAllForResearcher() : sampleRepo.findAll();
        }

        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=samples_export.csv");

        try (PrintWriter writer = response.getWriter()) {
            writer.println("SampleID;Timestamp;Name;WeightNet;Flags;Lane;Comment");
            for (Sample s : list) {
                writer.println(String.format("%s;%s;%s;%s;%s;%s;%s",
                    s.getSId() != null ? s.getSId() : "",
                    s.getSStamp() != null ? s.getSStamp().toString() : "",
                    s.getName() != null ? s.getName() : "",
                    s.getWeightNet() != null ? s.getWeightNet().toString() : "0",
                    s.getSFlags() != null ? s.getSFlags() : "",
                    s.getLane() != null ? s.getLane() : "",
                    s.getComment() != null ? s.getComment().replace(";", ",") : ""
                ));
            }
        }
    }

    private boolean isResearcher() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;
        
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_Researcher"));
    }
}
