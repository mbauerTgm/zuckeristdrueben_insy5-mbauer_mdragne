package com.mbauer_mdragne.vue_crud.Repositories;

import com.mbauer_mdragne.vue_crud.DTOs.AnalysisFilterDto;
import com.mbauer_mdragne.vue_crud.DTOs.Range;
import com.mbauer_mdragne.vue_crud.Entities.Analysis;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class AnalysisSpecifications {

    public static Specification<Analysis> withDynamicFilter(AnalysisFilterDto dto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // ID Filter
            addRangePredicate(predicates, cb, root.get("id"), dto.getId());

            // Sample ID Filter (s_id)
            addRangePredicate(predicates, cb, root.get("s_id"), dto.getSId());

            // Datum Filter (date_in / date_out)
            addRangePredicate(predicates, cb, root.get("date_in"), dto.getDateIn());
            addRangePredicate(predicates, cb, root.get("date_out"), dto.getDateOut());

            // Flags
            addRangePredicate(predicates, cb, root.get("a_flags"), dto.getAFlags());

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    // Generische Methode für ALLE Typen (Long, String, Date)
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
            // WHERE a.a_flags LIKE 'F%'
            jakarta.persistence.criteria.Predicate likeF = cb.like(root.get("a_flags"), "F%");
            
            // WHERE a.a_flags LIKE 'V%'
            jakarta.persistence.criteria.Predicate likeV = cb.like(root.get("a_flags"), "V%");
            
            // Kombinieren mit OR
            return cb.or(likeF, likeV);
        };
    }
}