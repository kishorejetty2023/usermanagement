package com.java.user.management.util;


import com.java.user.management.constants.UserConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CustomUtils {

    // Regular expression to match strings containing both letters and numbers
    public static final String ALPHANUMERIC = ".*[a-zA-Z].*\\d.*|.*\\d.*[a-zA-Z].*";

    // Define the regular expression for basic email validation
    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";

    public static boolean isAlphanumeric(String input) {

        // Create a Pattern object
        Pattern pattern = Pattern.compile(ALPHANUMERIC);

        // Use Matcher to match the input against the pattern
        return pattern.matcher(input).matches();
    }

    public static boolean containsOnlyWords(String str) {
        // Regex for containing only words (letters and spaces)
        String regex = "^[a-zA-Z ]+$";
        return str.matches(regex);
    }

    public static boolean validateEmail(String email) {

        // Create a Pattern object
        Pattern pattern = Pattern.compile(EMAIL_REGEX);

        // Create a Matcher object
        Matcher matcher = pattern.matcher(email);

        // Perform the matching and return the result
        return matcher.matches();
    }

    /**
     * This is a sample method that adds two numbers.
     *
     * @param dateInString Takes date as string in the format dd-MM-yyyy.
     *
     * @return The sum of the two numbers.
     */
    public static Date convetStringDateToSqlDate(String dateInString) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat(UserConstants.DATE_FORMAT, Locale.ENGLISH);
        java.util.Date date = formatter.parse(dateInString);
        // Create a LocalDate object from the java.util.Date object
        LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());

        log.info("SQL Date: {}", Date.valueOf(localDate));

        // Create a java.sql.Date object from the LocalDate object
        return Date.valueOf(localDate);

    }

    public static String convertSqlDateToDateString(Date sqlDate) {
        // Use the getTime() method to obtain the milliseconds since epoch
        long milliseconds = sqlDate.getTime();

        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat sdf = new SimpleDateFormat(UserConstants.DATE_FORMAT);

        // Create a java.util.Date object using the milliseconds
        // Convert the Date object to a string
        String strDate = sdf.format(new java.util.Date(milliseconds));
        log.info("Java Date in String: {}",strDate);
        return strDate;
    }

    public static boolean isValidAdminId(String adminId,String appAdminId) {
        byte[] decodedBytes = Base64.getDecoder().decode(adminId);
        return StringUtils.equals(appAdminId,new String(decodedBytes));
    }

    public static java.util.Date convertToDate(String dateInString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        java.util.Date dt = formatter.parse(dateInString);
        log.info("String date vs actual date : {} :::: {} ",dateInString,dt);
        return dt;

    }
}
