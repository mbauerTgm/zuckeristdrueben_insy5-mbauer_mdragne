package com.mbauer_mdragne.vue_crud.Repositories;

import java.time.LocalDate;
import java.util.List;
import com.mbauer_mdragne.vue_crud.DTOs.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.mbauer_mdragne.vue_crud.Entities.Sample;
import com.mbauer_mdragne.vue_crud.Entities.SampleId;
import com.mbauer_mdragne.vue_crud.Projections.SampleMultipleAnalysisView;
import com.mbauer_mdragne.vue_crud.Projections.SuspiciousEanSampleView;

public interface SampleRepository extends JpaRepository<Sample, SampleId>, JpaSpecificationExecutor<Sample> {
    @Query("SELECT s FROM Sample s WHERE s.sFlags LIKE 'F%' OR s.sFlags LIKE 'V%'")
    List<Sample> findAllForResearcher();
    @Query("SELECT s FROM Sample s WHERE s.sFlags LIKE 'F%' OR s.sFlags LIKE 'V%'")
    Page<Sample> findAllForResearcher(Pageable pageable);

    @Query(value = "SELECT * FROM venlab.get_suspicious_samples_with_multiple_analysis(:start_date, :end_date)",
            countQuery = "SELECT count(*) FROM venlab.get_suspicious_samples_with_multiple_analysis(:start_date, :end_date)",
            nativeQuery = true)
    Page<SampleMultipleAnalysisView> findSamplesWithMultipleAnalyses(@Param("start_date")LocalDate start_date,@Param("end_date")LocalDate end_date,Pageable pageable);

    @Query(value = "SELECT * FROM venlab.get_suspicious_ean13_samples(:start_date, :end_date)",
            countQuery = "SELECT count(*) FROM venlab.get_suspicious_ean13_samples(:start_date, :end_date)",
            nativeQuery = true)
    Page<SuspiciousEanSampleView> findSuspiciousSampleIdsInTimeRange(@Param("start_date") LocalDate start_date, @Param("end_date") LocalDate end_date,Pageable pageable);

    @Query(value = "SELECT * FROM venlab.sample s WHERE NOT venlab.check_ean13(s.s_id)",
            countQuery = "SELECT count(*) FROM venlab.sample s WHERE NOT venlab.check_ean13(s.s_id)",
           nativeQuery = true)
    Page<SuspiciousEanSampleView> findSamplesWithInvalidEan(Pageable pageable);

    @Query(value = "SELECT MIN(s.s_stamp)::date FROM venlab.sample s", nativeQuery = true)
    LocalDate findEarliestSampleDate();

    @Query(value = "SELECT MAX(s.s_stamp)::date FROM venlab.sample s", nativeQuery = true)
    LocalDate findLatestSampleDate();
    default Page<SuspiciousEanSampleView> findAllSuspiciousSamples(Pageable pageable) {
        LocalDate start = findEarliestSampleDate();
        LocalDate end = findLatestSampleDate(); 
        return findSuspiciousSampleIdsInTimeRange(start, end, pageable);
    }
}