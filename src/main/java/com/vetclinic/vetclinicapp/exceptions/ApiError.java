package com.vetclinic.vetclinicapp.exceptions;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiError {

    private String message;

    private LocalDateTime timestamp;

    private List<String> details;

    public ApiError(String message, List<String> details) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.details = details;
    }

}
