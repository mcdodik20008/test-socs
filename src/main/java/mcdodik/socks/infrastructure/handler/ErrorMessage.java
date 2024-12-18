package mcdodik.socks.infrastructure.handler;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorMessage {

    private String message;

    private LocalDateTime errorTime = LocalDateTime.now();

    public ErrorMessage(String message) {
        this.message = message;
    }
}
