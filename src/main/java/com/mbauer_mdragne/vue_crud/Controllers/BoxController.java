package com.mbauer_mdragne.vue_crud.Controllers;

import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
import com.mbauer_mdragne.vue_crud.Entities.Box;
import com.mbauer_mdragne.vue_crud.Errors.ResourceNotFoundException;
import com.mbauer_mdragne.vue_crud.Repositories.BoxRepository;
import com.mbauer_mdragne.vue_crud.Repositories.BoxSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boxes")
public class BoxController {

    @Autowired 
    private BoxRepository boxRepo;


    @GetMapping
    @PreAuthorize("hasAnyRole('User', 'Researcher', 'Admin')")
    public List<Box> getAllBoxes(AnalysisGlobalFilterDto globalFilter) {
        Specification<Box> spec = BoxSpecifications.withGlobalDateFilter(globalFilter);
        return (spec != null) ? boxRepo.findAll(spec) : boxRepo.findAll();
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('User', 'Researcher', 'Admin')")
    public ResponseEntity<Page<Box>> getAllBoxes(
            AnalysisGlobalFilterDto globalFilter,
            @PageableDefault(size = 20, sort = "bId", direction = Sort.Direction.DESC) Pageable pageable) {
        
        Specification<Box> spec = BoxSpecifications.withGlobalDateFilter(globalFilter);
        Page<Box> response = (spec != null) ? boxRepo.findAll(spec, pageable) : boxRepo.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{bId}")
    @PreAuthorize("hasAnyRole('User', 'Researcher', 'Admin')")
    public ResponseEntity<Box> getBoxById(@PathVariable String bId) {
        Box box = boxRepo.findById(bId)
                .orElseThrow(() -> new ResourceNotFoundException("Box not found with id=" + bId));
        return ResponseEntity.ok(box);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('Researcher', 'Admin')")
    public ResponseEntity<Box> createBox(@RequestBody Box box) {
        Box saved = boxRepo.save(box);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{bId}")
    @PreAuthorize("hasAnyRole('Researcher', 'Admin')")
    public ResponseEntity<Box> updateBox(@PathVariable String bId, @RequestBody Box updated) {
        Box existing = boxRepo.findById(bId)
                .orElseThrow(() -> new ResourceNotFoundException("Box not found with id=" + bId));
        updated.setBId(existing.getBId());
        
        Box saved = boxRepo.save(updated);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{bId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteBox(@PathVariable String bId) {
        if (!boxRepo.existsById(bId)) {
            throw new ResourceNotFoundException("Box not found with id=" + bId);
        }
        boxRepo.deleteById(bId);
        return ResponseEntity.noContent().build();
    }
}