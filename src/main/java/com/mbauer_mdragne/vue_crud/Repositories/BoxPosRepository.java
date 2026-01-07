package com.mbauer_mdragne.vue_crud.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.mbauer_mdragne.vue_crud.Entities.BoxPos;
import com.mbauer_mdragne.vue_crud.Entities.BoxPosId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoxPosRepository extends JpaRepository<BoxPos, BoxPosId>, JpaSpecificationExecutor<BoxPos> {
    //Rueckstellbehaelter (BoxPos) haben Proben, aber keine Analyse dazu
    @Query(value = "SELECT * FROM venlab.get_suspicious_boxpos_without_analysis()",
            countQuery = "SELECT count(*) FROM venlab.get_suspicious_boxpos_without_analysis()",
            nativeQuery = true)
    Page<BoxPos> findBoxPosWithoutAnalysis(Pageable pageable);

    //Rueckstellbehaelter haben gar keine Probenummer
    @Query(value = "SELECT * FROM venlab.get_suspicious_boxpos_without_samples()",
            countQuery = "SELECT count(*) FROM venlab.get_suspicious_boxpos_without_samples()",
            nativeQuery = true)
    Page<BoxPos> findBoxPosWithoutSample(Pageable pageable);
}