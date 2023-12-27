package com.java.h2.user.exceptions;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class CustomValidationException extends RuntimeException{
    public CustomValidationException(String s) {
        super(s);
    }
}
