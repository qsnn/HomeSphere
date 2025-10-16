package homeSphere.utils;

import homeSphere.domain.deviceModule.Usage;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;

public class Util {
    //根据已存在ID集合创建新的ID
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

    public static double calculatePowerConsumption(Set<Usage> usages, LocalDateTime startTime, LocalDateTime endTime) {
        double totalEnergy = 0.0;

        for (Usage usage : usages) {
            LocalDateTime powerOnTime = usage.getOpenTime();
            LocalDateTime powerOffTime = usage.getCloseTime();
            double power = usage.getPower();

            // 检查这个使用记录是否在查询时间范围内有重叠
            if (isUsageInTimeRange(powerOnTime, powerOffTime, startTime, endTime)) {
                // 计算实际在查询时间段内的使用时间
                LocalDateTime actualStart = getLaterTime(powerOnTime, startTime);
                LocalDateTime actualEnd = getEarlierTime(powerOffTime, endTime);

                // 计算时间差（小时）
                Duration duration = Duration.between(actualStart, actualEnd);
                double hours = duration.toSeconds() / 3600.0; // 精确到秒转换为小时

                // 累加能耗
                totalEnergy += power * hours;
            }
        }

        return totalEnergy;
    }

    public static double calculatePowerConsumption(Set<Usage> usages) {
        double sum = 0;
        for (Usage usage : usages) {
            sum += usage.getPowerConsumption();
        }
        return sum;
    }
    /**
     * 检查使用记录是否在查询时间范围内有重叠
     */
    private static boolean isUsageInTimeRange(LocalDateTime usageStart, LocalDateTime usageEnd,
                                              LocalDateTime queryStart, LocalDateTime queryEnd) {
        // 使用记录完全在查询时间范围之外的情况
        return !usageEnd.isBefore(queryStart) && !usageStart.isAfter(queryEnd);
    }

    /**
     * 获取两个时间中较晚的一个
     */
    private static LocalDateTime getLaterTime(LocalDateTime time1, LocalDateTime time2) {
        return time1.isAfter(time2) ? time1 : time2;
    }

    /**
     * 获取两个时间中较早的一个
     */
    private static LocalDateTime getEarlierTime(LocalDateTime time1, LocalDateTime time2) {
        return time1.isBefore(time2) ? time1 : time2;
    }
}
