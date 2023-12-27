package com.java.h2.user.constants;

public class UserConstants {
    private UserConstants() { }

    public static final String ALPHA   = "^[a-zA-Z ]+$";
    public static final String ALPHANUM = ".*[a-zA-Z].*\\d.*|.*\\d.*[a-zA-Z].*";
    public static final String MM_DD_YYYY_REGEX = "^(0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])[-](19|20)\\d\\d$";
    public static final String EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";

    public static final String DATE_FORMAT = "MM-dd-yyyy";
}
