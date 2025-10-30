package com.qsnn.homeSphere.utils;

import java.util.Date;

public class DateUtil {
    public static Date formatStringToDate(String dateStr) {
        String dateRegex = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
        if (!dateStr.matches(dateRegex)) {
            throw new IllegalArgumentException("日期格式错误，请按照 yyyy-MM-dd 格式输入");
        }

        String[] parts = dateStr.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]) - 1;  // 月份减1
        int day = Integer.parseInt(parts[2]);

        // 年份直接传入，不需要减1900
        return new Date(year, month, day);
    }

    public static String formatDateToString(Date date) {
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int day = date.getDate();

        return String.format("%04d-%02d-%02d", year, month, day);
    }
}
