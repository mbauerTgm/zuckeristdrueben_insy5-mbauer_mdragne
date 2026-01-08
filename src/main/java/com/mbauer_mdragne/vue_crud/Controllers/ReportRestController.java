package com.mbauer_mdragne.vue_crud.Controllers;

import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
import com.mbauer_mdragne.vue_crud.Projections.DayReportView;
import com.mbauer_mdragne.vue_crud.DTOs.SampleAnalysisCount;
import com.mbauer_mdragne.vue_crud.Entities.*;
import com.mbauer_mdragne.vue_crud.Repositories.*;
import com.mbauer_mdragne.vue_crud.DateUtils;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
public class ReportRestController {

    @Autowired private BoxPosRepository boxPosRepo;
    @Autowired private AnalysisRepository analysisRepo;
    @Autowired private SampleRepository sampleRepo;
    @Autowired private ReportRepository reportRepo;

    @GetMapping("/boxpos/with-sample-no-analysis")
    public ResponseEntity<Page<BoxPos>> getBoxPosWithSampleNoAnalysis(@PageableDefault(size = 20) Pageable pageable) {
        Page<BoxPos> result = boxPosRepo.findBoxPosWithoutAnalysis(pageable);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/boxpos/without-sample")
    public ResponseEntity<Page<BoxPos>> getBoxPosWithoutSample(@PageableDefault(size = 20) Pageable pageable) {
        Page<BoxPos> result = boxPosRepo.findBoxPosWithoutSample(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/samples/suspicious/by-date")
    public ResponseEntity<Page<Sample>> getSuspiciousSamplesByDate(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @PageableDefault(size = 20) Pageable pageable) {

        Page<Sample> result = sampleRepo.findSuspiciousSampleIdsInTimeRange(startDate, endDate, pageable);

        return ResponseEntity.ok(result);
    }
    @GetMapping("/analysis/without-boxpos")
    public ResponseEntity<Page<Analysis>> getAnalysesWithoutBoxPos(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Page<Analysis> result = analysisRepo.findAnalysisWithoutBoxPos(startDate, endDate, pageable);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/analysis/with-zero-values")
    public ResponseEntity<Page<Analysis>> getAnalysesWithZeroValues(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Page<Analysis> result = analysisRepo.findAnalysisWithNullValues(startDate, endDate, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/analysis/without-time")
    public ResponseEntity<Page<Analysis>> getAnalysesWithoutTime(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @PageableDefault(size = 20) Pageable pageable) {

        Page<Analysis> result = analysisRepo.findAnalysisWithMissingDates(startDate, endDate, pageable);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/samples/multiple-analyses")
    public ResponseEntity<Page<SampleAnalysisCount>> getSamplesWithMultipleAnalyses(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Page<SampleAnalysisCount> result = sampleRepo.findSamplesWithMultipleAnalyses(startDate, endDate, pageable);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/samples/suspicious")
    public ResponseEntity<Page<Sample>> getAllSuspiciousSamples(@PageableDefault(size = 20) Pageable pageable) {
        Page<Sample> result = sampleRepo.findAllSuspiciousSamples(pageable);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/samples/invalid-ean13")
    public ResponseEntity<Page<Sample>> getSamplesWithInvalidEan13(@PageableDefault(size = 20) Pageable pageable) {
        var list = sampleRepo.findSamplesWithInvalidEan();
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), list.size());
        Page<Sample> result = new org.springframework.data.domain.PageImpl<>(list.subList(start, end), pageable, list.size());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/daily-report")
    public List<DayReportView> getDailySummaryReport(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return reportRepo.getDayReports(startDate, endDate);
    }
}
