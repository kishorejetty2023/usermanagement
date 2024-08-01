package com.java.user.management.exceptions;


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
