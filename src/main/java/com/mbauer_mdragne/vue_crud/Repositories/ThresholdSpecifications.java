package com.mbauer_mdragne.vue_crud.Repositories;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mbauer_mdragne.vue_crud.DTOs.*;
import com.mbauer_mdragne.vue_crud.Entities.Threshold;

import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;

import jakarta.persistence.criteria.Predicate;
public class ThresholdSpecifications {
    public static Specification<Threshold> withGlobalDateFilter(AnalysisGlobalFilterDto globalDto) {
        return (root, query, cb) -> {
            if (globalDto == null || globalDto.getGlobalDateIn() == null) return null;

            Range<LocalDateTime> range = globalDto.getGlobalDateIn();
            List<Predicate> predicates = new ArrayList<>();

            if (range.getFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateChanged"), Timestamp.valueOf(range.getFrom())));
            }
            if (range.getTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dateChanged"), Timestamp.valueOf(range.getTo())));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}