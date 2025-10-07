package org.exemploTesouraria.handler;

import org.exemploTesouraria.exception.DataConflictException;
import org.exemploTesouraria.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handlerResourceNotFound(ResourceNotFoundException ex){
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp: ", LocalDateTime.now());
        body.put("status:", HttpStatus.NOT_FOUND.value());
        body.put("error:", "Not Found");
        body.put("message:", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataConflictException.class)
    public ResponseEntity<Object> handlerDataConflict(DataConflictException ex){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp: ", LocalDateTime.now());
        body.put("status:", HttpStatus.CONFLICT.value());
        body.put("error:", "Data Conflict Exception");
        body.put("message:", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
}
