package com.nekolr.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 日期时间工具
 *
 * @author nekolr
 */
public class DateTimeUtils {

    private DateTimeUtils() {

    }

    /**
     * 获取当前日期时间
     *
     * @return
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static LocalTime getCurrentTime() {
        return LocalTime.now();
    }
}
