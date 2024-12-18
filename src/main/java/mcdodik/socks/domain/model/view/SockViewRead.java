package mcdodik.socks.domain.model.view;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class SockViewRead {

    private Long id;

    private String color;

    private Integer cottonPercentage = 0;

    private Integer quantity = 0;

}
