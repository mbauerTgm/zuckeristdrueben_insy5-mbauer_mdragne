package com.mbauer_mdragne.vue_crud.Repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mbauer_mdragne.vue_crud.Entities.Analysis;

public interface AnalysisRepository extends JpaRepository<Analysis, Long>, JpaSpecificationExecutor<Analysis> {

    @Query("SELECT a FROM Analysis a WHERE a.aFlags LIKE 'F%' OR a.aFlags LIKE 'V%'")
    Page<Analysis> findAllForResearcher(Pageable pageable);

    @Query(value = "SELECT * FROM venlab.get_suspicious_analysis_without_boxpos(:start_date, :end_date)",
            countQuery = "SELECT count(*) FROM venlab.get_suspicious_analysis_without_boxpos(:start_date, :end_date)",
            nativeQuery = true)
    Page<Analysis> findAnalysisWithoutBoxPos(@Param("start_date")LocalDate start_date,@Param("end_date")LocalDate end_date,Pageable pageable);

    @Query(value = "SELECT * FROM venlab.get_suspicious_analysis_with_null_values(:start_date, :end_date)",
            countQuery = "SELECT count(*) FROM venlab.get_suspicious_analysis_with_null_values(:start_date, :end_date)",
            nativeQuery = true)
    Page<Analysis> findAnalysisWithNullValues(@Param("start_date")LocalDate start_date,@Param("end_date")LocalDate end_date,Pageable pageable);

    @Query(value = "SELECT * FROM venlab.get_suspicious_analysis_without_time(:start_date, :end_date)",
            countQuery = "SELECT count(*) FROM venlab.get_suspicious_analysis_without_time(:start_date, :end_date)",
            nativeQuery = true)
    Page<Analysis> findAnalysisWithMissingDates(@Param("start_date")LocalDate start_date,@Param("end_date")LocalDate end_date, Pageable pageable);
}