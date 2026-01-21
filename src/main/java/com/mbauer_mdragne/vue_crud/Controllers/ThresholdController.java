package com.mbauer_mdragne.vue_crud.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
import com.mbauer_mdragne.vue_crud.Entities.Threshold;
import com.mbauer_mdragne.vue_crud.Errors.ResourceNotFoundException;
import com.mbauer_mdragne.vue_crud.Repositories.ThresholdRepository;
import com.mbauer_mdragne.vue_crud.Repositories.ThresholdSpecifications;

@RestController
@RequestMapping("/api/thresholds")
public class ThresholdController {
    
    @Autowired 
    private ThresholdRepository thresholdRepo;

    @GetMapping
    @PreAuthorize("hasAnyRole('User', 'Researcher', 'Admin')")
    public List<Threshold> getAllThresholds(AnalysisGlobalFilterDto globalFilter) {
        Specification<Threshold> spec = ThresholdSpecifications.withGlobalDateFilter(globalFilter);
        return (spec != null) ? thresholdRepo.findAll(spec) : thresholdRepo.findAll();
    }
    
    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('User', 'Researcher', 'Admin')")
    public ResponseEntity<Page<Threshold>> getAllThresholds(
            AnalysisGlobalFilterDto globalFilter,
            @PageableDefault(size = 20, sort = "thId", direction = Sort.Direction.DESC) Pageable pageable) {
        
        Specification<Threshold> spec = ThresholdSpecifications.withGlobalDateFilter(globalFilter);
        Page<Threshold> response = (spec != null) ? thresholdRepo.findAll(spec, pageable) : thresholdRepo.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{thId}")
    @PreAuthorize("hasAnyRole('User', 'Researcher', 'Admin')")
    public ResponseEntity<Threshold> getThresholdById(@PathVariable String thId) {
        Threshold threshold = thresholdRepo.findById(thId)
                .orElseThrow(() -> new ResourceNotFoundException("Threshold not found with id=" + thId));
        return ResponseEntity.ok(threshold);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('Researcher', 'Admin')")
    public ResponseEntity<Threshold> createThreshold(@RequestBody Threshold threshold) {
        Threshold saved = thresholdRepo.save(threshold);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{thId}")
    @PreAuthorize("hasAnyRole('Researcher', 'Admin')")
    public ResponseEntity<Threshold> updateThreshold(@PathVariable String thId, @RequestBody Threshold updated) {
        if (!thresholdRepo.existsById(thId)) {
            throw new ResourceNotFoundException("Threshold not found with id=" + thId);
        }
        updated.setThId(thId);
        Threshold saved = thresholdRepo.save(updated);
        return ResponseEntity.ok(saved);
    }
}