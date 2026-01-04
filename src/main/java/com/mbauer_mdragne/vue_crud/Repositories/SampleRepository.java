package com.mbauer_mdragne.vue_crud.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mbauer_mdragne.vue_crud.Entities.Sample;
import com.mbauer_mdragne.vue_crud.Entities.SampleId;

public interface SampleRepository extends JpaRepository<Sample, SampleId> {

    @Query("SELECT s FROM Sample s WHERE s.s_flags LIKE 'F%' OR s.s_flags LIKE 'V%'")
    List<Sample> findAllForResearcher();
}