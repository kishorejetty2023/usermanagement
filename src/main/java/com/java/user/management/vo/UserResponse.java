package com.java.user.management.vo;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserResponse {

    private int id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String dob;
    private String userId;
    private List<Address> addressList;
}
