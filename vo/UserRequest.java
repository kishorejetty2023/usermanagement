package com.java.user.management.vo;


import com.java.user.management.constants.UserConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequest {

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = UserConstants.ALPHA)
    private String firstName;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = UserConstants.ALPHA)
    private String lastName;

    @Pattern(regexp = UserConstants.EMAIL,message = "not validEmail")
    private String emailId;

    @NotNull(message = "Date of Birth cannot be null")
    @NotBlank(message = "Date of Birth cannot be blank")
    @Pattern(regexp = UserConstants.MM_DD_YYYY_REGEX, message = "not in valid format: Expected mm-dd-yyyy")
    private String dob;

    @NotNull(message = "userId cannot be null")
    @NotBlank(message = "userId cannot be blank")
    @Pattern(regexp = UserConstants.ALPHANUM, message = "should be alpha numeric")
    private String userId;

    private List<Address> addressList;

}
