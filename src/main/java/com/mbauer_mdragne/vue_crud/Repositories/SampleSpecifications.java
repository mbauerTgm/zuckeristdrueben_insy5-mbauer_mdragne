package com.mbauer_mdragne.vue_crud.Repositories;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
import com.mbauer_mdragne.vue_crud.DTOs.Range;
import com.mbauer_mdragne.vue_crud.Entities.Analysis;
import com.mbauer_mdragne.vue_crud.Entities.Sample;
import com.mbauer_mdragne.vue_crud.DateUtils;
import org.springframework.data.jpa.domain.Specification;

public class SampleSpecifications {

    public static Specification<Sample> withGlobalDateFilter(AnalysisGlobalFilterDto globalDto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<Sample, Analysis> analysisJoin = root.join("analyses", JoinType.LEFT);

            if (globalDto != null && globalDto.getGlobalDateIn() != null) {
                Range<?> range = globalDto.getGlobalDateIn();
                Timestamp from = DateUtils.parseAny(range.getFrom());
                Timestamp to = DateUtils.parseAny(range.getTo());

                if (from != null) {
                    predicates.add(cb.greaterThanOrEqualTo(analysisJoin.get("dateIn"), from));
                }
                if (to != null) {
                    predicates.add(cb.lessThanOrEqualTo(analysisJoin.get("dateIn"), to));
                }
            }
            if (query != null) {
                query.distinct(true);
            }
            return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Sample> researcherRestriction() {
        return (root, query, cb) -> {
            Join<Sample, Analysis> analysisJoin = root.join("analyses", JoinType.LEFT);

            Predicate sampleF = cb.like(root.get("sFlags"), "F%");
            Predicate sampleV = cb.like(root.get("sFlags"), "V%");
            
            Predicate analysisF = cb.like(analysisJoin.get("aFlags"), "F%");
            Predicate analysisV = cb.like(analysisJoin.get("aFlags"), "V%");
            
            return cb.or(
                cb.or(sampleF, sampleV),
                cb.or(analysisF, analysisV)
            );
        };
    }
}