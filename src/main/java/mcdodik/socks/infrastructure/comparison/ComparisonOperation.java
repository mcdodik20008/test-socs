package mcdodik.socks.infrastructure.comparison;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

@FunctionalInterface
public interface ComparisonOperation {

    Predicate apply(CriteriaBuilder criteriaBuilder, Path<Integer> path, Integer value);
}
