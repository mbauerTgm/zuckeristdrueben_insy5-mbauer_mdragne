package com.mbauer_mdragne.vue_crud.Controllers;

import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
import com.mbauer_mdragne.vue_crud.DTOs.SampleAnalysisCount;
import com.mbauer_mdragne.vue_crud.Entities.*;
import com.mbauer_mdragne.vue_crud.Repositories.*;
import com.mbauer_mdragne.vue_crud.Services.EanValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
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
    public Page<BoxPos> getBoxPosNoAnalysis(
            AnalysisGlobalFilterDto filter,
            @PageableDefault(size = 20) Pageable pageable) {

        if (filter.getGlobalDateIn() != null && filter.getGlobalDateIn().getFrom() != null) {
             Specification<BoxPos> spec = BoxPosSpecifications.withGlobalDateFilter(filter);
             return boxPosRepo.findAll(spec, pageable);
        }

        return boxPosRepo.findBoxPosWithSampleButNoAnalysis(pageable);
    }

    @GetMapping("/boxpos-empty")
    public Page<BoxPos> getBoxPosEmpty(
            AnalysisGlobalFilterDto filter,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Specification<BoxPos> spec = (root, query, cb) -> 
            cb.or(cb.isNull(root.get("sId")), cb.equal(root.get("sId"), ""));
            
        Specification<BoxPos> dateSpec = BoxPosSpecifications.withGlobalDateFilter(filter);
        if (dateSpec != null) spec = spec.and(dateSpec);

        return boxPosRepo.findAll(spec, pageable);
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
    public Page<Analysis> getAnalysisNoBoxPos(
            AnalysisGlobalFilterDto filter,
            @PageableDefault(size = 20) Pageable pageable) {
        
        if (filter.getGlobalDateIn() != null && filter.getGlobalDateIn().getFrom() != null) {
            Specification<Analysis> spec = (root, query, cb) -> {
                return cb.not(cb.exists(
                    query.subquery(BoxPos.class).select(query.from(BoxPos.class).get("sId")) // Vereinfacht
                ));
            };
            Specification<Analysis> dateSpec = AnalysisSpecifications.withGlobalDateFilter(filter);
            return analysisRepo.findAll(spec.and(dateSpec), pageable); 
        }
        
        return analysisRepo.findAnalysisWithoutBoxPos(pageable);
    }

    @GetMapping("/analysis-zero-values")
    public Page<Analysis> getAnalysisZeroValues(
            AnalysisGlobalFilterDto filter,
            @PageableDefault(size = 20, sort = "aId", direction = Sort.Direction.DESC) Pageable pageable) {
        
        Specification<Analysis> zeroSpec = (root, query, cb) -> 
            cb.or(cb.equal(root.get("pol"), 0), cb.equal(root.get("nat"), 0), cb.equal(root.get("kal"), 0));

        Specification<Analysis> dateSpec = AnalysisSpecifications.withGlobalDateFilter(filter);
        if (dateSpec != null) zeroSpec = zeroSpec.and(dateSpec);

        return analysisRepo.findAll(zeroSpec, pageable);
    }

    @GetMapping("/analysis-missing-dates")
    public Page<Analysis> getAnalysisMissingDates(
            AnalysisGlobalFilterDto filter,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Specification<Analysis> missingSpec = (root, query, cb) -> 
            cb.or(cb.isNull(root.get("dateIn")), cb.isNull(root.get("dateOut")));

        Specification<Analysis> dateSpec = AnalysisSpecifications.withGlobalDateFilter(filter);
        if (dateSpec != null) missingSpec = missingSpec.and(dateSpec);

        return analysisRepo.findAll(missingSpec, pageable);
    }

    @GetMapping("/samples-multi-analysis")
    public Page<SampleAnalysisCount> getSamplesMultiAnalysis(@PageableDefault(size = 20) Pageable pageable) {
        return sampleRepo.findSamplesWithMultipleAnalyses(pageable);
    }

    @GetMapping("/samples-suspicious")
    public Page<Sample> getSuspiciousSamples(
            AnalysisGlobalFilterDto filter,
            @PageableDefault(size = 20) Pageable pageable) {
        
        if (filter.getGlobalDateIn() != null && filter.getGlobalDateIn().getFrom() != null) {
             Timestamp start = Timestamp.valueOf(filter.getGlobalDateIn().getFrom());
             Timestamp end = (filter.getGlobalDateIn().getTo() != null) 
                             ? Timestamp.valueOf(filter.getGlobalDateIn().getTo()) 
                             : new Timestamp(System.currentTimeMillis());
             
             return sampleRepo.findSuspiciousSampleIdsInTimeRange(start, end, pageable);
        }
        return sampleRepo.findSuspiciousSampleIds(pageable);
    }

    @GetMapping("/samples-bad-ean")
    public Page<Sample> getSamplesBadEan(
            AnalysisGlobalFilterDto filter,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Specification<Sample> dateSpec = SampleSpecifications.withGlobalDateFilter(filter);
        List<Sample> baseList = (dateSpec != null) ? sampleRepo.findAll(dateSpec) : sampleRepo.findAll();

        List<Sample> allBadEans = baseList.stream()
                .filter(s -> !eanService.isValidEan13(s.getSId()))
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allBadEans.size());
        if (start > allBadEans.size()) start = allBadEans.size();

        return new PageImpl<>(allBadEans.subList(start, end), pageable, allBadEans.size());
    }

    private Timestamp parseTimestamp(String dateStr, boolean isStart) {
        try {
            String cleanDate = dateStr.replace("T", " ");
            if (cleanDate.length() == 10) {
                cleanDate += isStart ? " 00:00:00" : " 23:59:59";
            } 
            else if (cleanDate.length() == 16) {
                cleanDate += ":00";
            }
            return Timestamp.valueOf(cleanDate);
        } catch (Exception e) {
            return Timestamp.from(java.time.OffsetDateTime.parse(dateStr).toInstant());
        }
    }
}