package com.java.user.management.exceptions;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class FieldErrorDetail {
    private String field;
    private String code;
    private String message;
}
