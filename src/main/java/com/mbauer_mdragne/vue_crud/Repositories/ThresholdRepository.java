package com.mbauer_mdragne.vue_crud.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mbauer_mdragne.vue_crud.Entities.Threshold;

public interface ThresholdRepository extends JpaRepository<Threshold, String>, JpaSpecificationExecutor<Threshold> {}