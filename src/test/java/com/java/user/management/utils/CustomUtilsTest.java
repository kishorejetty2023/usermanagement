package com.java.user.management.utils;

import com.java.user.management.util.CustomUtils;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.text.ParseException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class CustomUtilsTest {



    @Test
    void testConvertStringDateToSqlDate() throws ParseException {
        String dateInString = "12-25-2020";
        Date sqlDate = CustomUtils.convertStringDateToSqlDate(dateInString);
        assertNotNull(sqlDate);
        assertEquals("2020-12-25", sqlDate.toString());
    }

    @Test
    void testConvertSqlDateToDateString() {
        Date sqlDate = Date.valueOf("2020-12-25");
        String dateString = CustomUtils.convertSqlDateToDateString(sqlDate);
        assertEquals("12-25-2020", dateString);
    }

    @Test
    void testIsValidAdminId() {
        String adminId = Base64.getEncoder().encodeToString("admin123".getBytes());
        assertTrue(CustomUtils.isValidAdminId(adminId, "admin123"));
        assertFalse(CustomUtils.isValidAdminId(adminId, "admin321"));
    }


}
