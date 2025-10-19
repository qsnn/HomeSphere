package com.qsnn.homeSphere.domain.deviceModule;

import com.qsnn.homeSphere.log.Log;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 设备使用记录类
 *
 * <p>该类用于记录单个设备的使用情况，包括使用时间、功率消耗等信息。
 * 每次设备使用都会自动生成对应的日志记录。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>记录设备的开启和关闭时间</li>
 *   <li>计算设备使用的能耗</li>
 *   <li>自动生成使用日志</li>
 * </ul>
 *
 * <p><b>设计说明：</b></p>
 * <ul>
 *   <li>使用组合关系包含Log对象，符合单一职责原则</li>
 *   <li>所有字段均为final，确保对象不可变性</li>
 *   <li>能耗计算在构造时完成，避免重复计算</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class Usage {

    /** 设备唯一标识符 */
    private final String deviceID;

    /** 设备功率，单位：瓦特(W) */
    private final double power;

    /** 设备开启时间 */
    private final LocalDateTime openTime;

    /** 设备关闭时间 */
    private final LocalDateTime closeTime;

    /** 能耗计算结果，单位：千瓦时(kWh) */
    private final double powerConsumption;

    /** 设备使用日志记录 */
    private final Log log;

    /**
     * 构造设备使用记录
     *
     * <p>在创建使用记录时，会自动计算能耗并生成对应的日志记录。</p>
     *
     * @param deviceID 设备ID，不能为null或空
     * @param power 设备功率，单位：瓦特(W)，必须大于0
     * @param openTime 开启时间，不能为null且必须在closeTime之前
     * @param closeTime 关闭时间，不能为null且必须在openTime之后
     * @throws IllegalArgumentException 如果参数不满足条件
     * @throws NullPointerException 如果必要参数为null
     */
    public Usage(String deviceID, double power, LocalDateTime openTime, LocalDateTime closeTime) {
        // 参数校验
        validateParameters(deviceID, power, openTime, closeTime);

        this.deviceID = deviceID;
        this.power = power;
        this.openTime = openTime;
        this.closeTime = closeTime;

        // 计算能耗（在构造时完成，避免重复计算）
        this.powerConsumption = calculatePowerConsumption();

        // 自动生成使用日志
        this.log = new Log(deviceID, "设备被使用", Log.LogType.INFO, this.toString());
    }

    /**
     * 参数校验方法
     *
     * @param deviceID 设备ID
     * @param power 设备功率
     * @param openTime 开启时间
     * @param closeTime 关闭时间
     * @throws IllegalArgumentException 如果参数不合法
     * @throws NullPointerException 如果必要参数为null
     */
    private void validateParameters(String deviceID, double power, LocalDateTime openTime, LocalDateTime closeTime) {
        if (deviceID == null || deviceID.trim().isEmpty()) {
            throw new IllegalArgumentException("设备ID不能为空");
        }
        if (power <= 0) {
            throw new IllegalArgumentException("设备功率必须大于0");
        }
        if (openTime == null) {
            throw new NullPointerException("开启时间不能为null");
        }
        if (closeTime == null) {
            throw new NullPointerException("关闭时间不能为null");
        }
        if (!closeTime.isAfter(openTime)) {
            throw new IllegalArgumentException("关闭时间必须在开启时间之后");
        }
    }

    /**
     * 计算设备能耗
     *
     * <p>计算公式：能耗(kWh) = 功率(W) × 使用时间(小时) ÷ 1000</p>
     * <p>使用时间通过计算开启时间和关闭时间的时间差获得，精确到秒</p>
     *
     * @return 能耗值，单位：千瓦时(kWh)
     */
    private double calculatePowerConsumption() {
        // 计算时间差（秒）
        Duration duration = Duration.between(openTime, closeTime);
        double hours = duration.toSeconds() / 3600.0; // 转换为小时

        // 能耗 = 功率 × 时间 ÷ 1000 (转换为kWh)
        return power * hours / 1000.0;
    }

    // ==================== Getter 方法 ====================
    /**
     * 获取设备ID
     *
     * @return 设备唯一标识符
     */
    public String getDeviceID() {
        return deviceID;
    }

    /**
     * 获取设备功率
     *
     * @return 设备功率，单位：瓦特(W)
     */
    public double getPower() {
        return power;
    }

    /**
     * 获取设备开启时间
     *
     * @return 开启时间
     */
    public LocalDateTime getOpenTime() {
        return openTime;
    }

    /**
     * 获取设备关闭时间
     *
     * @return 关闭时间
     */
    public LocalDateTime getCloseTime() {
        return closeTime;
    }

    /**
     * 获取能耗计算结果
     *
     * @return 能耗值，单位：千瓦时(kWh)
     */
    public double getPowerConsumption() {
        return powerConsumption;
    }

    /**
     * 获取设备使用日志
     *
     * @return 对应的日志记录对象
     */
    public Log getLog() {
        return log;
    }

    /**
     * 获取使用时长
     *
     * @return 使用时长，单位：小时
     */
    public double getUsageDurationInHours() {
        Duration duration = Duration.between(openTime, closeTime);
        return duration.toSeconds() / 3600.0;
    }

    // ==================== 重写方法 ====================

    /**
     * 返回格式化的使用记录信息
     *
     * <p>格式：LOG[设备ID] 开始时间 - 结束时间 功耗：能耗值kWh</p>
     * <p>示例：LOG[AC001] 2024-01-15T10:30:00 - 2024-01-15T12:45:00 功耗：2.75kWh</p>
     *
     * @return 格式化的字符串表示
     */
    @Override
    public String toString() {
        return "LOG[" + deviceID + "] " +
                openTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " - " +
                closeTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " " +
                "功耗：" + String.format("%.3f", powerConsumption) + "kWh";
    }

    /**
     * 比较两个使用记录是否相等
     *
     * <p>基于设备ID、开启时间、关闭时间和功率进行比较</p>
     *
     * @param obj 要比较的对象
     * @return 如果相等返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Usage usage = (Usage) obj;
        return Double.compare(usage.power, power) == 0 &&
                Double.compare(usage.powerConsumption, powerConsumption) == 0 &&
                deviceID.equals(usage.deviceID) &&
                openTime.equals(usage.openTime) &&
                closeTime.equals(usage.closeTime);
    }

}