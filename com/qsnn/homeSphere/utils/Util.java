package qsnn.homeSphere.utils;

import java.time.Duration;
import java.time.Date;
import java.util.Random;
import java.util.Set;

/**
 * 工具类
 *
 * <p>该类提供系统中常用的通用工具方法，包括ID生成和能耗计算等功能。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>生成唯一的ID标识符</li>
 *   <li>计算设备能耗消耗</li>
 *   <li>提供时间范围相关的辅助方法</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>所有方法均为静态方法，无需实例化</li>
 *   <li>提供重载方法支持不同的计算需求</li>
 *   <li>包含防止无限循环的安全机制</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class Util {

    /**
     * 根据已存在ID集合创建新的唯一ID
     *
     * <p>生成6-9位数字的随机ID，确保不与现有ID重复</p>
     *
     * @param existingIds 已存在的ID集合
     * @return 新的唯一ID，如果无法生成则返回重复的ID
     */
    public static Integer createFreeID(Set<Integer> existingIds) {
        Random random = new Random();
        int maxAttempts = 1000; // 防止无限循环
        int attempts = 0;
        int id = random.nextInt(100000, 1000000000);
        while (attempts < maxAttempts) {
            id = random.nextInt(100000, 1000000000); // 6-9位数字
            if (!existingIds.contains(id)) {
                break;
            }
            attempts++;
        }
        return id;
    }

    /**
     * 计算指定时间段内的总能耗
     *
     * <p>根据设备使用记录，计算在指定时间范围内的总能耗消耗</p>
     *
     * @param usages 设备使用记录集合
     * @param startTime 查询起始时间
     * @param endTime 查询结束时间
     * @return 指定时间段内的总能耗，单位：瓦时(Wh)
     */
    public static double calculatePowerConsumption(Set<Usage> usages, Date startTime, Date endTime) {
        double totalEnergy = 0.0;

        for (Usage usage : usages) {
            Date powerOnTime = usage.getOpenTime();
            Date powerOffTime = usage.getCloseTime();
            double power = usage.getPower();

            // 检查这个使用记录是否在查询时间范围内有重叠
            if (isUsageInTimeRange(powerOnTime, powerOffTime, startTime, endTime)) {
                // 计算实际在查询时间段内的使用时间
                Date actualStart = getLaterTime(powerOnTime, startTime);
                Date actualEnd = getEarlierTime(powerOffTime, endTime);

                // 计算时间差（小时）
                Duration duration = Duration.between(actualStart, actualEnd);
                double hours = duration.toSeconds() / 3600.0; // 精确到秒转换为小时

                // 累加能耗
                totalEnergy += power * hours;
            }
        }

        return totalEnergy;
    }

    /**
     * 计算所有使用记录的总能耗
     *
     * <p>累加所有设备使用记录的能耗值</p>
     *
     * @param usages 设备使用记录集合
     * @return 总能耗，单位：千瓦时(kWh)
     */
    public static double calculatePowerConsumption(Set<Usage> usages) {
        double sum = 0;
        for (Usage usage : usages) {
            sum += usage.getPowerConsumption();
        }
        return sum;
    }

    /**
     * 检查使用记录是否在查询时间范围内有重叠
     *
     * <p>判断设备使用时间段与查询时间段是否有交集</p>
     *
     * @param usageStart 设备使用开始时间
     * @param usageEnd 设备使用结束时间
     * @param queryStart 查询开始时间
     * @param queryEnd 查询结束时间
     * @return 如果有时间重叠返回true，否则返回false
     */
    private static boolean isUsageInTimeRange(Date usageStart, Date usageEnd,
                                              Date queryStart, Date queryEnd) {
        // 使用记录完全在查询时间范围之外的情况
        return !usageEnd.isBefore(queryStart) && !usageStart.isAfter(queryEnd);
    }

    /**
     * 获取两个时间中较晚的一个
     *
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 较晚的时间
     */
    private static Date getLaterTime(Date time1, Date time2) {
        return time1.isAfter(time2) ? time1 : time2;
    }

    /**
     * 获取两个时间中较早的一个
     *
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 较早的时间
     */
    private static Date getEarlierTime(Date time1, Date time2) {
        return time1.isBefore(time2) ? time1 : time2;
    }
}