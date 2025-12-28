package com.mbauer_mdragne.vue_crud;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Sample, SampleId> {}