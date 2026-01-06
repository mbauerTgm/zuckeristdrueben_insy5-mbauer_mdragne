package com.mbauer_mdragne.vue_crud.Repositories;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mbauer_mdragne.vue_crud.Entities.Box;

public interface BoxRepository extends JpaRepository<Box, String>, JpaSpecificationExecutor<Box> {
    @Query("SELECT DISTINCT b FROM Box b " +
           "JOIN b.boxPositions bp " +
           "JOIN bp.sample s " +
           "JOIN s.analyses a " +
           "WHERE a.dateIn BETWEEN :start AND :end")
    Page<Box> findBoxesUsedInPeriod(Timestamp start, Timestamp end, Pageable pageable);
}