package com.mbauer_mdragne.vue_crud.Repositories;

import com.mbauer_mdragne.vue_crud.DTOs.AnalysisFilterDto;
import com.mbauer_mdragne.vue_crud.DTOs.AnalysisGlobalFilterDto;
import com.mbauer_mdragne.vue_crud.DTOs.Range;
import com.mbauer_mdragne.vue_crud.Entities.Analysis;
import com.mbauer_mdragne.vue_crud.DateUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class AnalysisSpecifications {

    public static Specification<Analysis> withDynamicFilter(AnalysisFilterDto dto, boolean isResearcher) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            addRangePredicate(predicates, cb, root.get("aId"), dto.getAId());
            addRangePredicate(predicates, cb, root.get("sId"), dto.getSId());
            addDateTimeRangePredicate(predicates, cb, root.get("dateIn"), dto.getDateIn());
            addDateTimeRangePredicate(predicates, cb, root.get("dateOut"), dto.getDateOut());

            // NUR hinzufügen, wenn es KEIN Researcher ist. 
            // Researcher bekommen ihre aFlags-Logik separat über forResearcher()
            if (!isResearcher) {
                addAFlagsPredicate(predicates, cb, root.get("aFlags"), dto.getAFlags());
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
    private static void addAFlagsPredicate(
        List<Predicate> predicates,
        jakarta.persistence.criteria.CriteriaBuilder cb,
        Path<String> path,
        Range<String> range
    ) {
        if (range == null || range.getFrom() == null) return;

        predicates.add(cb.like(path, range.getFrom() + "%"));
    }

    private static void addDateTimeRangePredicate(
            List<Predicate> predicates,
            jakarta.persistence.criteria.CriteriaBuilder cb,
            Path<Timestamp> path,
            Range<?> range
    ) {
        if (range == null) return;

        Timestamp from = DateUtils.parseAny(range.getFrom());
        Timestamp to = DateUtils.parseAny(range.getTo());

        if (from != null) {
            predicates.add(cb.greaterThanOrEqualTo(path, from));
        }
        if (to != null) {
            predicates.add(cb.lessThanOrEqualTo(path, to));
        }
    }

    private static <T extends Comparable<? super T>> void addRangePredicate(
            List<Predicate> predicates,
            jakarta.persistence.criteria.CriteriaBuilder cb,
            Path<T> path,
            Range<T> range
    ) {
        if (range == null) return;

        if (range.getFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(path, range.getFrom()));
        }
        if (range.getTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(path, range.getTo()));
        }
    }

    public static Specification<Analysis> forResearcher() {
        return (root, query, cb) -> {
            Predicate likeF = cb.like(root.get("aFlags"), "F%");
            Predicate likeV = cb.like(root.get("aFlags"), "V%");
            return cb.or(likeF, likeV);
        };
    }

    public static Specification<Analysis> withGlobalDateFilter(AnalysisGlobalFilterDto globalDto) {
        return (root, query, cb) -> {
            if (globalDto == null || globalDto.getGlobalDateIn() == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            Range<?> range = globalDto.getGlobalDateIn();

            Timestamp from = DateUtils.parseAny(range.getFrom());
            Timestamp to = DateUtils.parseAny(range.getTo());

            if (from != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateIn"), from));
            }
            if (to != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dateIn"), to));
            }

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}