package com.mbauer_mdragne.vue_crud.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mbauer_mdragne.vue_crud.Entities.Analysis;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {}