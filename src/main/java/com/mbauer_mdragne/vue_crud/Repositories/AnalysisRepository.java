package com.mbauer_mdragne.vue_crud.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mbauer_mdragne.vue_crud.Entities.Analysis;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {

    @Query("SELECT a FROM Analysis a WHERE a.a_flags LIKE 'F%' OR a.a_flags LIKE 'V%'")
    List<Analysis> findAllForResearcher();
}