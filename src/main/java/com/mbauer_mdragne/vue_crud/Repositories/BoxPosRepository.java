package com.mbauer_mdragne.vue_crud.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.mbauer_mdragne.vue_crud.Entities.BoxPos;
import com.mbauer_mdragne.vue_crud.Entities.BoxPosId;
import com.mbauer_mdragne.vue_crud.Projections.BoxPosWithoutTableView;

import java.util.List;

public interface BoxPosRepository extends JpaRepository<BoxPos, BoxPosId>, JpaSpecificationExecutor<BoxPos> {
    //Rueckstellbehaelter (BoxPos) haben Proben, aber keine Analyse dazu
    @Query(value = "SELECT * FROM venlab.get_suspicious_boxpos_without_analysis()", nativeQuery = true)
    List<BoxPosWithoutTableView> findBoxPosWithoutAnalysis();

    //Rueckstellbehaelter haben gar keine Probenummer
    @Query(value = "SELECT * FROM venlab.get_suspicious_boxpos_without_samples()", nativeQuery = true)
    List<BoxPosWithoutTableView> findBoxPosWithoutSample();
}