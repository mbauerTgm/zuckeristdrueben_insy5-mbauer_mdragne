package com.mbauer_mdragne.vue_crud.Repositories;

import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
import com.mbauer_mdragne.vue_crud.DTOs.Range;
import com.mbauer_mdragne.vue_crud.Entities.Log;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogSpecifications {

    public static Specification<Log> withGlobalDateFilter(AnalysisGlobalFilterDto globalDto) {
        return (root, query, cb) -> {
            if (globalDto == null || globalDto.getGlobalDateIn() == null) {
                return null;
            }

            List<Predicate> predicates = new ArrayList<>();
            Range<LocalDateTime> range = globalDto.getGlobalDateIn();

            if (range.getFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateCreated"), Timestamp.valueOf(range.getFrom())));
            }
            if (range.getTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dateCreated"), Timestamp.valueOf(range.getTo())));
            }

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}