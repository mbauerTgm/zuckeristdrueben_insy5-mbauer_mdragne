package com.mbauer_mdragne.vue_crud.Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.mbauer_mdragne.vue_crud.DTOs.AnalysisFilterDto;
import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
import com.mbauer_mdragne.vue_crud.Entities.Analysis;
import com.mbauer_mdragne.vue_crud.Entities.Sample;
import com.mbauer_mdragne.vue_crud.Errors.BadRequestException;
import com.mbauer_mdragne.vue_crud.Errors.ResourceNotFoundException;
import com.mbauer_mdragne.vue_crud.Repositories.AnalysisRepository;
import com.mbauer_mdragne.vue_crud.Repositories.AnalysisSpecifications;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    @Autowired private AnalysisRepository analysisRepo;
    @Autowired private EntityManager em;

    @GetMapping
    public List<Analysis> getAllAnalysis(AnalysisGlobalFilterDto globalFilter) {
        Specification<Analysis> spec = AnalysisSpecifications.withGlobalDateFilter(globalFilter);
        
        if (isResearcher()) {
            spec = (spec == null) ? AnalysisSpecifications.forResearcher() : spec.and(AnalysisSpecifications.forResearcher());
        }
        
        return spec != null ? analysisRepo.findAll(spec) : analysisRepo.findAll();
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Analysis>> filterAnalysis(
        AnalysisFilterDto filterDto,
        AnalysisGlobalFilterDto globalFilter,
        @PageableDefault(size = 20, sort = "aId", direction = Sort.Direction.DESC) Pageable pageable) {
    
        boolean researcher = isResearcher();
        
        // 1. Dynamischen Filter laden (jetzt mit researcher flag)
        Specification<Analysis> spec = AnalysisSpecifications.withDynamicFilter(filterDto, researcher);
        
        // 2. Globalen Datumsfilter
        Specification<Analysis> globalSpec = AnalysisSpecifications.withGlobalDateFilter(globalFilter);
        if (globalSpec != null) {
            spec = (spec == null) ? globalSpec : spec.and(globalSpec);
        }

        // 3. Researcher-Pflichtfilter (F% oder V%)
        if (researcher) {
            spec = (spec == null) ? AnalysisSpecifications.forResearcher() : spec.and(AnalysisSpecifications.forResearcher());
        }
        
        return ResponseEntity.ok(analysisRepo.findAll(spec, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Analysis> getAnalysisById(@PathVariable Long id) {
        return analysisRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Analysis not found with id=" + id));
    }

    @PostMapping
    public ResponseEntity<Analysis> createAnalysis(@RequestBody Analysis analysis) {
        if (analysis.getSId() == null) {
            throw new BadRequestException("sId darf nicht null sein");
        }
        
        Sample sample = findLatestSample(analysis.getSId());
        analysis.setSStamp(sample.getSStamp());
        
        return ResponseEntity.ok(analysisRepo.save(analysis));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Analysis> updateAnalysis(@PathVariable Long id, @RequestBody Analysis updated) {
        Analysis existing = analysisRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Analysis not found with id=" + id));

        Sample sample = findLatestSample(updated.getSId());
        
        existing.setSId(sample.getSId());
        existing.setSStamp(sample.getSStamp());
        existing.setPol(updated.getPol());
        existing.setNat(updated.getNat());
        existing.setKal(updated.getKal());
        existing.setAn(updated.getAn());
        existing.setGlu(updated.getGlu());
        existing.setDry(updated.getDry());
        existing.setDateIn(updated.getDateIn());
        existing.setDateOut(updated.getDateOut());
        existing.setWeightMea(updated.getWeightMea());
        existing.setDensity(updated.getDensity());
        existing.setAFlags(updated.getAFlags());
        existing.setComment(updated.getComment());

        return ResponseEntity.ok(analysisRepo.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnalysis(@PathVariable Long id) {
        if (!analysisRepo.existsById(id)) throw new ResourceNotFoundException("Not found");
        analysisRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export")
    public void exportAnalysisToCsv(
            AnalysisFilterDto searchDto, 
            AnalysisGlobalFilterDto globalFilter,
            @RequestParam(value = "columns", required = false) List<String> columns,
            HttpServletResponse response) throws IOException {
        
        boolean researcher = isResearcher();
        
        // 1. Spezifikation wie bisher
        Specification<Analysis> spec = AnalysisSpecifications.withDynamicFilter(searchDto, researcher);
        Specification<Analysis> globalSpec = AnalysisSpecifications.withGlobalDateFilter(globalFilter);
        if (globalSpec != null) spec = (spec == null) ? globalSpec : spec.and(globalSpec);
        if (researcher) {
            spec = (spec == null) ? AnalysisSpecifications.forResearcher() : spec.and(AnalysisSpecifications.forResearcher());
        }

        List<Analysis> list = analysisRepo.findAll(spec);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (columns == null || columns.isEmpty()) {
            columns = List.of("aId", "sId", "dateIn", "pol", "nat", "kal", "comment");
        }

        // HTTP Header setzen
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=analysis_export.csv");
        
        try (PrintWriter writer = response.getWriter()) {
            writer.write('\ufeff'); 

            // Spaltenüberschriften schreiben
            writer.println(String.join(";", columns));
            for (Analysis a : list) {
                List<String> row = new ArrayList<>();
                for (String col : columns) {
                    String val = getFieldValue(a, col, sdf);
                    
                    if (val == null) val = "";
                    val = val.replace("\"", "\"\""); 
                    row.add("\"" + val + "\"");
                }
                writer.println(String.join(";", row));
            }
            writer.flush();
        }
    }

    private String getFieldValue(Analysis a, String col, SimpleDateFormat sdf) {
        Object val = null;
        switch (col) {
            case "aId": val = a.getAId(); break;
            case "sId": val = a.getSId(); break;
            case "dateIn": val = a.getDateIn() != null ? sdf.format(a.getDateIn()) : ""; break;
            case "dateOut": val = a.getDateOut() != null ? sdf.format(a.getDateOut()) : ""; break;
            case "pol": val = a.getPol(); break;
            case "nat": val = a.getNat(); break;
            case "kal": val = a.getKal(); break;
            case "an": val = a.getAn(); break;
            case "glu": val = a.getGlu(); break;
            case "dry": val = a.getDry(); break;
            case "aFlags": val = a.getAFlags(); break;
            case "comment": val = a.getComment() != null ? a.getComment().replace(";", ",") : ""; break;
            default: val = "";
        }
        return val != null ? val.toString() : "";
    }

    private Sample findLatestSample(String sId) {
        return em.createQuery("SELECT s FROM Sample s WHERE s.sId = :sId ORDER BY s.sStamp DESC", Sample.class)
                .setParameter("sId", sId)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Kein Sample gefunden für sId=" + sId));
    }

    private boolean isResearcher() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_Researcher"));
    }
}