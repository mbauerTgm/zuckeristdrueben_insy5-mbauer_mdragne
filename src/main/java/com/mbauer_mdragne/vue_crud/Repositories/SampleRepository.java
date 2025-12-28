package com.mbauer_mdragne.vue_crud.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mbauer_mdragne.vue_crud.Entities.Sample;
import com.mbauer_mdragne.vue_crud.Entities.SampleId;

public interface SampleRepository extends JpaRepository<Sample, SampleId> {}