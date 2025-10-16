package com.qsnn.homeSphere.domain.deviceModule;

import com.qsnn.homeSphere.log.Log;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Usage {
    private final String deviceID;
    private final double power;
    private final LocalDateTime openTime;
    private final LocalDateTime closeTime;
    private final double powerConsumption;
    private final Log log;

    public Usage(String deviceID, double power, LocalDateTime openTime, LocalDateTime closeTime) {
        this.deviceID = deviceID;
        this.power = power;
        this.openTime = openTime;
        this.closeTime = closeTime;
        powerConsumption = calculatePower();
        log = new Log(deviceID, "设备被使用", Log.LogType.INFO, this.toString());
    }

    private double calculatePower() {
        // 计算时间差（小时）
        Duration duration = Duration.between(openTime, closeTime);
        double hours = duration.toSeconds() / 3600.0; // 转换为小时
        // 能耗 = 功率 × 时间
        return power * hours / 1000.0;
    }

    public double getPower() {
        return power;
    }

    public LocalDateTime getOpenTime() {
        return openTime;
    }
    public LocalDateTime getCloseTime() {
        return closeTime;
    }
    public double getPowerConsumption() {
        return powerConsumption;
    }

    public String toString() {
        return "LOG[" + deviceID + "] " +
                openTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " - " +
                closeTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " " +
                "功耗：" + powerConsumption + "kWh";
    }

    public Log getLog() {
        return log;
    }
}
