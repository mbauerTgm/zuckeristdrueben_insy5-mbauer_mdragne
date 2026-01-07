package com.mbauer_mdragne.vue_crud.Repositories;

import java.sql.Timestamp;
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

public interface SampleRepository extends JpaRepository<Sample, SampleId>, JpaSpecificationExecutor<Sample> {

    @Query("SELECT s FROM Sample s WHERE s.sFlags LIKE 'F%' OR s.sFlags LIKE 'V%'")
    List<Sample> findAllForResearcher();
    @Query("SELECT s FROM Sample s WHERE s.sFlags LIKE 'F%' OR s.sFlags LIKE 'V%'")
    Page<Sample> findAllForResearcher(Pageable pageable);

    @Query(value = "SELECT * FROM venlab.get_suspicious_samples_with_multiple_analysis(:start_date, :end_date)",
            countQuery = "SELECT count(*) FROM venlab.get_suspicious_samples_with_multiple_analysis(:start_date, :end_date)",
            nativeQuery = true)
    Page<SampleAnalysisCount> findSamplesWithMultipleAnalyses(@Param("start_date")LocalDate start_date,@Param("end_date")LocalDate end_date,Pageable pageable);

    @Query(value = "SELECT * FROM venlab.get_suspicious_ean13_samples(:start_date, :end_date)",
            countQuery = "SELECT count(*) FROM venlab.get_suspicious_ean13_samples(:start_date, :end_date)",
            nativeQuery = true)
    Page<Sample> findSuspiciousSampleIdsInTimeRange(@Param("start_date") Timestamp start, @Param("end_date") Timestamp end,Pageable pageable);

    @Query(value = "SELECT * FROM venlab.sample s WHERE length(s.s_id) != 13 OR s.s_id !~ '^[0-9]+$'", nativeQuery = true)
    Page<Sample> findSuspiciousSampleIds(Pageable pageable);
}