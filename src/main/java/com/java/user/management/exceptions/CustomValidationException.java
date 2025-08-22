package com.java.user.management.exceptions;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomValidationException extends RuntimeException{
    private List<FieldErrorDetail> errors;

    public CustomValidationException(String s) {
        super(s);
    }

}
