package mcdodik.socks.domain.model.view;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
public class SockViewOutcome {

    private String color;

    private Integer cottonPercentage = 0;

    @NotNull(message = "Количество носков не может быть пустым")
    @Max(value = -1, message = "Количество носков должно быть меньше нуля")
    private Integer quantity = 0;

}
