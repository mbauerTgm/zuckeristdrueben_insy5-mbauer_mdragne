// package com.mbauer_mdragne.vue_crud.Controllers;

// import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
// import org.springframework.security.core.Authentication;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.mbauer_mdragne.vue_crud.Entities.*;
// import com.mbauer_mdragne.vue_crud.Errors.BadRequestException;
// import com.mbauer_mdragne.vue_crud.Errors.ResourceNotFoundException;
// import com.mbauer_mdragne.vue_crud.Repositories.*;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
// import org.springframework.data.web.PageableDefault;
// import org.springframework.data.jpa.domain.Specification;
// import com.mbauer_mdragne.vue_crud.DTOs.AnalysisFilterDto;

// import jakarta.persistence.EntityManager;
// import jakarta.servlet.http.HttpServletResponse;

// import java.time.OffsetDateTime;
// import java.time.format.DateTimeParseException;
// import java.io.IOException;
// import java.io.PrintWriter;
// import java.sql.Timestamp;
// import java.util.List;

// import org.springframework.security.core.context.SecurityContextHolder;

// @RestController
// @RequestMapping("/legacy/api")
// public class ZuckerIstDruebenRestController {

//     @Autowired private AnalysisRepository analysisRepo;
//     @Autowired private SampleRepository sampleRepo;
//     @Autowired private BoxRepository boxRepo;
//     @Autowired private BoxPosRepository boxPosRepo;
//     @Autowired private LogRepository logRepo;
//     @Autowired private ThresholdRepository thresholdRepo;

//     @Autowired
//     private EntityManager em;

//     // ------------------ Analysis ------------------

//     @GetMapping("/analysis")
//     public List<Analysis> getAllAnalysis(AnalysisGlobalFilterDto globalFilter) {
//         Specification<Analysis> spec = AnalysisSpecifications.withGlobalDateFilter(globalFilter);
        
//         if (isResearcher()) {
//             Specification<Analysis> resSpec = AnalysisSpecifications.forResearcher();
//             spec = (spec == null) ? resSpec : spec.and(resSpec);
//         }
        
//         return spec != null ? analysisRepo.findAll(spec) : analysisRepo.findAll();
//     }

//     @GetMapping("/analysis/filter")
//     public ResponseEntity<Page<Analysis>> filterAnalysis(
//             AnalysisFilterDto filterDto,
//             AnalysisGlobalFilterDto globalFilter,
//             @PageableDefault(size = 20, sort = "aId", direction = Sort.Direction.DESC) Pageable pageable) {
        
//         Specification<Analysis> spec = AnalysisSpecifications.withDynamicFilter(filterDto);
//         Specification<Analysis> globalSpec = AnalysisSpecifications.withGlobalDateFilter(globalFilter);

//         if (globalSpec != null) {
//             spec = (spec == null) ? globalSpec : spec.and(globalSpec);
//         }

//         if (isResearcher()) {
//             spec = (spec == null) ? AnalysisSpecifications.forResearcher() : spec.and(AnalysisSpecifications.forResearcher());
//         }
        
//         Page<Analysis> result = analysisRepo.findAll(spec, pageable);
//         return ResponseEntity.ok(result);
//     }

//     @GetMapping("/analysis/{id}")
//     public ResponseEntity<Analysis> getAnalysisById(@PathVariable Long id) {
//         Analysis analysis = analysisRepo.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("Analysis not found with id=" + id));
//         return ResponseEntity.ok(analysis);
//     }


//     @PostMapping("/analysis")
//     public ResponseEntity<Analysis> createAnalysis(@RequestBody Analysis analysis) {
//         if (analysis.getSId() == null) {
//             throw new BadRequestException("sId darf nicht null sein");
//         }
//         Sample sample = em.createQuery("SELECT s FROM Sample s WHERE s.sId = :sId ORDER BY s.sStamp DESC", Sample.class)
//                 .setParameter("sId", analysis.getSId())
//                 .setMaxResults(1)
//                 .getResultStream()
//                 .findFirst()
//                 .orElseThrow(() -> new BadRequestException("Kein Sample gefunden für sId=" + analysis.getSId()));

//         analysis.setSStamp(sample.getSStamp());
//         Analysis saved = analysisRepo.save(analysis);
//         return ResponseEntity.ok(saved);
//     }


//     @PutMapping("/analysis/{id}")
//     public ResponseEntity<Analysis> updateAnalysis(@PathVariable Long id, @RequestBody Analysis updatedAnalysis) {
//         Analysis existing = analysisRepo.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("Analysis not found with id=" + id));

//         Sample sample = em.createQuery("SELECT s FROM Sample s WHERE s.sId = :sId ORDER BY s.sStamp DESC", Sample.class)
//                 .setParameter("sId", updatedAnalysis.getSId())
//                 .setMaxResults(1)
//                 .getResultStream()
//                 .findFirst()
//                 .orElseThrow(() -> new BadRequestException("Kein Sample gefunden für sId=" + updatedAnalysis.getSId()));
        
//         existing.setSId(sample.getSId());
//         existing.setSStamp(sample.getSStamp());
//         existing.setPol(updatedAnalysis.getPol());
//         existing.setNat(updatedAnalysis.getNat());
//         existing.setKal(updatedAnalysis.getKal());
//         existing.setAn(updatedAnalysis.getAn());
//         existing.setGlu(updatedAnalysis.getGlu());
//         existing.setDry(updatedAnalysis.getDry());
//         existing.setDateIn(updatedAnalysis.getDateIn());
//         existing.setDateOut(updatedAnalysis.getDateOut());
//         existing.setWeightMea(updatedAnalysis.getWeightMea());
//         existing.setWeightNrm(updatedAnalysis.getWeightNrm());
//         existing.setWeightCur(updatedAnalysis.getWeightCur());
//         existing.setWeightDif(updatedAnalysis.getWeightDif());
//         existing.setDensity(updatedAnalysis.getDensity());
//         existing.setAFlags(updatedAnalysis.getAFlags());
//         existing.setLane(updatedAnalysis.getLane());
//         existing.setComment(updatedAnalysis.getComment());
//         existing.setDateExported(updatedAnalysis.getDateExported());

//         Analysis saved = analysisRepo.save(existing);
//         return ResponseEntity.ok(saved);
//     }


//     @DeleteMapping("/analysis/{id}")
//     public ResponseEntity<Void> deleteAnalysis(@PathVariable Long id) {
//         if (!analysisRepo.existsById(id)) {
//             throw new ResourceNotFoundException("Analysis not found with id=" + id);
//         }
//         analysisRepo.deleteById(id);
//         return ResponseEntity.noContent().build();
//     }
    
//     @GetMapping("/analysis/export")
//     public void exportAnalysisToCsv(
//             AnalysisFilterDto searchDto, 
//             AnalysisGlobalFilterDto globalFilter,
//             HttpServletResponse response) throws IOException {
        
//         Specification<Analysis> spec = AnalysisSpecifications.withDynamicFilter(searchDto);
//         Specification<Analysis> globalSpec = AnalysisSpecifications.withGlobalDateFilter(globalFilter);
        
//         if (globalSpec != null) {
//             spec = (spec == null) ? globalSpec : spec.and(globalSpec);
//         }
        
//         if (isResearcher()) {
//             spec = (spec == null) ? AnalysisSpecifications.forResearcher() : spec.and(AnalysisSpecifications.forResearcher());
//         }
        
//         List<Analysis> list = analysisRepo.findAll(spec);

//         response.setContentType("text/csv");
//         response.setCharacterEncoding("UTF-8");
//         response.setHeader("Content-Disposition", "attachment; filename=analysis_export.csv");
        
//         try (PrintWriter writer = response.getWriter()) {
//             writer.println("ID;SampleID;DateIn;Pol;Nat;Kal;Comment");
//             for (Analysis a : list) {
//                 writer.println(String.format("%s;%s;%s;%s;%s;%s;%s",
//                     a.getAId(),
//                     a.getSId() != null ? a.getSId() : "",
//                     a.getDateIn() != null ? a.getDateIn() : "",
//                     a.getPol(),
//                     a.getNat(),
//                     a.getKal(),
//                     a.getComment() != null ? a.getComment().replace(";", ",") : ""
//                 ));
//             }
//         }
//     }

//     // ------------------ Sample ------------------

//     @GetMapping("/samples")
//     public List<Sample> getAllSamples(AnalysisGlobalFilterDto globalFilter) {
//         Specification<Sample> spec = SampleSpecifications.withGlobalDateFilter(globalFilter);
//         if (isResearcher()) {
//              // Da SampleSpec noch keine forResearcher hat, nutzen wir hier findAll wenn kein Filter, sonst spec
//              // Hinweis: Hier müsste idealerweise auch eine Spec für Researcher hin.
//              return (spec != null) ? sampleRepo.findAll(spec) : sampleRepo.findAllForResearcher();
//         }
//         return (spec != null) ? sampleRepo.findAll(spec) : sampleRepo.findAll();
//     }
    
//     @GetMapping("/samples/filter")
//     public ResponseEntity<Page<Sample>> filterSamples(
//         AnalysisGlobalFilterDto globalFilter,
//         @PageableDefault(size = 20, sort = "sId", direction = Sort.Direction.DESC) Pageable pageable) {
        
//         Specification<Sample> spec = SampleSpecifications.withGlobalDateFilter(globalFilter);
        
//         // Wenn kein Filter da ist, Fallback auf alte Logik, sonst Filter anwenden
//         if (spec == null) {
//             if (isResearcher()) return ResponseEntity.ok(sampleRepo.findAllForResearcher(pageable));
//             else return ResponseEntity.ok(sampleRepo.findAll(pageable));
//         }

//         // Mit Filter
//         return ResponseEntity.ok(sampleRepo.findAll(spec, pageable));
//     }

//     @PostMapping("/samples")
//     public ResponseEntity<Sample> createSample(@RequestBody Sample sample) {
//         if (sample.getSStamp() == null) {
//             sample.setSStamp(new Timestamp(System.currentTimeMillis()));
//         }
//         Sample saved = sampleRepo.save(sample);
//         return ResponseEntity.ok(saved);
//     }

//     @PutMapping("/samples/{sId}/{sStamp}")
//     public ResponseEntity<Sample> updateSample(
//         @PathVariable String sId,
//         @PathVariable String sStamp,
//         @RequestBody Sample updatedSample) {
//         try {
//             OffsetDateTime odt = OffsetDateTime.parse(sStamp);
//             Timestamp ts = Timestamp.from(odt.toInstant());
//             SampleId id = new SampleId(sId, ts);

//             Sample existing = sampleRepo.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("Sample not found: sId=" + sId + ", sStamp=" + sStamp));

//             updatedSample.setSId(existing.getSId());
//             updatedSample.setSStamp(existing.getSStamp());
//             Sample saved = sampleRepo.save(updatedSample);
//             return ResponseEntity.ok(saved);
//         } catch (DateTimeParseException e) {
//             throw new BadRequestException("Invalid timestamp: " + sStamp);
//         }
//     }

//     @DeleteMapping("/samples/{sId}/{sStamp}")
//         public ResponseEntity<Void> deleteSample(@PathVariable String sId, @PathVariable String sStamp) {
//         try {
//             OffsetDateTime odt = OffsetDateTime.parse(sStamp);
//             Timestamp ts = Timestamp.from(odt.toInstant());
//             SampleId id = new SampleId(sId, ts);

//             if (!sampleRepo.existsById(id)) {
//                 throw new ResourceNotFoundException("Sample not found: sId=" + sId + ", sStamp=" + sStamp);
//             }
//             sampleRepo.deleteById(id);
//             return ResponseEntity.noContent().build();
//         } catch (DateTimeParseException e) {
//             throw new BadRequestException("Invalid timestamp: " + sStamp);
//         }
//     }

//     @GetMapping("/samples/{sId}/{sStamp}")
//     public ResponseEntity<Sample> getSampleById(@PathVariable String sId, @PathVariable String sStamp) {
//         try {
//             Timestamp ts = new Timestamp(Long.parseLong(sStamp));
//             SampleId id = new SampleId(sId, ts);
//             Sample sample = sampleRepo.findById(id)
//                     .orElseThrow(() -> new ResourceNotFoundException("Sample not found: sId=" + sId + ", sStamp=" + sStamp));
//             return ResponseEntity.ok(sample);
//         } catch (NumberFormatException e) {
//             throw new BadRequestException("Invalid timestamp: " + sStamp);
//         }
//     }
    
//     @GetMapping("/samples/export")
//     public void exportSamplesToCsv(AnalysisGlobalFilterDto globalFilter, HttpServletResponse response) throws IOException {
//         Specification<Sample> spec = SampleSpecifications.withGlobalDateFilter(globalFilter);
//         List<Sample> list;
    
//         if (spec != null) {
//              list = sampleRepo.findAll(spec);
//         } else {
//              list = isResearcher() ? sampleRepo.findAllForResearcher() : sampleRepo.findAll();
//         }

//         response.setContentType("text/csv");
//         response.setCharacterEncoding("UTF-8");
//         response.setHeader("Content-Disposition", "attachment; filename=samples_export.csv");

//         try (PrintWriter writer = response.getWriter()) {
//             writer.println("SampleID;Timestamp;Name;WeightNet;Flags;Lane;Comment");
//             for (Sample s : list) {
//                 writer.println(String.format("%s;%s;%s;%s;%s;%s;%s",
//                     s.getSId() != null ? s.getSId() : "",
//                     s.getSStamp() != null ? s.getSStamp().toString() : "",
//                     s.getName() != null ? s.getName() : "",
//                     s.getWeightNet() != null ? s.getWeightNet().toString() : "0",
//                     s.getSFlags() != null ? s.getSFlags() : "",
//                     s.getLane() != null ? s.getLane() : "",
//                     s.getComment() != null ? s.getComment().replace(";", ",") : ""
//                 ));
//             }
//         }
//     }

//     // ------------------ Box ------------------

//     @GetMapping("/boxes")
//     public List<Box> getAllBoxes(AnalysisGlobalFilterDto globalFilter) {
//         Specification<Box> spec = BoxSpecifications.withGlobalDateFilter(globalFilter);
//         return (spec != null) ? boxRepo.findAll(spec) : boxRepo.findAll();
//     }

//     @GetMapping("/boxes/filter")
//     public ResponseEntity<Page<Box>> getAllBoxes(
//             AnalysisGlobalFilterDto globalFilter,
//             @PageableDefault(size = 20, sort = "bId", direction = Sort.Direction.DESC) Pageable pageable) {
        
//         Specification<Box> spec = BoxSpecifications.withGlobalDateFilter(globalFilter);
//         Page<Box> response = (spec != null) ? boxRepo.findAll(spec, pageable) : boxRepo.findAll(pageable);
//         return ResponseEntity.ok(response);
//     }

//     @GetMapping("/boxes/{bId}")
//     public ResponseEntity<Box> getBoxById(@PathVariable String bId) {
//         Box box = boxRepo.findById(bId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Box not found with id=" + bId));
//         return ResponseEntity.ok(box);
//     }

//     @PostMapping("/boxes")
//     public ResponseEntity<Box> createBox(@RequestBody Box box) {
//         Box saved = boxRepo.save(box);
//         return ResponseEntity.ok(saved);
//     }

//     @PutMapping("/boxes/{bId}")
//     public ResponseEntity<Box> updateBox(@PathVariable String bId, @RequestBody Box updated) {
//         Box existing = boxRepo.findById(bId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Box not found with id=" + bId));
//         updated.setBId(existing.getBId());
//         Box saved = boxRepo.save(updated);
//         return ResponseEntity.ok(saved);
//     }

//     @DeleteMapping("/boxes/{bId}")
//     public ResponseEntity<Void> deleteBox(@PathVariable String bId) {
//         if (!boxRepo.existsById(bId)) {
//             throw new ResourceNotFoundException("Box not found with id=" + bId);
//         }
//         boxRepo.deleteById(bId);
//         return ResponseEntity.noContent().build();
//     }

//     // ------------------ BoxPos ------------------

//     @GetMapping("/boxpos")
//     public List<BoxPos> getAllBoxPos(AnalysisGlobalFilterDto globalFilter) {
//         Specification<BoxPos> spec = BoxPosSpecifications.withGlobalDateFilter(globalFilter);
//         return (spec != null) ? boxPosRepo.findAll(spec) : boxPosRepo.findAll();
//     }

//     @GetMapping("/boxpos/filter")
//     public ResponseEntity<Page<BoxPos>> getAllBoxPos(
//             AnalysisGlobalFilterDto globalFilter,
//             @PageableDefault(size = 20, sort = "bId", direction = Sort.Direction.DESC) Pageable pageable) {
        
//         Specification<BoxPos> spec = BoxPosSpecifications.withGlobalDateFilter(globalFilter);
//         Page<BoxPos> response = (spec != null) ? boxPosRepo.findAll(spec, pageable) : boxPosRepo.findAll(pageable);
//         return ResponseEntity.ok(response);
//     }

//     @GetMapping("/boxpos/{bId}/{bposId}")
//     public ResponseEntity<BoxPos> getBoxPosById(@PathVariable String bId, @PathVariable Integer bposId) {
//         BoxPosId id = new BoxPosId(bId, bposId);
//         BoxPos boxPos = boxPosRepo.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("BoxPos not found: bId=" + bId + ", bposId=" + bposId));
//         return ResponseEntity.ok(boxPos);
//     }

//     @PostMapping("/boxpos")
//     public ResponseEntity<BoxPos> createBoxPos(@RequestBody BoxPos boxPos) {
//          Sample sample = em.createQuery("SELECT s FROM Sample s WHERE s.sId = :sId ORDER BY s.sStamp DESC", Sample.class)
//                 .setParameter("sId", boxPos.getSId())
//                 .setMaxResults(1)
//                 .getResultStream()
//                 .findFirst()
//                 .orElseThrow(() -> new BadRequestException("Kein Sample gefunden für sId=" + boxPos.getSId()));
//          boxPos.setSStamp(sample.getSStamp());
//          BoxPos saved = boxPosRepo.save(boxPos);
//          return ResponseEntity.ok(saved);
//     }

//     @PutMapping("/boxpos/{bId}/{bposId}")
//     public ResponseEntity<BoxPos> updateBoxPos(
//         @PathVariable String bId, @PathVariable Integer bposId, @RequestBody BoxPos updated) {
//         BoxPosId id = new BoxPosId(bId, bposId);
//         BoxPos existing = boxPosRepo.findById(id)
//             .orElseThrow(() -> new ResourceNotFoundException("BoxPos not found: bId=" + bId + ", bposId=" + bposId));

//         updated.setBId(existing.getBId());
//         updated.setBposId(existing.getBposId());

//         Sample sample = em.createQuery("SELECT s FROM Sample s WHERE s.sId = :sId ORDER BY s.sStamp DESC", Sample.class)
//             .setParameter("sId", updated.getSId())
//             .setMaxResults(1)
//             .getResultStream()
//             .findFirst()
//             .orElseThrow(() -> new BadRequestException("Kein Sample gefunden für sId=" + updated.getSId()));

//         updated.setSStamp(sample.getSStamp());
//         BoxPos saved = boxPosRepo.save(updated);
//         return ResponseEntity.ok(saved);
//     }


//     @DeleteMapping("/boxpos/{bId}/{bposId}")
//     public ResponseEntity<Void> deleteBoxPos(@PathVariable String bId, @PathVariable Integer bposId) {
//         BoxPosId id = new BoxPosId(bId, bposId);
//         if (!boxPosRepo.existsById(id)) {
//             throw new ResourceNotFoundException("BoxPos not found: bId=" + bId + ", bposId=" + bposId);
//         }
//         boxPosRepo.deleteById(id);
//         return ResponseEntity.noContent().build();
//     }

//     // ------------------ Log ------------------

//     @GetMapping("/logs")
//     public List<Log> getAllLogs(AnalysisGlobalFilterDto globalFilter) {
//         Specification<Log> spec = LogSpecifications.withGlobalDateFilter(globalFilter);
//         return (spec != null) ? logRepo.findAll(spec) : logRepo.findAll();
//     }
    
//     @GetMapping("/logs/filter")
//     public ResponseEntity<Page<Log>> getAllLogs(
//             AnalysisGlobalFilterDto globalFilter,
//             @PageableDefault(size = 20, sort = "logId", direction = Sort.Direction.DESC) Pageable pageable) {
        
//         Specification<Log> spec = LogSpecifications.withGlobalDateFilter(globalFilter);
//         Page<Log> response = (spec != null) ? logRepo.findAll(spec, pageable) : logRepo.findAll(pageable);
//         return ResponseEntity.ok(response);
//     }

//     @GetMapping("/logs/{logId}")
//     public ResponseEntity<Log> getLogById(@PathVariable Long logId) {
//         Log log = logRepo.findById(logId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Log not found with id=" + logId));
//         return ResponseEntity.ok(log);
//     }

//     // ------------------ Threshold ------------------

//     @GetMapping("/thresholds")
//     public List<Threshold> getAllThresholds(AnalysisGlobalFilterDto globalFilter) {
//         Specification<Threshold> spec = ThresholdSpecifications.withGlobalDateFilter(globalFilter);
//         return (spec != null) ? thresholdRepo.findAll(spec) : thresholdRepo.findAll();
//     }
    
//     @GetMapping("/thresholds/filter")
//     public ResponseEntity<Page<Threshold>> getAllThresholds(
//             AnalysisGlobalFilterDto globalFilter,
//             @PageableDefault(size = 20, sort = "thId", direction = Sort.Direction.DESC) Pageable pageable) {
        
//         Specification<Threshold> spec = ThresholdSpecifications.withGlobalDateFilter(globalFilter);
//         Page<Threshold> response = (spec != null) ? thresholdRepo.findAll(spec, pageable) : thresholdRepo.findAll(pageable);
//         return ResponseEntity.ok(response);
//     }

//     @GetMapping("/thresholds/{thId}")
//     public ResponseEntity<Threshold> getThresholdById(@PathVariable String thId) {
//         Threshold threshold = thresholdRepo.findById(thId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Threshold not found with id=" + thId));
//         return ResponseEntity.ok(threshold);
//     }

//     // ------------------ Util Methods ------------------

//     private boolean isResearcher() {
//         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         if (auth == null) return false;
        
//         return auth.getAuthorities().stream()
//                 .anyMatch(a -> a.getAuthority().equals("ROLE_Researcher"));
//     }
// }