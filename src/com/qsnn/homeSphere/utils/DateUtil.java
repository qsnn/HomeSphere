package com.qsnn.homeSphere.utils;

import java.util.Date;

public class DateUtil {
    public static Date formatStringToDate(String dateStr) {
        // 使用正则表达式验证格式
        String dateRegex = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
        if (!dateStr.matches(dateRegex)) {
            throw new IllegalArgumentException("日期格式错误，请按照 yyyy-MM-dd 格式输入");
        }

        return new Date(
                Integer.parseInt(dateStr.split("-")[0]),
                Integer.parseInt(dateStr.split("-")[1]) - 1,
                Integer.parseInt(dateStr.split("-")[2])
        );

    }
}
