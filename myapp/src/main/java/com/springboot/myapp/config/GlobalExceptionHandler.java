package com.springboot.myapp.config;

import com.springboot.myapp.exceptions.AccessNotAllowedException;
import com.springboot.myapp.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> getMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String, Object> map= new HashMap<>();
        BindingResult bindingResult= e.getBindingResult();
        List<FieldError> list= bindingResult.getFieldErrors();
        for(FieldError error: list){
            map.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(map);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> getHttpMessageNotReadableException(HttpMessageNotReadableException e){
        Map<String, Object> map= new HashMap<>();

        map.put("message","Incorrect value of Enum");

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(map);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e){
        Map<String, Object> map= new HashMap<>();

        map.put("message",e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(AccessNotAllowedException.class)
    public ResponseEntity<?> handleAccessNotAllowedException(AccessNotAllowedException e){
        Map<String, Object> map= new HashMap<>();

        map.put("message",e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e){
        Map<String, Object> map= new HashMap<>();

        map.put("message",e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }
}
