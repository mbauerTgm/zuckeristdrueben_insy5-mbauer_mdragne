package com.mbauer_mdragne.vue_crud.Repositories;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
import com.mbauer_mdragne.vue_crud.DTOs.Range;
import com.mbauer_mdragne.vue_crud.Entities.BoxPos;
import com.mbauer_mdragne.vue_crud.DateUtils;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

public class BoxPosSpecifications {

    public static Specification<BoxPos> withGlobalDateFilter(AnalysisGlobalFilterDto globalDto) {
        return (root, query, cb) -> {
            if (globalDto == null || globalDto.getGlobalDateIn() == null) {
                return null;
            }

            Range<?> range = globalDto.getGlobalDateIn();
            
            Timestamp from = DateUtils.parseAny(range.getFrom());
            Timestamp to = DateUtils.parseAny(range.getTo());

            if (from == null && to == null) return null;

            Join<Object, Object> sampleJoin = root.join("sample");
            Join<Object, Object> analysisJoin = sampleJoin.join("analyses");

            List<Predicate> predicates = new ArrayList<>();
            
            if (from != null) {
                predicates.add(cb.greaterThanOrEqualTo(analysisJoin.get("dateIn"), from));
            }
            if (to != null) {
                predicates.add(cb.lessThanOrEqualTo(analysisJoin.get("dateIn"), to));
            }

            query.distinct(true);

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}