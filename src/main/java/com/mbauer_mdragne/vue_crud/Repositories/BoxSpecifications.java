package com.mbauer_mdragne.vue_crud.Repositories;

import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
import com.mbauer_mdragne.vue_crud.DTOs.Range;
import com.mbauer_mdragne.vue_crud.Entities.Analysis;
import com.mbauer_mdragne.vue_crud.Entities.Box;
import com.mbauer_mdragne.vue_crud.Entities.BoxPos;
import com.mbauer_mdragne.vue_crud.Entities.Sample;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BoxSpecifications {

    public static Specification<Box> withGlobalDateFilter(AnalysisGlobalFilterDto globalDto) {
        return (root, query, cb) -> {
            if (globalDto == null || globalDto.getGlobalDateIn() == null) {
                return null;
            }

            Range<LocalDateTime> range = globalDto.getGlobalDateIn();
            
            if (range.getFrom() == null && range.getTo() == null) {
                return null;
            }

            Join<Box, BoxPos> posJoin = root.join("boxPositions");
            Join<BoxPos, Sample> sampleJoin = posJoin.join("sample");
            Join<Sample, Analysis> analysisJoin = sampleJoin.join("analyses");

            List<Predicate> predicates = new ArrayList<>();

            if (range.getFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                    analysisJoin.get("dateIn"),
                    Timestamp.valueOf(range.getFrom())
                ));
            }

            if (range.getTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(
                    analysisJoin.get("dateIn"),
                    Timestamp.valueOf(range.getTo())
                ));
            }

            query.distinct(true);

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}