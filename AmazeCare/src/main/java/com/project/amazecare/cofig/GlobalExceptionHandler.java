package com.project.amazecare.cofig;

import com.project.amazecare.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
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
@Slf4j
public class GlobalExceptionHandler {

    String m= "Message";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> getMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.atLevel(Level.WARN).log(e.getMessage());
        Map<String, Object> map= new HashMap<>();
        BindingResult bindingResult= e.getBindingResult();
        List<FieldError> list= bindingResult.getFieldErrors();
        for(FieldError error: list){
            map.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(map);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> getHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.atLevel(Level.WARN).log(e.getMessage());
        Map<String, Object> map= new HashMap<>();

        map.put(m, e.getMessage());

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(map);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException e){
        log.atLevel(Level.WARN).log(e.getMessage());
        Map<String, Object> map= new HashMap<>();

        map.put(m, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(AppointmentUpdateException.class)
    public ResponseEntity<Map<String, Object>> handleAppointmentUpdateException(AppointmentUpdateException e){
        log.atLevel(Level.WARN).log(e.getMessage());
        Map<String, Object> map= new HashMap<>();

        map.put(m, e.getMessage());

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(map);
    }


    @ExceptionHandler(AppointmentStatusUpdateException.class)
    public ResponseEntity<Map<String, Object>> handleAppointmentStatusUpdateException(AppointmentStatusUpdateException e){
        log.atLevel(Level.WARN).log(e.getMessage());
        Map<String, Object> map= new HashMap<>();

        map.put(m, e.getMessage());

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(map);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, Object>> handleNullPointerException(NullPointerException e){
        log.atLevel(Level.WARN).log(e.getMessage());
        Map<String, Object> map= new HashMap<>();
        map.put(m, e.getMessage());

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(map);
    }


    @ExceptionHandler(PatientAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handlePatientAlreadyExistsException(PatientAlreadyExistsException e){
        log.atLevel(Level.WARN).log(e.getMessage());
        Map<String, Object> map= new HashMap<>();
        map.put(m, e.getMessage());

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(map);
    }


    @ExceptionHandler(AppointmentAndAdmissionException.class)
    public ResponseEntity<Map<String, Object>> handleAppointmentAndAdmissionException(AppointmentAndAdmissionException e){
        log.atLevel(Level.WARN).log(e.getMessage());
        Map<String, Object> map= new HashMap<>();
        map.put(m, e.getMessage());

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(map);
    }
}
