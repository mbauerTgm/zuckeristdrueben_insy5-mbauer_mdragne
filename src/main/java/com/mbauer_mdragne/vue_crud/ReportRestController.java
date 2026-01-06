package com.mbauer_mdragne.vue_crud;

import com.mbauer_mdragne.vue_crud.Entities.*;
import com.mbauer_mdragne.vue_crud.Repositories.*;
import com.mbauer_mdragne.vue_crud.DTOs.SampleAnalysisCount;
import com.mbauer_mdragne.vue_crud.Services.EanValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
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
    public Page<BoxPos> getBoxPosNoAnalysis(@PageableDefault(size = 20) Pageable pageable) {
        return boxPosRepo.findBoxPosWithSampleButNoAnalysis(pageable);
    }

    @GetMapping("/boxpos-empty")
    public Page<BoxPos> getBoxPosEmpty(@PageableDefault(size = 20) Pageable pageable) {
        return boxPosRepo.findBoxPosWithoutSample(pageable);
    }

    @GetMapping("/samples-suspicious-timerange")
    public Page<Sample> getSuspiciousSamplesInRange(
            @RequestParam String start, 
            @RequestParam String end,
            @PageableDefault(size = 20) Pageable pageable) {
        Timestamp startTs = parseTimestamp(start, true);
        Timestamp endTs = parseTimestamp(end, false);
        return sampleRepo.findSuspiciousSampleIdsInTimeRange(startTs, endTs, pageable);
    }

    @GetMapping("/analysis-no-boxpos")
    public Page<Analysis> getAnalysisNoBoxPos(@PageableDefault(size = 20) Pageable pageable) {
        return analysisRepo.findAnalysisWithoutBoxPos(pageable);
    }

    @GetMapping("/analysis-zero-values")
    public Page<Analysis> getAnalysisZeroValues(
            @PageableDefault(size = 20, sort = "aId", direction = Sort.Direction.DESC) Pageable pageable) {
        return analysisRepo.findAnalysisWithZeroValues(pageable);
    }

    @GetMapping("/analysis-missing-dates")
    public Page<Analysis> getAnalysisMissingDates(@PageableDefault(size = 20) Pageable pageable) {
        return analysisRepo.findAnalysisWithMissingDates(pageable);
    }

    @GetMapping("/samples-multi-analysis")
    public Page<SampleAnalysisCount> getSamplesMultiAnalysis(@PageableDefault(size = 20) Pageable pageable) {
        return sampleRepo.findSamplesWithMultipleAnalyses(pageable);
    }

    @GetMapping("/samples-suspicious")
    public Page<Sample> getSuspiciousSamples(@PageableDefault(size = 20) Pageable pageable) {
        return sampleRepo.findSuspiciousSampleIds(pageable);
    }

    @GetMapping("/samples-bad-ean")
    public Page<Sample> getSamplesBadEan(@PageableDefault(size = 20) Pageable pageable) {
        List<Sample> allBadEans = sampleRepo.findAll().stream()
                .filter(s -> !eanService.isValidEan13(s.getSId()))
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allBadEans.size());
        if (start > allBadEans.size()) start = allBadEans.size();

        return new PageImpl<>(allBadEans.subList(start, end), pageable, allBadEans.size());
    }

    private Timestamp parseTimestamp(String dateStr, boolean isStart) {
        try {
            // Versuche ISO-Format (von Vue .toISOString())
            return Timestamp.from(OffsetDateTime.parse(dateStr).toInstant());
        } catch (Exception e) {
            // Fallback für einfaches Datum yyyy-MM-dd
            String suffix = isStart ? " 00:00:00" : " 23:59:59";
            return Timestamp.valueOf(dateStr + suffix);
        }
    }
}