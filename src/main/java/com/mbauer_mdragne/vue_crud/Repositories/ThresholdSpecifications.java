package com.mbauer_mdragne.vue_crud.Repositories;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
import com.mbauer_mdragne.vue_crud.DTOs.Range;
import com.mbauer_mdragne.vue_crud.Entities.Threshold;
import com.mbauer_mdragne.vue_crud.DateUtils;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

public class ThresholdSpecifications {

    public static Specification<Threshold> withGlobalDateFilter(AnalysisGlobalFilterDto globalDto) {
        return (root, query, cb) -> {
            if (globalDto == null || globalDto.getGlobalDateIn() == null) {
                return null;
            }

            Range<?> range = globalDto.getGlobalDateIn();
            
            Timestamp from = DateUtils.parseAny(range.getFrom());
            Timestamp to = DateUtils.parseAny(range.getTo());

            if (from == null && to == null) return null;

            List<Predicate> predicates = new ArrayList<>();

            if (from != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateChanged"), from));
            }
            if (to != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dateChanged"), to));
            }

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}