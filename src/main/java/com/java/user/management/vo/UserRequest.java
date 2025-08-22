package com.java.user.management.vo;


import com.java.user.management.constants.ErrorConstants;
import com.java.user.management.constants.UserConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserRequest {

    @NotNull(message = ErrorConstants.NULL_FIRST_NAME_ERROR_MESSAGE)
    @NotBlank(message = ErrorConstants.BLANK_FIRST_NAME_ERROR_MESSAGE)
    @Size(min = 2, message = ErrorConstants.MIN_CHARS_FIRST_NAME_ERROR_MESSAGE)
    @Pattern(regexp = UserConstants.ALPHA, message = ErrorConstants.ALPHA_ONLY_FIRST_NAME_ERROR_MESSAGE)
    private String firstName;

    @NotNull(message = ErrorConstants.NULL_LAST_NAME_ERROR_MESSAGE)
    @NotBlank(message = ErrorConstants.BLANK_LAST_NAME_ERROR_MESSAGE)
    @Size(min = 2, message = ErrorConstants.MIN_CHARS_LAST_NAME_ERROR_MESSAGE)
    @Pattern(regexp = UserConstants.ALPHA, message = ErrorConstants.ALPHA_ONLY_LAST_NAME_ERROR_MESSAGE)
    private String lastName;

    @Pattern(regexp = UserConstants.EMAIL,message =  ErrorConstants.INVALID_EMAIL_ERROR_MESSAGE)
    private String emailId;

    @NotNull(message = ErrorConstants.NULL_DOB_ERROR_MESSAGE)
    @NotBlank(message = ErrorConstants.BLANK_DOB_ERROR_MESSAGE)
    @Pattern(regexp = UserConstants.MM_DD_YYYY_REGEX, message = ErrorConstants.INVALID_FORMAT_DOB_ERROR_MESSAGE)
    private String dob;

    @NotNull(message = ErrorConstants.NULL_USERID_ERROR_MESSAGE)
    @NotBlank(message = ErrorConstants.BLANK_USERID_ERROR_MESSAGE)
    @Pattern(regexp = UserConstants.ALPHANUM, message =ErrorConstants.ALPHANUMERIC_ONLY_USERID_ERROR_MESSAGE)
    private String userId;

    @Valid
    private List<Address> addressList;

}
