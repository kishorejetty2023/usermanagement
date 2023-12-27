package com.java.user.management.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<CustomError> handleCustomValidationException(CustomValidationException cve){
        log.error("Validation error : {}",cve);
        return new ResponseEntity<>(CustomError.builder()
                .errorCode("0001")
                .errorMsg(cve.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    //DataIntegrityViolationException

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomError> handleDataIntegrityViolationException(DataIntegrityViolationException dive){

        log.error("Unique key validation error : {}",dive);
        return new ResponseEntity<>(CustomError.builder()
                .errorCode("23505")
                .errorMsg(dive.getMostSpecificCause().getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    //MethodArgumentNotValidException

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> handleMethodArgumentNotValidException(MethodArgumentNotValidException manve){

        log.error("Method argument not valid validation error : ",manve);
        return new ResponseEntity<>(CustomError.builder()
                .errorCode("0002")
                .errorMsg(Arrays.stream(manve.getDetailMessageArguments()).toList().get(1).toString())
                .build(), HttpStatus.BAD_REQUEST);
    }

    //MissingRequestHeaderException
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<CustomError> handleMissingRequestHeaderException(MissingRequestHeaderException mrhe){

        log.error("Missing request header error : {}",mrhe);
        return new ResponseEntity<>(CustomError.builder()
                .errorCode("0003")
                .errorMsg(mrhe.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    //MissingServletRequestParameterException
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<CustomError> handleMissingServletRequestParameterException(MissingServletRequestParameterException msrp){

        log.error("Missing servlet request parameter exception : {}",msrp);
        return new ResponseEntity<>(CustomError.builder()
                .errorCode("0004")
                .errorMsg(msrp.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    //NoSuchElementException

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<CustomError> handleNoSuchElementException(NoSuchElementException nse){

        log.error("No such element exception : {}",nse);
        return new ResponseEntity<>(CustomError.builder()
                .errorCode("0005")
                .errorMsg(nse.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
