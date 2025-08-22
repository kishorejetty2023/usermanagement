package com.java.user.management.vo;

import com.java.user.management.constants.ErrorConstants;
import com.java.user.management.constants.UserConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserUpdateRequest {

    @Min(value = 1, message = ErrorConstants.ID_ERROR_MESSAGE)
    private int id;


    private String userId;

    @NotNull(message = ErrorConstants.NULL_EMAIL_ERROR_MESSAGE)
    @NotBlank(message = ErrorConstants.BLANK_EMAIL_ERROR_MESSAGE)
    @Pattern(regexp = UserConstants.EMAIL,message = ErrorConstants.INVALID_EMAIL_ERROR_MESSAGE)
    private String emailId;

}
