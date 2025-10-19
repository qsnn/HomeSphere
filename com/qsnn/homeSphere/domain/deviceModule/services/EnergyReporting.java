package qsnn.homeSphere.domain.deviceModule.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 能源报告接口
 *
 * <p>该接口定义了设备能源消耗报告的相关功能，用于监控和管理设备的能耗情况。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>获取设备的实时功率</li>
 *   <li>计算指定时间范围内的能源消耗</li>
 *   <li>提供基于运行日志的能耗计算</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>使用接口设计，支持多态实现</li>
 *   <li>提供默认方法实现，减少重复代码</li>
 *   <li>支持基于运行日志的精确能耗计算</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public interface EnergyReporting {

    /**
     * 获取设备功率
     *
     * @return 设备功率值，单位：瓦(W)
     */
    double getPower();

    /**
     * 获取设备运行日志列表
     *
     * @return 运行日志列表
     */
    List<RunningLog> getRunningLogs();

    /**
     * 获取设备当前电源状态
     *
     * @return 设备电源状态
     */
    boolean isPowerStatus();


    /**
     * 获取能源消耗报告 - 基于运行日志
     *
     * <p>根据设备的开关机日志精确计算在指定时间范围内的能源消耗量。</p>
     *
     * @param startTime 报告开始时间
     * @param endTime 报告结束时间
     * @return 在指定时间范围内的精确能源消耗量，单位：千瓦时(kWh)
     */
    default double getReport(Date startTime, Date endTime) {
        if (startTime == null || endTime == null || startTime.after(endTime)) {
            return 0.0;
        }

        List<RunningLog> powerLogs = getPowerLogsInPeriod(startTime, endTime);
        double totalEnergy = 0.0;

        // 根据第一个日志类型确定初始状态
        Date currentStart = powerLogs.isEmpty() ?
                (isPowerStatus() ? startTime : null) :
                ("设备开机".equals(powerLogs.get(0).getEvent()) ? null : startTime);

        for (RunningLog log : powerLogs) {
            if ("设备开机".equals(log.getEvent())) {
                currentStart = log.getDateTime();
            } else if ("设备关机".equals(log.getEvent()) && currentStart != null) {
                long runningTime = Math.min(log.getDateTime().getTime(), endTime.getTime())
                        - Math.max(currentStart.getTime(), startTime.getTime());

                if (runningTime > 0) {
                    double hours = runningTime / (1000.0 * 60 * 60);
                    totalEnergy += getPower() * hours / 1000.0;  // 转换为kWh
                }
                currentStart = null;
            }
        }

        // 处理最后一段开机状态
        if (currentStart != null) {
            long runningTime = endTime.getTime() - Math.max(currentStart.getTime(), startTime.getTime());
            if (runningTime > 0) {
                double hours = runningTime / (1000.0 * 60 * 60);
                totalEnergy += getPower() * hours / 1000.0;  // 转换为kWh
            }
        }

        return totalEnergy;
    }

    /**
     * 获取指定时间范围内的开关机日志
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 按时间排序的开关机日志列表
     */
    default List<RunningLog> getPowerLogsInPeriod(Date startTime, Date endTime) {
        return getRunningLogs().stream()
                .filter(log -> ("设备开机".equals(log.getEvent()) || "设备关机".equals(log.getEvent())))
                .filter(log -> !log.getDateTime().before(startTime) && !log.getDateTime().after(endTime))
                .sorted(java.util.Comparator.comparing(RunningLog::getDateTime))
                .collect(Collectors.toList());
    }

    /**
     * 按日统计能耗
     *
     * @param date 统计日期
     * @return 当日能耗值（千瓦时）
     */
    default double getDailyEnergyConsumption(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        Date startTime = cal.getTime();

        cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
        Date endTime = cal.getTime();

        return getReport(startTime, endTime);
    }

    /**
     * 按月统计能耗
     *
     * @param year 年份
     * @param month 月份（1-12）
     * @return 当月能耗值（千瓦时）
     */
    default double getMonthlyEnergyConsumption(int year, int month) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month - 1, 1, 0, 0, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        Date startTime = cal.getTime();

        cal.add(java.util.Calendar.MONTH, 1);
        Date endTime = cal.getTime();

        return getReport(startTime, endTime);
    }

    /**
     * 按年统计能耗
     *
     * @param year 年份
     * @return 当年能耗值（千瓦时）
     */
    default double getYearlyEnergyConsumption(int year) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, 0, 1, 0, 0, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        Date startTime = cal.getTime();

        cal.add(java.util.Calendar.YEAR, 1);
        Date endTime = cal.getTime();

        return getReport(startTime, endTime);
    }
}