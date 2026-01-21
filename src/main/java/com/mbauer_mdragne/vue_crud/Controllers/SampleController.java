package com.mbauer_mdragne.vue_crud.Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
import com.mbauer_mdragne.vue_crud.Entities.Sample;
import com.mbauer_mdragne.vue_crud.Entities.SampleId;
import com.mbauer_mdragne.vue_crud.Errors.ResourceNotFoundException;
import com.mbauer_mdragne.vue_crud.Repositories.SampleRepository;
import com.mbauer_mdragne.vue_crud.Repositories.SampleSpecifications;
import com.mbauer_mdragne.vue_crud.DateUtils;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/samples")
public class SampleController {

    @Autowired private SampleRepository sampleRepo;

    @GetMapping
    @PreAuthorize("hasAnyRole('User', 'Researcher', 'Admin')")
    public List<Sample> getAllSamples(AnalysisGlobalFilterDto globalFilter) {
        Specification<Sample> spec = SampleSpecifications.withGlobalDateFilter(globalFilter);
        if (isResearcher()) {
             return (spec != null) ? sampleRepo.findAll(spec) : sampleRepo.findAllForResearcher();
        }
        return (spec != null) ? sampleRepo.findAll(spec) : sampleRepo.findAll();
    }
    
    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('User', 'Researcher', 'Admin')")
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

    @GetMapping("/{sId}/{sStamp}")
    @PreAuthorize("hasAnyRole('User', 'Researcher', 'Admin')")
    public ResponseEntity<Sample> getSampleById(@PathVariable String sId, @PathVariable String sStamp) {
        SampleId id = new SampleId(sId, DateUtils.parseAny(sStamp));
        Sample sample = sampleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sample not found: " + sId));
        return ResponseEntity.ok(sample);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('Researcher', 'Admin')")
    public ResponseEntity<Sample> createSample(@RequestBody Sample sample) {
        if (sample.getSStamp() == null) {
            sample.setSStamp(new Timestamp(System.currentTimeMillis()));
        }
        return ResponseEntity.ok(sampleRepo.save(sample));
    }

    @PutMapping("/{sId}/{sStamp}")
    @PreAuthorize("hasAnyRole('Researcher', 'Admin')")
    public ResponseEntity<Sample> updateSample(
        @PathVariable String sId,
        @PathVariable String sStamp,
        @RequestBody Sample updatedSample) {
        
        SampleId id = new SampleId(sId, DateUtils.parseAny(sStamp));
        Sample existing = sampleRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sample not found"));

        updatedSample.setSId(existing.getSId());
        updatedSample.setSStamp(existing.getSStamp());
        return ResponseEntity.ok(sampleRepo.save(updatedSample));
    }

    @DeleteMapping("/{sId}/{sStamp}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteSample(@PathVariable String sId, @PathVariable String sStamp) {
        SampleId id = new SampleId(sId, DateUtils.parseAny(sStamp));
        if (!sampleRepo.existsById(id)) {
            throw new ResourceNotFoundException("Sample not found");
        }
        sampleRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export")
    @PreAuthorize("hasAnyRole('User', 'Researcher', 'Admin')")
    public void exportSamplesToCsv(AnalysisGlobalFilterDto globalFilter, HttpServletResponse response) throws IOException {
        Specification<Sample> spec = SampleSpecifications.withGlobalDateFilter(globalFilter);
        List<Sample> list = (spec != null) ? sampleRepo.findAll(spec) : 
                           (isResearcher() ? sampleRepo.findAllForResearcher() : sampleRepo.findAll());

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
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_Researcher"));
    }
}