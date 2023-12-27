package com.java.h2.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {

    private int id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String dob;
    private String userId;
}
