package com.designershop.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeFormatUtil {

    public static final DateTimeFormatter SIMPLE_YEAR_MONTH = DateTimeFormatter.ofPattern("yyyyMM");
    public static final DateTimeFormatter SIMPLE_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter SIMPLE_DATE_DASH = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter SIMPLE_DATE_SLASH = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    public static final DateTimeFormatter SIMPLE_TIME = DateTimeFormatter.ofPattern("HHmmss");
    public static final DateTimeFormatter SIMPLE_TIME_COLON = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter FULL_DATE_TIME = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter FULL_DATE_DASH_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter FULL_DATE_SLASH_TIME = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static LocalDateTime currentDateTime() {
        return LocalDateTime.now();
    }

    public static String currentLocalDateFormat() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.format(SIMPLE_YEAR_MONTH);
    }

    public static LocalDateTime localDateTimeFormat(String date) {
        return LocalDateTime.from(FULL_DATE_DASH_TIME.parse(date));
    }

    public static String localDateTimeFormat(LocalDateTime localDateTime) {
        return localDateTime.format(FULL_DATE_DASH_TIME);
    }
}
