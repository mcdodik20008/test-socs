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
public class SockViewUpdate {

    @NotNull(message = "Цвет носков не может быть пустым")
    private String color;

    @NotNull(message = "Процент содержания хлопка не может быть пустым")
    @Min(value = 0, message = "Процент содержания хлопка не может быть меньше 0")
    @Max(value = 100, message = "Процент содержания хлопка не может быть больше 100")
    private Integer cottonPercentage = 0;

    @NotNull(message = "Количество носков не может быть пустым")
    @Min(value = 1, message = "Количество носков должно быть больше нуля")
    private Integer quantity = 0;

}
