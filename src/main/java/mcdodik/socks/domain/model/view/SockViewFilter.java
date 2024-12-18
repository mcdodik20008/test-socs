package mcdodik.socks.domain.model.view;

import lombok.Getter;
import lombok.Setter;
import mcdodik.socks.infrastructure.enums.FilterOperations;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Validated
public class SockViewFilter {

    private String color;

    @Min(value = 0, message = "Процент содержания хлопка не может быть меньше 0")
    @Max(value = 100, message = "Процент содержания хлопка не может быть больше 100")
    private Integer cottonPercentage;

    private FilterOperations cottonPercentageOperations;

    private String cottonPercentRange;

    private Sort sort;

}
