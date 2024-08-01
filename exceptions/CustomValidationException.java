package com.java.user.management.exceptions;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class CustomValidationException extends RuntimeException{
    public CustomValidationException(String s) {
        super(s);
    }
}
