package com.mbauer_mdragne.vue_crud.Repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mbauer_mdragne.vue_crud.Entities.Analysis;

public interface AnalysisRepository extends JpaRepository<Analysis, Long>, JpaSpecificationExecutor<Analysis>{

    //Analysen die Researcher sehen duerfen (Wo Flags mit F oder V beginnen)
    //Analysen die Researcher sehen duerfen (Wo Flags mit F oder V beginnen)
    @Query("SELECT a FROM Analysis a WHERE a.aFlags LIKE 'F%' OR a.aFlags LIKE 'V%'")
    List<Analysis> findAllForResearcher();
    @Query("SELECT a FROM Analysis a WHERE a.aFlags LIKE 'F%' OR a.aFlags LIKE 'V%'")
    Page<Analysis> findAllForResearcher(Pageable pageable);

    //Analysen haben keine Rueckstellposition
    @Query("SELECT a FROM Analysis a WHERE NOT EXISTS (SELECT bp FROM BoxPos bp WHERE bp.sId = a.sId AND bp.sStamp = a.sStamp)")
    Page<Analysis> findAnalysisWithoutBoxPos(Pageable pageable);

    //Analysen haben 0-Werte (Beispiel für Pol, Nat, Kal)
    @Query("SELECT a FROM Analysis a WHERE a.pol = 0 OR a.nat = 0 OR a.kal = 0")
    Page<Analysis> findAnalysisWithZeroValues(Pageable pageable);

    //Analysen fehlen Start- oder Endzeit
    @Query("SELECT a FROM Analysis a WHERE a.dateIn IS NULL OR a.dateOut IS NULL")
    Page<Analysis> findAnalysisWithMissingDates(Pageable pageable);
}