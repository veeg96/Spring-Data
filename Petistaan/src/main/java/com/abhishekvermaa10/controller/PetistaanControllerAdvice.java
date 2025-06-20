package com.abhishekvermaa10.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.abhishekvermaa10.dto.ErrorDTO;
import com.abhishekvermaa10.exception.OwnerNotFoundException;
import com.abhishekvermaa10.exception.PetNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;

@RestControllerAdvice
public class PetistaanControllerAdvice {
    @ExceptionHandler({OwnerNotFoundException.class,PetNotFoundException.class})
    public ResponseEntity<ErrorDTO> handleOwnerNotFoundException(Exception e){
        ErrorDTO dto = ErrorDTO.builder().error(HttpStatus.NOT_FOUND).
                                            message(e.getMessage()).
                                            status(HttpStatus.NOT_FOUND.value()).timeStamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleValidationException(ValidationException e) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(e.getMessage())
                .error(HttpStatus.BAD_REQUEST)
                .status(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }
    @ExceptionHandler
    public ResponseEntity<List<ErrorDTO>> handleConstraintVoilationException(ConstraintViolationException e){
       List<ErrorDTO> list = e.getConstraintViolations().stream().map(err-> ErrorDTO.builder()
                                                        .message(err.getMessage())
                                                        .error(HttpStatus.BAD_REQUEST)
                                                        .status(HttpStatus.BAD_REQUEST.value())
                                                        .timeStamp(LocalDateTime.now())
                                                        .build()).toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(list);
                                                        
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException exception) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(exception.getMessage())
                .error(HttpStatus.METHOD_NOT_ALLOWED)
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleGenericExcetpion(Exception exception) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(exception.getMessage())
                .error(HttpStatus.INTERNAL_SERVER_ERROR)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }


    @ExceptionHandler
    public ResponseEntity<List<ErrorDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ErrorDTO> errorDTOList = e.getBindingResult().getAllErrors().stream()
                .map(error -> ErrorDTO.builder()
                        .message(error.getDefaultMessage())
                        .error(HttpStatus.BAD_REQUEST)
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
                )
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTOList);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleEnumException(HttpMessageNotReadableException e){
        String errors = "";
        if(e.getCause() instanceof InvalidFormatException invalidFormatException && Objects.nonNull(invalidFormatException.getTargetType())&& invalidFormatException.getTargetType().isEnum()){
            errors = String.format("Invalid enum value: '%s' for the field: '%s'. The value must be one of: %s.",
                invalidFormatException.getValue(),invalidFormatException.getPath().get(invalidFormatException.getPath().size()-1).getFieldName(),
                                    Arrays.toString(invalidFormatException.getTargetType().getEnumConstants()));
               
                             }
                ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errors)
                .error(HttpStatus.BAD_REQUEST)
                .status(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now())
                .build();

        
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);

        
    }
}
