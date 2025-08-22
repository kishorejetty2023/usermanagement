package com.java.user.management.exceptions;

import com.java.user.management.constants.ErrorConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private List<FieldErrorDetail> fieldErrors ;
    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<CustomError> handleCustomValidationException(CustomValidationException cve){
       fieldErrors = new ArrayList<>();
        log.error("Validation error : ",cve);
        return new ResponseEntity<>(CustomError.builder()
                .errors(cve.getErrors())
                .build(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomError> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        fieldErrors = new ArrayList<>();

        // Extract specific details from the exception
        String message = ex.getMostSpecificCause().getMessage();
        log.info("error Code : {}",message);

        // Determine which unique constraint was violated based on the error message
        if (message.contains("firstName") && message.contains("lastName")) {
            // If firstName and lastName unique constraint is violated
            fieldErrors.add(new FieldErrorDetail("firstName, lastName", ErrorConstants.DUPLICATE_KEY, "Combination of First Name and Last Name must be unique"));
        } else if (message.contains("Duplicate entry")) {
            // If userId unique constraint is violated
            fieldErrors.add(new FieldErrorDetail("userId", ErrorConstants.DUPLICATE_KEY, "User ID must be unique"));
        }  else {
            // General error for other data integrity violations
            fieldErrors.add(new FieldErrorDetail("unknown", ErrorConstants.DATA_INTEGRITY_VIOLATION, "Data integrity violation occurred"));
        }

        // Create the custom error response
        CustomError errorResponse = CustomError.builder()
                .errors(fieldErrors).build();

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    //MethodArgumentNotValidException

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Method argument not valid validation error: {}", ex.getFieldError());
        BindingResult bindingResult = ex.getBindingResult();

        //building errors
        fieldErrors = bindingResult.getFieldErrors().stream()
                .map(fieldError -> FieldErrorDetail.builder()
                                        .code(ErrorConstants.INVALID_DATA)
                                                .message(fieldError.getDefaultMessage())
                                                        .field(fieldError.getField()).build()

                )
                .collect(Collectors.toList());
        // Build the custom error response
        CustomError errorResponse = CustomError.builder()
                .errors(fieldErrors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // MissingRequestHeaderException
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<CustomError> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        log.error("Missing request header error: {}", ex);
        FieldErrorDetail errorDetail = new FieldErrorDetail(
                ex.getHeaderName(),
                ErrorConstants.MISSING_HEADER,
                "Required request header '" + ex.getHeaderName() + "' is missing."
        );

        CustomError errorResponse = CustomError.builder()
                .errors(List.of(errorDetail))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // MissingServletRequestParameterException
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<CustomError> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error("Missing servlet request parameter exception: {}", ex);

        FieldErrorDetail errorDetail = new FieldErrorDetail(
                ex.getParameterName(),
                ErrorConstants.MISSING_PARAMETER,
                "Required request parameter '" + ex.getParameterName() + "' is missing."
        );

        CustomError errorResponse = CustomError.builder()
                .errors(List.of(errorDetail))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // NoSuchElementException
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<CustomError> handleNoSuchElementException(NoSuchElementException ex) {
        log.error("No such element exception: {}", ex);

        FieldErrorDetail errorDetail = new FieldErrorDetail(
                "element",
                ErrorConstants.NO_SUCH_ELEMENT,
                "The requested element could not be found."
        );

        CustomError errorResponse = CustomError.builder()
                .errors(List.of(errorDetail))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // MethodArgumentTypeMismatchException
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("Method Argument Type Mismatch exception: {}", ex);

        FieldErrorDetail errorDetail = new FieldErrorDetail(
                ex.getName(),
                ErrorConstants.TYPE_MISMATCH,
                "The parameter '" + ex.getName() + "' should be of type '" + ex.getRequiredType().getSimpleName() + "'."
        );

        CustomError errorResponse = CustomError.builder()
                .errors(List.of(errorDetail))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
