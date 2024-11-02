package com.vetclinic.vetclinicapp.exceptions;

import com.vetclinic.vetclinicapp.constants.Constants;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @Getter
    public static class CustomException extends RuntimeException {
        private final HttpStatus status;

        public CustomException(String message, HttpStatus status) {
            super(message);
            this.status = status;
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        LocalDateTime dateTime = LocalDateTime.now();

        Map<String, String> errorMessages = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorMessages.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> response = new HashMap<>();
        response.put("time:", dateTime.format(Constants.DATE_TIME_FORMATTER));
        response.put("message:", errorMessages);
        response.put("errorCode", "METHOD_ARGUMENT_NOT_VALID_EXCEPTION");

        logger.error("Error occurred: Validation exception, field errors: {}", errorMessages);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(CustomException ex) {
        LocalDateTime dateTime = LocalDateTime.now();

        Map<String, String> response = new HashMap<>();
        response.put("time", dateTime.format(Constants.DATE_TIME_FORMATTER));
        response.put("message", ex.getMessage());
        response.put("errorCode", "RESOURCE_NOT_FOUND_EXCEPTION");

        logger.error("Error occurred: Resource not found, message: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        LocalDateTime dateTime = LocalDateTime.now();

        Map<String, String> response = new HashMap<>();
        response.put("time", dateTime.format(Constants.DATE_TIME_FORMATTER));
        response.put("message", ex.getMessage());
        response.put("errorCode", "SQL_INTEGRITY_CONSTRAINT_VIOLATION");

        logger.error("Error occurred: SQL integrity constraint violation, message: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        LocalDateTime dateTime = LocalDateTime.now();

        Map<String, String> response = new HashMap<>();
        response.put("time", dateTime.format(Constants.DATE_TIME_FORMATTER));
        response.put("message", "An unexpected error occurred: " + ex.getMessage());
        response.put("errorCode", "GENERAL_EXCEPTION");

        logger.error("Error occurred: Unexpected exception, message: {}", ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}