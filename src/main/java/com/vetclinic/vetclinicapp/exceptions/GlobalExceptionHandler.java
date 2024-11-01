package com.vetclinic.vetclinicapp.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        LocalDateTime dateTime = LocalDateTime.now();

        Map<String, String> errorMessages = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorMessages.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> response = new HashMap<>();
        response.put("time:", dateTime.format(formatter));
        response.put("message:", errorMessages);
        response.put("errorCode", "METHOD_ARGUMENT_NOT_VALID_EXCEPTION");

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
        LocalDateTime dateTime = LocalDateTime.now();

        Map<String, String> response = new HashMap<>();
        response.put("time", dateTime.format(formatter));
        response.put("message", ex.getMessage());
        response.put("errorCode", "RESOURCE_NOT_FOUND_EXCEPTION");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        LocalDateTime dateTime = LocalDateTime.now();

        Map<String, String> response = new HashMap<>();
        response.put("time", dateTime.format(formatter));
        response.put("message", ex.getMessage());
        response.put("errorCode", "SQL_INTEGRITY_CONSTRAINT_VIOLATION");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}