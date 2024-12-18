package mcdodik.socks.domain.specifications;

import liquibase.repackaged.net.sf.jsqlparser.util.validation.ValidationException;
import mcdodik.socks.domain.model.entity.Sock;
import mcdodik.socks.domain.model.view.SockViewFilter;
import mcdodik.socks.infrastructure.comparison.ComparisonMap;
import mcdodik.socks.infrastructure.comparison.ComparisonOperation;
import mcdodik.socks.infrastructure.enums.FilterOperations;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;

import javax.persistence.criteria.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SockSpecification {

    public Specification<Sock> getConfiguredSpecification(SockViewFilter filter){
        // Фильтрация цвета по указанному цвету
        var specification = Specification.where(hasColor(filter.getColor()));

        // Выбор фильтровать по равенству числа или по интервалу
        Pair<Integer, Integer> range = getPercentRangeFromInput(filter.getCottonPercentRange());
        System.out.println(range);
        if (range != null) {
            // Фильтрация по интервалу
            specification = specification.and(cottonPercentageBetween(range.getFirst(), range.getSecond()));
        } else if (filter.getCottonPercentage() != null) {
            // Фильтрация по равенству
            specification = specification.and(hasCottonPercentage(filter.getCottonPercentage(), filter.getCottonPercentageOperations()));
        }
        return specification;
    }

    public Specification<Sock> hasColor(String color) {
        return (root, query, criteriaBuilder) -> {
            if (color == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("color"), color);
        };
    }

    public Specification<Sock> hasCottonPercentage(Integer cottonPercentage, FilterOperations operation) {
        return (root, query, criteriaBuilder) -> {
            if (cottonPercentage == null || operation == null) {
                return criteriaBuilder.conjunction();
            }

            ComparisonOperation comparison = ComparisonMap.MAP.get(operation);
            if (comparison == null) {
                throw new IllegalArgumentException("Unsupported operation: " + operation);
            }

            Path<Integer> path = root.get("cottonPercentage");
            return comparison.apply(criteriaBuilder, path, cottonPercentage);
        };
    }

    public Specification<Sock> cottonPercentageBetween(Integer minCotton, Integer maxCotton) {
        return (root, query, criteriaBuilder) -> {
            if (minCotton == null || maxCotton == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.between(root.get("cottonPercentage"), minCotton, maxCotton);
        };
    }

    private Pair<Integer, Integer> getPercentRangeFromInput(String input) {
        if (input == null || input.isEmpty()){
            return null;
        }

        String regex = "от (\\d+) до (\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            try {
                int firstNumber = Integer.parseInt(matcher.group(1));
                int secondNumber = Integer.parseInt(matcher.group(2));
                return Pair.of(firstNumber, secondNumber);
            } catch (NumberFormatException e) {
                throw new ValidationException("Ошибка при приведении интервала к паре чисел. Интервал: " + input);
            }
        }
        return null;
    }
}
