package mcdodik.socks.infrastructure.handler;

import mcdodik.socks.infrastructure.exceptions.FileProcessingException;
import mcdodik.socks.infrastructure.exceptions.InsufficientStockException;
import mcdodik.socks.infrastructure.exceptions.InvalidDataFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDataFormatException.class)
    public ResponseEntity<ErrorMessage> handleInvalidDataFormatException(InvalidDataFormatException ex) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorMessage> handleInsufficientStockException(InsufficientStockException ex) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<ErrorMessage> handleFileProcessingException(FileProcessingException ex) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGlobalException(Exception ex) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
