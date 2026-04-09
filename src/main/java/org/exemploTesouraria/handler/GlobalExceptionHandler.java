package org.exemploTesouraria.handler;

import jakarta.validation.ConstraintViolationException;
import org.exemploTesouraria.exception.DataConflictException;
import org.exemploTesouraria.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handlerDataIntegrityViolation(DataIntegrityViolationException ex){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp: ", LocalDateTime.now());
        body.put("status:", HttpStatus.CONFLICT.value());
        body.put("error:", "Data Conflict Exception");
        body.put("message:", "Violação de integridade de dados.");

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handlerIllegalArgument(IllegalArgumentException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp: ", LocalDateTime.now());
        body.put("status:", HttpStatus.BAD_REQUEST.value());
        body.put("error:", "Bad Request");
        body.put("message:", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handlerConstraintViolation(ConstraintViolationException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp: ", LocalDateTime.now());
        body.put("status:", HttpStatus.BAD_REQUEST.value());
        body.put("error:", "Validation Error");
        body.put("message:", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handlerMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp: ", LocalDateTime.now());
        body.put("status:", HttpStatus.BAD_REQUEST.value());
        body.put("error:", "Validation Error");
        body.put("message:", "Parâmetro inválido: '" + ex.getName() + "'. Valor recebido: '" + ex.getValue() + "'.");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
