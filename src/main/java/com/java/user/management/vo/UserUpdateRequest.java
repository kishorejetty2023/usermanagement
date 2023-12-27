package com.java.user.management.vo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserUpdateRequest {

    @Min(value = 1, message = "Id should not be less than 1")
    private int id;

    @NotNull(message = "emailId cannot be null")
    @NotBlank(message = "emailId cannot be blank")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$",message = "not validEmail")
    private String emailId;

}
