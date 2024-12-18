package mcdodik.socks.infrastructure.comparison;

import lombok.experimental.UtilityClass;
import mcdodik.socks.infrastructure.enums.FilterOperations;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.EnumMap;
import java.util.Map;

@UtilityClass
public class ComparisonMap {

    public static Map<FilterOperations, ComparisonOperation> MAP = new EnumMap<>(FilterOperations.class);

    static {
        MAP.put(FilterOperations.EQUAL, CriteriaBuilder::equal);
        MAP.put(FilterOperations.LESS_THEN, CriteriaBuilder::lessThan);
        MAP.put(FilterOperations.MORE_THEN, CriteriaBuilder::greaterThan);
    }

}
