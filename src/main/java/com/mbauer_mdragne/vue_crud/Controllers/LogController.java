package com.mbauer_mdragne.vue_crud.Controllers;

import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mbauer_mdragne.vue_crud.Entities.*;
import com.mbauer_mdragne.vue_crud.Errors.BadRequestException;
import com.mbauer_mdragne.vue_crud.Errors.ResourceNotFoundException;
import com.mbauer_mdragne.vue_crud.Repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.jpa.domain.Specification;
import com.mbauer_mdragne.vue_crud.DTOs.AnalysisFilterDto;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletResponse;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/logs")
public class LogController {
    @Autowired private LogRepository logRepo;
    @GetMapping
    public List<Log> getAllLogs(AnalysisGlobalFilterDto globalFilter) {
        Specification<Log> spec = LogSpecifications.withGlobalDateFilter(globalFilter);
        return (spec != null) ? logRepo.findAll(spec) : logRepo.findAll();
    }
    
    @GetMapping("/filter")
    public ResponseEntity<Page<Log>> getAllLogs(
            AnalysisGlobalFilterDto globalFilter,
            @PageableDefault(size = 20, sort = "logId", direction = Sort.Direction.DESC) Pageable pageable) {
        
        Specification<Log> spec = LogSpecifications.withGlobalDateFilter(globalFilter);
        Page<Log> response = (spec != null) ? logRepo.findAll(spec, pageable) : logRepo.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{logId}")
    public ResponseEntity<Log> getLogById(@PathVariable Long logId) {
        Log log = logRepo.findById(logId)
                .orElseThrow(() -> new ResourceNotFoundException("Log not found with id=" + logId));
        return ResponseEntity.ok(log);
    }
}