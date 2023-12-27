package com.java.h2.user.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<CustomError> handleCustomValidationException(CustomValidationException cve){

        return new ResponseEntity<>(CustomError.builder()
                .errorCode("0001")
                .errorMsg(cve.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    //DataIntegrityViolationException

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomError> handleDataIntegrityViolationException(DataIntegrityViolationException dive){

        return new ResponseEntity<>(CustomError.builder()
                .errorCode("23505")
                .errorMsg(dive.getMostSpecificCause().getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    //MethodArgumentNotValidException

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> handleMethodArgumentNotValidException(MethodArgumentNotValidException manve){

        return new ResponseEntity<>(CustomError.builder()
                .errorCode("0002")
                .errorMsg(Arrays.stream(manve.getDetailMessageArguments()).toList().get(1).toString())
                .build(), HttpStatus.BAD_REQUEST);
    }

    //MissingRequestHeaderException
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<CustomError> handleMissingRequestHeaderException(MissingRequestHeaderException mrhe){

        return new ResponseEntity<>(CustomError.builder()
                .errorCode("0003")
                .errorMsg(mrhe.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    //MissingServletRequestParameterException
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<CustomError> handleMissingServletRequestParameterException(MissingServletRequestParameterException mrhe){

        return new ResponseEntity<>(CustomError.builder()
                .errorCode("0004")
                .errorMsg(mrhe.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
