package com.mbauer_mdragne.vue_crud.Controllers;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.mbauer_mdragne.vue_crud.Errors.BadRequestException;
import com.mbauer_mdragne.vue_crud.Errors.ResourceNotFoundException;
import com.mbauer_mdragne.vue_crud.Repositories.AnalysisRepository;
import com.mbauer_mdragne.vue_crud.Repositories.AnalysisSpecifications;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    @Autowired private AnalysisRepository analysisRepo;
    @Autowired private EntityManager em;

    // ------------------ Analysis ------------------

    @GetMapping
    public List<Analysis> getAllAnalysis(AnalysisGlobalFilterDto globalFilter) {
        Specification<Analysis> spec = AnalysisSpecifications.withGlobalDateFilter(globalFilter);
        
        if (isResearcher()) {
            Specification<Analysis> resSpec = AnalysisSpecifications.forResearcher();
            spec = (spec == null) ? resSpec : spec.and(resSpec);
        }
        
        return spec != null ? analysisRepo.findAll(spec) : analysisRepo.findAll();
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Analysis>> filterAnalysis(
            AnalysisFilterDto filterDto,
            AnalysisGlobalFilterDto globalFilter,
            @PageableDefault(size = 20, sort = "aId", direction = Sort.Direction.DESC) Pageable pageable) {
        
        Specification<Analysis> spec = AnalysisSpecifications.withDynamicFilter(filterDto);
        Specification<Analysis> globalSpec = AnalysisSpecifications.withGlobalDateFilter(globalFilter);

        if (globalSpec != null) {
            spec = (spec == null) ? globalSpec : spec.and(globalSpec);
        }

        if (isResearcher()) {
            spec = (spec == null) ? AnalysisSpecifications.forResearcher() : spec.and(AnalysisSpecifications.forResearcher());
        }
        
        Page<Analysis> result = analysisRepo.findAll(spec, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Analysis> getAnalysisById(@PathVariable Long id) {
        Analysis analysis = analysisRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Analysis not found with id=" + id));
        return ResponseEntity.ok(analysis);
    }


    @PostMapping
    public ResponseEntity<Analysis> createAnalysis(@RequestBody Analysis analysis) {
        if (analysis.getSId() == null) {
            throw new BadRequestException("sId darf nicht null sein");
        }
        Sample sample = em.createQuery("SELECT s FROM Sample s WHERE s.sId = :sId ORDER BY s.sStamp DESC", Sample.class)
                .setParameter("sId", analysis.getSId())
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Kein Sample gefunden für sId=" + analysis.getSId()));

        analysis.setSStamp(sample.getSStamp());
        Analysis saved = analysisRepo.save(analysis);
        return ResponseEntity.ok(saved);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Analysis> updateAnalysis(@PathVariable Long id, @RequestBody Analysis updatedAnalysis) {
        Analysis existing = analysisRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Analysis not found with id=" + id));

        Sample sample = em.createQuery("SELECT s FROM Sample s WHERE s.sId = :sId ORDER BY s.sStamp DESC", Sample.class)
                .setParameter("sId", updatedAnalysis.getSId())
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Kein Sample gefunden für sId=" + updatedAnalysis.getSId()));
        
        existing.setSId(sample.getSId());
        existing.setSStamp(sample.getSStamp());
        existing.setPol(updatedAnalysis.getPol());
        existing.setNat(updatedAnalysis.getNat());
        existing.setKal(updatedAnalysis.getKal());
        existing.setAn(updatedAnalysis.getAn());
        existing.setGlu(updatedAnalysis.getGlu());
        existing.setDry(updatedAnalysis.getDry());
        existing.setDateIn(updatedAnalysis.getDateIn());
        existing.setDateOut(updatedAnalysis.getDateOut());
        existing.setWeightMea(updatedAnalysis.getWeightMea());
        existing.setWeightNrm(updatedAnalysis.getWeightNrm());
        existing.setWeightCur(updatedAnalysis.getWeightCur());
        existing.setWeightDif(updatedAnalysis.getWeightDif());
        existing.setDensity(updatedAnalysis.getDensity());
        existing.setAFlags(updatedAnalysis.getAFlags());
        existing.setLane(updatedAnalysis.getLane());
        existing.setComment(updatedAnalysis.getComment());
        existing.setDateExported(updatedAnalysis.getDateExported());

        Analysis saved = analysisRepo.save(existing);
        return ResponseEntity.ok(saved);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnalysis(@PathVariable Long id) {
        if (!analysisRepo.existsById(id)) {
            throw new ResourceNotFoundException("Analysis not found with id=" + id);
        }
        analysisRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/export")
    public void exportAnalysisToCsv(
            AnalysisFilterDto searchDto, 
            AnalysisGlobalFilterDto globalFilter,
            HttpServletResponse response) throws IOException {
        
        Specification<Analysis> spec = AnalysisSpecifications.withDynamicFilter(searchDto);
        Specification<Analysis> globalSpec = AnalysisSpecifications.withGlobalDateFilter(globalFilter);
        
        if (globalSpec != null) {
            spec = (spec == null) ? globalSpec : spec.and(globalSpec);
        }
        
        if (isResearcher()) {
            spec = (spec == null) ? AnalysisSpecifications.forResearcher() : spec.and(AnalysisSpecifications.forResearcher());
        }
        
        List<Analysis> list = analysisRepo.findAll(spec);

        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=analysis_export.csv");
        
        try (PrintWriter writer = response.getWriter()) {
            writer.println("ID;SampleID;DateIn;Pol;Nat;Kal;Comment");
            for (Analysis a : list) {
                writer.println(String.format("%s;%s;%s;%s;%s;%s;%s",
                    a.getAId(),
                    a.getSId() != null ? a.getSId() : "",
                    a.getDateIn() != null ? a.getDateIn() : "",
                    a.getPol(),
                    a.getNat(),
                    a.getKal(),
                    a.getComment() != null ? a.getComment().replace(";", ",") : ""
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
