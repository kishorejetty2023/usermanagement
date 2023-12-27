package com.java.h2.user.exceptions;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CustomError {

    private String errorMsg;
    private String errorCode;
}
