package br.com.gc.barbershop.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardError error = new StandardError.Builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Entity not found")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status.value()).body(error);

    }

}
