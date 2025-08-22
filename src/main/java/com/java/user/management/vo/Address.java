package com.java.user.management.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.java.user.management.constants.ErrorConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @JsonIgnore
    private String id;
    @NotNull(message = ErrorConstants.NULL_ADDRESS_LINE_ONE_ERROR_MESSAGE)
    @NotBlank(message = ErrorConstants.BLANK_ADDRESS_LINE_ONE_ERROR_MESSAGE)
    private String addLineOne;
    private String addLineTwo;
    @NotNull(message = ErrorConstants.NULL_CITY_ERROR_MESSAGE)
    @NotBlank(message = ErrorConstants.BLANK_CITY_ERROR_MESSAGE)
    private String city;
    @NotNull(message = ErrorConstants.NULL_STATE_ERROR_MESSAGE)
    @NotBlank(message = ErrorConstants.BLANK_STATE_ERROR_MESSAGE)
    private String state;
    @NotNull(message = ErrorConstants.NULL_ZIP_ERROR_MESSAGE)
    @NotBlank(message =ErrorConstants.BLANK_ZIP_ERROR_MESSAGE)
    private String zip;
    @NotNull(message = ErrorConstants.NULL_COUNTRY_ERROR_MESSAGE)
    @NotBlank(message = ErrorConstants.BLANK_COUNTRY_ERROR_MESSAGE)
    private String country;
    private boolean permanent;
    private boolean temp;

}
