package com.mbauer_mdragne.vue_crud.Repositories;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Join;

import com.mbauer_mdragne.vue_crud.DTOs.*;
import com.mbauer_mdragne.vue_crud.Entities.Analysis;
import com.mbauer_mdragne.vue_crud.Entities.Sample;

import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;

import jakarta.persistence.criteria.Predicate;

public class SampleSpecifications {
    public static Specification<Sample> withGlobalDateFilter(AnalysisGlobalFilterDto globalDto) {
        return (root, query, cb) -> {
            if (globalDto == null || globalDto.getGlobalDateIn() == null) return null;

            Join<Sample, Analysis> analysisJoin = root.join("analysis");

            Range<LocalDateTime> range = globalDto.getGlobalDateIn();
            List<Predicate> predicates = new ArrayList<>();

            if (range.getFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(analysisJoin.get("dateIn"), Timestamp.valueOf(range.getFrom())));
            }
            if (range.getTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(analysisJoin.get("dateIn"), Timestamp.valueOf(range.getTo())));
            }

            query.distinct(true);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}