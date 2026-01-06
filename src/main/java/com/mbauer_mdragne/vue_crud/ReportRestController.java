package com.mbauer_mdragne.vue_crud;

import com.mbauer_mdragne.vue_crud.Entities.*;
import com.mbauer_mdragne.vue_crud.Repositories.*;
import com.mbauer_mdragne.vue_crud.DTOs.SampleAnalysisCount;
import com.mbauer_mdragne.vue_crud.Services.EanValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
public class ReportRestController {

    @Autowired private BoxPosRepository boxPosRepo;
    @Autowired private AnalysisRepository analysisRepo;
    @Autowired private SampleRepository sampleRepo;
    @Autowired private EanValidatorService eanService;

    @GetMapping("/boxpos-no-analysis")
    public List<BoxPos> getBoxPosNoAnalysis() {
        return boxPosRepo.findBoxPosWithSampleButNoAnalysis();
    }
    @GetMapping("/boxpos-empty")
    public List<BoxPos> getBoxPosEmpty() {
        return boxPosRepo.findBoxPosWithoutSample();
    }

    @GetMapping("/samples-suspicious-timerange")
    public List<Sample> getSuspiciousSamplesInRange(@RequestParam String start, @RequestParam String end) {
        try {
            return sampleRepo.findSuspiciousSampleIdsInTimeRange(
                Timestamp.valueOf(start), Timestamp.valueOf(end)
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Datum muss Format yyyy-mm-dd hh:mm:ss haben");
        }
    }

    @GetMapping("/analysis-no-boxpos")
    public List<Analysis> getAnalysisNoBoxPos() {
        return analysisRepo.findAnalysisWithoutBoxPos();
    }

    @GetMapping("/analysis-zero-values")
    public List<Analysis> getAnalysisZeroValues() {
        return analysisRepo.findAnalysisWithZeroValues();
    }

    @GetMapping("/analysis-missing-dates")
    public List<Analysis> getAnalysisMissingDates() {
        return analysisRepo.findAnalysisWithMissingDates();
    }

    @GetMapping("/samples-multi-analysis")
    public List<SampleAnalysisCount> getSamplesMultiAnalysis() {
        return sampleRepo.findSamplesWithMultipleAnalyses();
    }

    @GetMapping("/samples-suspicious")
    public List<Sample> getSuspiciousSamples() {
        return sampleRepo.findSuspiciousSampleIds();
    }

    @GetMapping("/samples-bad-ean")
    public List<Sample> getSamplesBadEan() {
        return sampleRepo.findAll().stream()
                .filter(s -> !eanService.isValidEan13(s.getSId()))
                .collect(Collectors.toList());
    }
}