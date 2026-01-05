package com.mbauer_mdragne.vue_crud.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mbauer_mdragne.vue_crud.Entities.Sample;
import com.mbauer_mdragne.vue_crud.Entities.SampleId;

public interface SampleRepository extends JpaRepository<Sample, SampleId> {

    @Query("SELECT s FROM Sample s WHERE s.sFlags LIKE 'F%' OR s.sFlags LIKE 'V%'")
    List<Sample> findAllForResearcher();
    @Query("SELECT s FROM Sample s WHERE s.sFlags LIKE 'F%' OR s.sFlags LIKE 'V%'")
    Page<Sample> findAllForResearcher(Pageable pageable);
}