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
import com.mbauer_mdragne.vue_crud.Entities.BoxPos;
import com.mbauer_mdragne.vue_crud.Entities.BoxPosId;
import com.mbauer_mdragne.vue_crud.Errors.BadRequestException;
import com.mbauer_mdragne.vue_crud.Errors.ResourceNotFoundException;
import com.mbauer_mdragne.vue_crud.Repositories.BoxPosRepository;
import com.mbauer_mdragne.vue_crud.Repositories.BoxPosSpecifications;

import jakarta.persistence.EntityManager;

@RestController
@RequestMapping("/api/boxpos")
public class BoxPosController {

    @Autowired private BoxPosRepository boxPosRepo;
    @Autowired private EntityManager em;

    @GetMapping
    public List<BoxPos> getAllBoxPos(AnalysisGlobalFilterDto globalFilter) {
        Specification<BoxPos> spec = BoxPosSpecifications.withGlobalDateFilter(globalFilter);
        return (spec != null) ? boxPosRepo.findAll(spec) : boxPosRepo.findAll();
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('User', 'Researcher', 'Admin')")
    public ResponseEntity<Page<BoxPos>> getAllBoxPos(
            AnalysisGlobalFilterDto globalFilter,
            @PageableDefault(size = 20, sort = "bId", direction = Sort.Direction.DESC) Pageable pageable) {
        
        Specification<BoxPos> spec = BoxPosSpecifications.withGlobalDateFilter(globalFilter);
        Page<BoxPos> response = (spec != null) ? boxPosRepo.findAll(spec, pageable) : boxPosRepo.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{bId}/{bposId}")
    @PreAuthorize("hasAnyRole('User', 'Researcher', 'Admin')")
    public ResponseEntity<BoxPos> getBoxPosById(@PathVariable String bId, @PathVariable Integer bposId) {
        BoxPosId id = new BoxPosId(bId, bposId);
        BoxPos boxPos = boxPosRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BoxPos not found"));
        return ResponseEntity.ok(boxPos);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('Researcher', 'Admin')")
    public ResponseEntity<BoxPos> createBoxPos(@RequestBody BoxPos boxPos) {
        if (boxPos.getSId() == null) throw new BadRequestException("sId missing");
        
        boxPos.setSStamp(findLatestSampleStamp(boxPos.getSId()));
        return ResponseEntity.ok(boxPosRepo.save(boxPos));
    }

    @PutMapping("/{bId}/{bposId}")
    @PreAuthorize("hasAnyRole('Researcher', 'Admin')")
    public ResponseEntity<BoxPos> updateBoxPos(
            @PathVariable String bId, 
            @PathVariable Integer bposId, 
            @RequestBody BoxPos updated) {
        
        BoxPosId id = new BoxPosId(bId, bposId);
        BoxPos existing = boxPosRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("BoxPos not found"));

        updated.setBId(existing.getBId());
        updated.setBposId(existing.getBposId());

        if (updated.getSId() != null) {
            updated.setSStamp(findLatestSampleStamp(updated.getSId()));
        }

        return ResponseEntity.ok(boxPosRepo.save(updated));
    }

    @DeleteMapping("/{bId}/{bposId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteBoxPos(@PathVariable String bId, @PathVariable Integer bposId) {
        BoxPosId id = new BoxPosId(bId, bposId);
        if (!boxPosRepo.existsById(id)) throw new ResourceNotFoundException("Not found");
        boxPosRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private java.sql.Timestamp findLatestSampleStamp(String sId) {
        return em.createQuery("SELECT s.sStamp FROM Sample s WHERE s.sId = :sId ORDER BY s.sStamp DESC", java.sql.Timestamp.class)
                .setParameter("sId", sId)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Sample not found: " + sId));
    }
}