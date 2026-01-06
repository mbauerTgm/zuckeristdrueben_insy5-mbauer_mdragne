package com.mbauer_mdragne.vue_crud.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mbauer_mdragne.vue_crud.Entities.BoxPos;
import com.mbauer_mdragne.vue_crud.Entities.BoxPosId;

public interface BoxPosRepository extends JpaRepository<BoxPos, BoxPosId> {
    //Rueckstellbehaelter (BoxPos) haben Proben, aber keine Analyse dazu
    @Query("SELECT bp FROM BoxPos bp WHERE bp.sId IS NOT NULL AND NOT EXISTS (SELECT a FROM Analysis a WHERE a.sId = bp.sId AND a.sStamp = bp.sStamp)")
    List<BoxPos> findBoxPosWithSampleButNoAnalysis();

    //Rueckstellbehaelter haben gar keine Probenummer
    @Query("SELECT bp FROM BoxPos bp WHERE bp.sId IS NULL OR bp.sId = ''")
    List<BoxPos> findBoxPosWithoutSample();
}