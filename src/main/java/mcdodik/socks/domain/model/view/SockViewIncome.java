package mcdodik.socks.domain.model.view;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class SockViewIncome {

    private String color;

    private Integer cottonPercentage = 0;

    @NotNull(message = "Количество носков не может быть пустым")
    @Min(value = 1, message = "Количество носков должно быть больше нуля")
    private Integer quantity = 0;
}
