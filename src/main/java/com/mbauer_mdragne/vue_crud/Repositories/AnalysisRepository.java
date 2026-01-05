package com.mbauer_mdragne.vue_crud.Repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mbauer_mdragne.vue_crud.Entities.Analysis;

public interface AnalysisRepository extends JpaRepository<Analysis, Long>, JpaSpecificationExecutor<Analysis>{

    @Query("SELECT a FROM Analysis a WHERE a.aFlags LIKE 'F%' OR a.aFlags LIKE 'V%'")
    List<Analysis> findAllForResearcher();
}