package com.mbauer_mdragne.vue_crud.Repositories;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Join;

import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
import com.mbauer_mdragne.vue_crud.DTOs.Range;
import com.mbauer_mdragne.vue_crud.Entities.Analysis;
import com.mbauer_mdragne.vue_crud.Entities.Sample;
import com.mbauer_mdragne.vue_crud.DateUtils;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

public class SampleSpecifications {

    public static Specification<Sample> withGlobalDateFilter(AnalysisGlobalFilterDto globalDto) {
        return (root, query, cb) -> {
            if (globalDto == null || globalDto.getGlobalDateIn() == null) return null;

            Range<?> range = globalDto.getGlobalDateIn();
            Timestamp from = DateUtils.parseAny(range.getFrom());
            Timestamp to = DateUtils.parseAny(range.getTo());

            if (from == null && to == null) return null;

            Join<Sample, Analysis> analysisJoin = root.join("analyses"); 

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