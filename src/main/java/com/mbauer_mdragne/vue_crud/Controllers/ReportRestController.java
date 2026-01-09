package com.mbauer_mdragne.vue_crud.Controllers;

import com.mbauer_mdragne.vue_crud.Projections.*;
import com.mbauer_mdragne.vue_crud.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportRestController {

    @Autowired private BoxPosRepository boxPosRepo;
    @Autowired private AnalysisRepository analysisRepo;
    @Autowired private SampleRepository sampleRepo;
    @Autowired private ReportRepository reportRepo;

    @GetMapping("/boxpos/with-sample-no-analysis")
    public ResponseEntity<List<BoxPosWithoutTableView>> getBoxPosWithSampleNoAnalysis() {
        List<BoxPosWithoutTableView> result = boxPosRepo.findBoxPosWithoutAnalysis();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/boxpos/without-sample")
    public ResponseEntity<List<BoxPosWithoutTableView>> getBoxPosWithoutSample() {
        List<BoxPosWithoutTableView> result = boxPosRepo.findBoxPosWithoutSample();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/samples/suspicious/by-date")
    public ResponseEntity<List<SuspiciousEanSampleView>> getSuspiciousSamplesByDate(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<SuspiciousEanSampleView> result = sampleRepo.findSuspiciousSampleIdsInTimeRange(startDate, endDate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/analysis/without-boxpos")
    public ResponseEntity<List<AnalysisWithoutBoxposView>> getAnalysesWithoutBoxPos(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<AnalysisWithoutBoxposView> result = analysisRepo.findAnalysisWithoutBoxPos(startDate, endDate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/analysis/with-zero-values")
    public ResponseEntity<List<AnalysisWithNullValuesView>> getAnalysesWithZeroValues(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<AnalysisWithNullValuesView> result = analysisRepo.findAnalysisWithNullValues(startDate, endDate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/analysis/without-time")
    public ResponseEntity<List<AnalysisWithoutTimeView>> getAnalysesWithoutTime(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<AnalysisWithoutTimeView> result = analysisRepo.findAnalysisWithMissingDates(startDate, endDate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/samples/multiple-analyses")
    public ResponseEntity<List<SampleMultipleAnalysisView>> getSamplesWithMultipleAnalyses(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<SampleMultipleAnalysisView> result = sampleRepo.findSamplesWithMultipleAnalyses(startDate, endDate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/samples/suspicious")
    public ResponseEntity<List<SuspiciousEanSampleView>> getAllSuspiciousSamples() {
        List<SuspiciousEanSampleView> result = sampleRepo.findAllSuspiciousSamples();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/samples/invalid-ean13")
    public ResponseEntity<List<SuspiciousEanSampleView>> getSamplesWithInvalidEan13() {
        List<SuspiciousEanSampleView> result = sampleRepo.findSamplesWithInvalidEan();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/daily-report")
    public ResponseEntity<List<DayReportView>> getDailySummaryReport(
            @RequestParam LocalDate startDate, 
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(reportRepo.getDayReports(startDate, endDate));
    }
}