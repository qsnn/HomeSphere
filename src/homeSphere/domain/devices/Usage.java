package homeSphere.domain.devices;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Usage {
    private final String usageID;
    private final double power;
    private final LocalDateTime openTime;
    private final LocalDateTime closeTime;
    private final double powerConsumption;

    public Usage(String usageID, double power, LocalDateTime openTime, LocalDateTime closeTime) {
        this.usageID = usageID;
        this.power = power;
        this.openTime = openTime;
        this.closeTime = closeTime;
        powerConsumption = calculatePower();
    }

    private double calculatePower() {
        // 计算时间差（小时）
        Duration duration = Duration.between(openTime, closeTime);
        double hours = duration.toSeconds() / 3600.0; // 转换为小时
        // 能耗 = 功率 × 时间
        return power * hours / 1000.0;
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
        return "LOG[" + usageID + "] " +
                openTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " - " +
                closeTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " " +
                "功耗：" + powerConsumption + "kWh";
    }
}
