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
import com.mbauer_mdragne.vue_crud.Entities.BoxPos;
import com.mbauer_mdragne.vue_crud.Entities.BoxPosId;
import com.mbauer_mdragne.vue_crud.Entities.Sample;
import com.mbauer_mdragne.vue_crud.Entities.SampleId;
import com.mbauer_mdragne.vue_crud.Errors.BadRequestException;
import com.mbauer_mdragne.vue_crud.Errors.ResourceNotFoundException;
import com.mbauer_mdragne.vue_crud.Repositories.AnalysisRepository;
import com.mbauer_mdragne.vue_crud.Repositories.AnalysisSpecifications;
import com.mbauer_mdragne.vue_crud.Repositories.BoxPosRepository;
import com.mbauer_mdragne.vue_crud.Repositories.BoxPosSpecifications;
import com.mbauer_mdragne.vue_crud.Repositories.SampleRepository;
import com.mbauer_mdragne.vue_crud.Repositories.SampleSpecifications;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/boxpos")
public class BoxPosController {

    @Autowired private BoxPosRepository boxPosRepo;
    @Autowired private EntityManager em;

    // ------------------ BoxPos ------------------

    @GetMapping
    public List<BoxPos> getAllBoxPos(AnalysisGlobalFilterDto globalFilter) {
        Specification<BoxPos> spec = BoxPosSpecifications.withGlobalDateFilter(globalFilter);
        return (spec != null) ? boxPosRepo.findAll(spec) : boxPosRepo.findAll();
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<BoxPos>> getAllBoxPos(
            AnalysisGlobalFilterDto globalFilter,
            @PageableDefault(size = 20, sort = "bId", direction = Sort.Direction.DESC) Pageable pageable) {
        
        Specification<BoxPos> spec = BoxPosSpecifications.withGlobalDateFilter(globalFilter);
        Page<BoxPos> response = (spec != null) ? boxPosRepo.findAll(spec, pageable) : boxPosRepo.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{bId}/{bposId}")
    public ResponseEntity<BoxPos> getBoxPosById(@PathVariable String bId, @PathVariable Integer bposId) {
        BoxPosId id = new BoxPosId(bId, bposId);
        BoxPos boxPos = boxPosRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BoxPos not found: bId=" + bId + ", bposId=" + bposId));
        return ResponseEntity.ok(boxPos);
    }

    @PostMapping
    public ResponseEntity<BoxPos> createBoxPos(@RequestBody BoxPos boxPos) {
         Sample sample = em.createQuery("SELECT s FROM Sample s WHERE s.sId = :sId ORDER BY s.sStamp DESC", Sample.class)
                .setParameter("sId", boxPos.getSId())
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Kein Sample gefunden für sId=" + boxPos.getSId()));
         boxPos.setSStamp(sample.getSStamp());
         BoxPos saved = boxPosRepo.save(boxPos);
         return ResponseEntity.ok(saved);
    }

    @PutMapping("/{bId}/{bposId}")
    public ResponseEntity<BoxPos> updateBoxPos(
        @PathVariable String bId, @PathVariable Integer bposId, @RequestBody BoxPos updated) {
        BoxPosId id = new BoxPosId(bId, bposId);
        BoxPos existing = boxPosRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("BoxPos not found: bId=" + bId + ", bposId=" + bposId));

        updated.setBId(existing.getBId());
        updated.setBposId(existing.getBposId());

        Sample sample = em.createQuery("SELECT s FROM Sample s WHERE s.sId = :sId ORDER BY s.sStamp DESC", Sample.class)
            .setParameter("sId", updated.getSId())
            .setMaxResults(1)
            .getResultStream()
            .findFirst()
            .orElseThrow(() -> new BadRequestException("Kein Sample gefunden für sId=" + updated.getSId()));

        updated.setSStamp(sample.getSStamp());
        BoxPos saved = boxPosRepo.save(updated);
        return ResponseEntity.ok(saved);
    }


    @DeleteMapping("/{bId}/{bposId}")
    public ResponseEntity<Void> deleteBoxPos(@PathVariable String bId, @PathVariable Integer bposId) {
        BoxPosId id = new BoxPosId(bId, bposId);
        if (!boxPosRepo.existsById(id)) {
            throw new ResourceNotFoundException("BoxPos not found: bId=" + bId + ", bposId=" + bposId);
        }
        boxPosRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}
