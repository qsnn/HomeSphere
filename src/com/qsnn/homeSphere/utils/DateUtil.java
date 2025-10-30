package com.qsnn.homeSphere.utils;

import java.util.Date;

/**
 * 日期工具类
 *
 * <p>提供日期格式转换功能，支持字符串与Date对象之间的相互转换。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>将格式化的日期字符串转换为Date对象</li>
 *   <li>将Date对象格式化为标准日期字符串</li>
 *   <li>验证日期字符串格式的正确性</li>
 * </ul>
 *
 * <p><b>日期格式：</b></p>
 * <ul>
 *   <li>支持格式：yyyy-MM-dd（如：2025-01-15）</li>
 *   <li>年份范围：0000-9999</li>
 *   <li>月份范围：01-12</li>
 *   <li>日期范围：01-31（会根据月份自动验证）</li>
 * </ul>
 *
 * <p><b>注意事项：</b></p>
 * <ul>
 *   <li>使用Java内置Date类，注意其月份从0开始的特性</li>
 *   <li>年份处理：Date对象年份需要+1900，月份需要+1</li>
 *   <li>仅处理日期部分，时间部分默认为00:00:00</li>
 * </ul>
 *
 * @author qsnn
 * @version 2.0
 * @since 2025
 */
public class DateUtil {

    /**
     * 将日期字符串转换为Date对象
     *
     * <p>将格式为"yyyy-MM-dd"的字符串转换为对应的Date对象。</p>
     *
     * <p><b>转换规则：</b></p>
     * <ul>
     *   <li>字符串必须匹配正则表达式：^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])$</li>
     *   <li>月份会自动减1以适应Date类的月份表示（0-11）</li>
     *   <li>年份直接传入，不需要减1900</li>
     * </ul>
     *
     * @param dateStr 日期字符串，格式必须为"yyyy-MM-dd"
     * @return 对应的Date对象
     * @throws IllegalArgumentException 当日期字符串格式不正确时抛出异常
     *
     * @example
     * <pre>
     * Date date = DateUtil.formatStringToDate("2025-01-15");
     * // 返回表示2025年1月15日的Date对象
     * </pre>
     */
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

    /**
     * 将Date对象格式化为日期字符串
     *
     * <p>将Date对象转换为格式为"yyyy-MM-dd"的字符串。</p>
     *
     * <p><b>转换规则：</b></p>
     * <ul>
     *   <li>年份需要加1900（Date类的年份从1900开始）</li>
     *   <li>月份需要加1（Date类的月份从0开始）</li>
     *   <li>日期直接使用</li>
     *   <li>输出格式为四位年份、两位月份、两位日期</li>
     * </ul>
     *
     * @param date 要格式化的Date对象
     * @return 格式化的日期字符串，格式为"yyyy-MM-dd"
     *
     * @example
     * <pre>
     * String dateStr = DateUtil.formatDateToString(new Date(125, 0, 15));
     * // 返回"2025-01-15"
     * </pre>
     */
    public static String formatDateToString(Date date) {
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int day = date.getDate();

        return String.format("%04d-%02d-%02d", year, month, day);
    }
}