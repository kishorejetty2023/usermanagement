package com.java.user.management.constants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ErrorConstants {
    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String DATA_INTEGRITY_ERROR = "DATA_INTEGRITY_ERROR"; ///DATA_INTEGRITY_VIOLATION
    public static final String MISSING_HEADER = "MISSING_HEADER";
    public static final String TYPE_MISMATCH = "TYPE_MISMATCH";
    public static final String NO_SUCH_ELEMENT = "NO_SUCH_ELEMENT";
    public static final String MISSING_PARAMETER = "MISSING_PARAMETER";
    public static final String INVALID_REFERENCE = "INVALID_REFERENCE";
    public static final String DUPLICATE_KEY ="DUPLICATE_KEY";
    public static final String DATA_INTEGRITY_VIOLATION = "DATA_INTEGRITY_VIOLATION";
    public static final String INVALID_DATA = "INVALID_DATA";


    //Error messages
    public static final String ID_ERROR_MESSAGE = "Id should not be less than 1";
    public static final String INVALID_EMAIL_ERROR_MESSAGE = "Email id is not valid";
    public static final String NULL_FIRST_NAME_ERROR_MESSAGE="First name cannot be null";
    public static final String BLANK_FIRST_NAME_ERROR_MESSAGE="First name cannot be blank";
    public static final String MIN_CHARS_FIRST_NAME_ERROR_MESSAGE= "First name must be at least 2 characters";
    public static final String ALPHA_ONLY_FIRST_NAME_ERROR_MESSAGE="First name cannot contain special character or numbers";
    public static final String NULL_LAST_NAME_ERROR_MESSAGE = "Last name cannot be null";
    public static final String BLANK_LAST_NAME_ERROR_MESSAGE = "Last name cannot be blank";
    public static final String ALPHA_ONLY_LAST_NAME_ERROR_MESSAGE = "Lastname name cannot contain special character or numbers";
    public static final String MIN_CHARS_LAST_NAME_ERROR_MESSAGE ="Last name must be at least 2 characters";
    public static final String NULL_DOB_ERROR_MESSAGE="Date of Birth cannot be null";
    public static final String BLANK_DOB_ERROR_MESSAGE="Date of Birth cannot be blank";
    public static final String NULL_EMAIL_ERROR_MESSAGE= "emailId cannot be null";
    public static final String BLANK_EMAIL_ERROR_MESSAGE= "emailId cannot be blank";
    public static final String INVALID_FORMAT_DOB_ERROR_MESSAGE="not in valid format: Expected mm-dd-yyyy";
    public static final String NULL_USERID_ERROR_MESSAGE ="userId cannot be null";
    public static final String BLANK_USERID_ERROR_MESSAGE = "userId cannot be blank";
    public static final String ALPHANUMERIC_ONLY_USERID_ERROR_MESSAGE = "should be alpha numeric";
    public static final String NULL_ADDRESS_LINE_ONE_ERROR_MESSAGE="Address line one cannot be null";
    public static final String BLANK_ADDRESS_LINE_ONE_ERROR_MESSAGE ="Address line one cannot be blank";
    public static final String NULL_CITY_ERROR_MESSAGE =  "City cannot be null";
    public static final String BLANK_CITY_ERROR_MESSAGE= "City cannot be blank";
    public static final String NULL_STATE_ERROR_MESSAGE= "State cannot be null";
    public static final String BLANK_STATE_ERROR_MESSAGE= "State cannot be blank";
    public static final String NULL_ZIP_ERROR_MESSAGE= "Zip cannot be null";
    public static final String BLANK_ZIP_ERROR_MESSAGE= "Zip cannot be blank";
    public static final String NULL_COUNTRY_ERROR_MESSAGE= "Country cannot be null";
    public static final String BLANK_COUNTRY_ERROR_MESSAGE= "Country cannot be blank";
}
