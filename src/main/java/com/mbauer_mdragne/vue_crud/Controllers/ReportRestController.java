package com.mbauer_mdragne.vue_crud.Controllers;

import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
import com.mbauer_mdragne.vue_crud.Projections.AnalysisWithNullValuesView;
import com.mbauer_mdragne.vue_crud.Projections.AnalysisWithoutBoxposView;
import com.mbauer_mdragne.vue_crud.Projections.AnalysisWithoutTimeView;
import com.mbauer_mdragne.vue_crud.Projections.BoxPosWithoutTableView;
import com.mbauer_mdragne.vue_crud.Projections.DayReportView;
import com.mbauer_mdragne.vue_crud.Projections.SampleMultipleAnalysisView;
import com.mbauer_mdragne.vue_crud.Projections.SuspiciousEanSampleView;
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
    public ResponseEntity<Page<BoxPosWithoutTableView>> getBoxPosWithSampleNoAnalysis(@PageableDefault(size = 20) Pageable pageable) {
        Page<BoxPosWithoutTableView> result = boxPosRepo.findBoxPosWithoutAnalysis(pageable);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/boxpos/without-sample")
    public ResponseEntity<Page<BoxPosWithoutTableView>> getBoxPosWithoutSample(@PageableDefault(size = 20) Pageable pageable) {
        Page<BoxPosWithoutTableView> result = boxPosRepo.findBoxPosWithoutSample(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/samples/suspicious/by-date")
    public ResponseEntity<Page<SuspiciousEanSampleView>> getSuspiciousSamplesByDate(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @PageableDefault(size = 20) Pageable pageable) {

        Page<SuspiciousEanSampleView> result = sampleRepo.findSuspiciousSampleIdsInTimeRange(startDate, endDate, pageable);

        return ResponseEntity.ok(result);
    }
    @GetMapping("/analysis/without-boxpos")
    public ResponseEntity<Page<AnalysisWithoutBoxposView>> getAnalysesWithoutBoxPos(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Page<AnalysisWithoutBoxposView> result = analysisRepo.findAnalysisWithoutBoxPos(startDate, endDate, pageable);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/analysis/with-zero-values")
    public ResponseEntity<Page<AnalysisWithNullValuesView>> getAnalysesWithZeroValues(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Page<AnalysisWithNullValuesView> result = analysisRepo.findAnalysisWithNullValues(startDate, endDate, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/analysis/without-time")
    public ResponseEntity<Page<AnalysisWithoutTimeView>> getAnalysesWithoutTime(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @PageableDefault(size = 20) Pageable pageable) {

        Page<AnalysisWithoutTimeView> result = analysisRepo.findAnalysisWithMissingDates(startDate, endDate, pageable);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/samples/multiple-analyses")
    public ResponseEntity<Page<SampleMultipleAnalysisView>> getSamplesWithMultipleAnalyses(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Page<SampleMultipleAnalysisView> result = sampleRepo.findSamplesWithMultipleAnalyses(startDate, endDate, pageable);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/samples/suspicious")
    public ResponseEntity<Page<SuspiciousEanSampleView>> getAllSuspiciousSamples(@PageableDefault(size = 20) Pageable pageable) {
        Page<SuspiciousEanSampleView> result = sampleRepo.findAllSuspiciousSamples(pageable);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/samples/invalid-ean13")
    public ResponseEntity<Page<SuspiciousEanSampleView>> getSamplesWithInvalidEan13(@PageableDefault(size = 20) Pageable pageable) {
        
        Page<SuspiciousEanSampleView> result = sampleRepo.findSamplesWithInvalidEan(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/daily-report")
    public List<DayReportView> getDailySummaryReport(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return reportRepo.getDayReports(startDate, endDate);
    }
}
