package com.mbauer_mdragne.vue_crud.Controllers;

import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
import com.mbauer_mdragne.vue_crud.Entities.Log;
import com.mbauer_mdragne.vue_crud.Errors.ResourceNotFoundException;
import com.mbauer_mdragne.vue_crud.Repositories.LogRepository;
import com.mbauer_mdragne.vue_crud.Repositories.LogSpecifications;
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
@RequestMapping("/api/logs")
public class LogController {

    @Autowired 
    private LogRepository logRepo;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Log> getAllLogs(AnalysisGlobalFilterDto globalFilter) {
        Specification<Log> spec = LogSpecifications.withGlobalDateFilter(globalFilter);
        return (spec != null) ? logRepo.findAll(spec) : logRepo.findAll();
    }
    
    @GetMapping("/filter")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<Log>> getAllLogs(
            AnalysisGlobalFilterDto globalFilter,
            @PageableDefault(size = 20, sort = "logId", direction = Sort.Direction.DESC) Pageable pageable) {
        
        Specification<Log> spec = LogSpecifications.withGlobalDateFilter(globalFilter);
        Page<Log> response = (spec != null) ? logRepo.findAll(spec, pageable) : logRepo.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{logId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Log> getLogById(@PathVariable Long logId) {
        Log log = logRepo.findById(logId)
                .orElseThrow(() -> new ResourceNotFoundException("Log not found with id=" + logId));
        return ResponseEntity.ok(log);
    }
}