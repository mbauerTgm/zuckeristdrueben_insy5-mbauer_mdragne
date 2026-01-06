package com.mbauer_mdragne.vue_crud.Repositories;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
import com.mbauer_mdragne.vue_crud.DTOs.*;
import com.mbauer_mdragne.vue_crud.Entities.BoxPos;
import jakarta.persistence.criteria.Join;


import jakarta.persistence.criteria.Predicate;

public class BoxPosSpecifications {

    public static Specification<BoxPos> withGlobalDateFilter(AnalysisGlobalFilterDto globalDto) {
        return (root, query, cb) -> {
            if (globalDto == null || globalDto.getGlobalDateIn() == null) return null;

            Range<LocalDateTime> range = globalDto.getGlobalDateIn();
            Join<Object, Object> analysisJoin = root.join("sample").join("analyses");

            List<Predicate> predicates = new ArrayList<>();
            if (range.getFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(analysisJoin.get("dateIn"), Timestamp.valueOf(range.getFrom())));
            }
            if (range.getTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(analysisJoin.get("dateIn"), Timestamp.valueOf(range.getTo())));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}