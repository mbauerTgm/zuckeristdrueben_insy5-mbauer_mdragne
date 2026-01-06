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

import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
import com.mbauer_mdragne.vue_crud.Entities.*;
import com.mbauer_mdragne.vue_crud.Errors.BadRequestException;
import com.mbauer_mdragne.vue_crud.Errors.ResourceNotFoundException;
import com.mbauer_mdragne.vue_crud.Repositories.ThresholdRepository;
import com.mbauer_mdragne.vue_crud.Repositories.ThresholdSpecifications;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/thresholds")
public class ThresholdController {
    
@Autowired private ThresholdRepository thresholdRepo;

@GetMapping
    public List<Threshold> getAllThresholds(AnalysisGlobalFilterDto globalFilter) {
        Specification<Threshold> spec = ThresholdSpecifications.withGlobalDateFilter(globalFilter);
        return (spec != null) ? thresholdRepo.findAll(spec) : thresholdRepo.findAll();
    }
    
    @GetMapping("/filter")
    public ResponseEntity<Page<Threshold>> getAllThresholds(
            AnalysisGlobalFilterDto globalFilter,
            @PageableDefault(size = 20, sort = "thId", direction = Sort.Direction.DESC) Pageable pageable) {
        
        Specification<Threshold> spec = ThresholdSpecifications.withGlobalDateFilter(globalFilter);
        Page<Threshold> response = (spec != null) ? thresholdRepo.findAll(spec, pageable) : thresholdRepo.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{thId}")
    public ResponseEntity<Threshold> getThresholdById(@PathVariable String thId) {
        Threshold threshold = thresholdRepo.findById(thId)
                .orElseThrow(() -> new ResourceNotFoundException("Threshold not found with id=" + thId));
        return ResponseEntity.ok(threshold);
    }
}