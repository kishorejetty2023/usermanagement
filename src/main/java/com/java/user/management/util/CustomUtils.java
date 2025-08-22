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

@Slf4j
public class CustomUtils {

    /**
     * This method helps in converting string Date to SQL Date.
     *
     * @param dateInString Takes date as string in the format MM-dd-yyyy.
     *
     * @return Date - converted SQL date
     */
    public static Date convertStringDateToSqlDate(String dateInString) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat(UserConstants.DATE_FORMAT, Locale.ENGLISH);
        java.util.Date date = formatter.parse(dateInString);
        // Create a LocalDate object from the java.util.Date object
        LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());

        log.info("SQL Date: {}", Date.valueOf(localDate));

        // Create a java.sql.Date object from the LocalDate object
        return Date.valueOf(localDate);

    }
    /**
     * This method helps in converting string Date to SQL Date.
     *
     * @param sqlDate Takes sqlDate as input.
     *
     * @return String Date  - in the format MM-dd-yyyy
     */
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

    /**
     * This method helps in converting string Date to SQL Date.
     *
     * @param xAdminId,adminId as input.
     *
     * @return true ir false based on appAdminId passed
     */
    public static boolean isValidAdminId(String xAdminId,String adminId) {

        byte[] decodedBytes = Base64.getDecoder().decode(xAdminId);
        log.info("Decoded String from Yml :{}",new String(decodedBytes));
        return StringUtils.equals(adminId,new String(decodedBytes));
    }
}
