package com.mbauer_mdragne.vue_crud.Repositories;

import java.sql.Timestamp;
import java.util.List;
import com.mbauer_mdragne.vue_crud.DTOs.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mbauer_mdragne.vue_crud.Entities.Sample;
import com.mbauer_mdragne.vue_crud.Entities.SampleId;

public interface SampleRepository extends JpaRepository<Sample, SampleId> {

    @Query("SELECT s FROM Sample s WHERE s.sFlags LIKE 'F%' OR s.sFlags LIKE 'V%'")
    List<Sample> findAllForResearcher();
    @Query("SELECT s FROM Sample s WHERE s.sFlags LIKE 'F%' OR s.sFlags LIKE 'V%'")
    Page<Sample> findAllForResearcher(Pageable pageable);

    //Proben haben mehr als 1 Analyse (Gruppierung)
    @Query("SELECT a.sId as sId, COUNT(a) as count FROM Analysis a GROUP BY a.sId HAVING COUNT(a) > 1")
    List<SampleAnalysisCount> findSamplesWithMultipleAnalyses();

    //Verdächtige Probenummern (Falsche Länge oder keine Zahl) im Zeitraum
    @Query(value = "SELECT * FROM venlab.sample s WHERE (length(s.s_id) != 13 OR s.s_id !~ '^[0-9]+$') AND s.s_stamp BETWEEN :start AND :end", nativeQuery = true)
    List<Sample> findSuspiciousSampleIdsInTimeRange(@Param("start") Timestamp start, @Param("end") Timestamp end);

    //Verdächtige Probenummern (Generell)
    @Query(value = "SELECT * FROM venlab.sample s WHERE length(s.s_id) != 13 OR s.s_id !~ '^[0-9]+$'", nativeQuery = true)
    List<Sample> findSuspiciousSampleIds();
}