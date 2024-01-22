package com.project.eventManagement.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.eventManagement.exception.EventNotFoundException;
import com.project.eventManagement.exception.UserNotFoundException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleInvalidArguments(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleEventNotFound(EventNotFoundException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Error", exception.getMessage());

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleTimestampRequestError(HttpMessageNotReadableException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", exception.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", exception.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.UNAUTHORIZED);

    }
}
