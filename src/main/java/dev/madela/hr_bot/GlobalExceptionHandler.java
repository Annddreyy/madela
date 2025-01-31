package dev.madela.hr_bot;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder("Ошибка валидации данных: ");
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorMessage.append("Поле '" + error.getField() + "' - " + error.getDefaultMessage() + ". ")
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationErrors(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка валидации данных: " + ex.getMessage());
    }
}

/*
curl -X POST http://localhost:8080/faqs -H "Content-Type: application/json" -d "{\"question\": \"\"}"
curl -X PUT http://localhost:8080/faqs/999 -H "Content-Type: application/json" -d "{\"question\": \"Updated question\"}"

*/
