package com.mbauer_mdragne.vue_crud.Repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mbauer_mdragne.vue_crud.Entities.Analysis;
import com.mbauer_mdragne.vue_crud.Projections.AnalysisWithNullValuesView;
import com.mbauer_mdragne.vue_crud.Projections.AnalysisWithoutBoxposView;
import com.mbauer_mdragne.vue_crud.Projections.AnalysisWithoutTimeView;

public interface AnalysisRepository extends JpaRepository<Analysis, Long>, JpaSpecificationExecutor<Analysis> {

    @Query("SELECT a FROM Analysis a WHERE a.aFlags LIKE 'F%' OR a.aFlags LIKE 'V%'")
    List<Analysis> findAllForResearcher();

    @Query(value = "SELECT * FROM venlab.get_suspicious_analysis_without_boxpos(:start_date, :end_date)", nativeQuery = true)
    List<AnalysisWithoutBoxposView> findAnalysisWithoutBoxPos(@Param("start_date")LocalDate start_date,@Param("end_date")LocalDate end_date);

    @Query(value = "SELECT * FROM venlab.get_suspicious_analysis_with_null_values(:start_date, :end_date)", nativeQuery = true)
    List<AnalysisWithNullValuesView> findAnalysisWithNullValues(@Param("start_date")LocalDate start_date,@Param("end_date")LocalDate end_date);

    @Query(value = "SELECT * FROM venlab.get_suspicious_analysis_without_time(:start_date, :end_date)", nativeQuery = true)
    List<AnalysisWithoutTimeView> findAnalysisWithMissingDates(@Param("start_date")LocalDate start_date,@Param("end_date")LocalDate end_date);
}