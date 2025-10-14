package homeSphere.domain.devices;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Usage {
    private final String usageID;
    private final Device device;
    private final LocalDateTime openTime;
    private final LocalDateTime closeTime;
    private final double powerConsumption;

    public Usage(String usageID, Device device, LocalDateTime openTime, LocalDateTime closeTime) {
        this.usageID = usageID;
        this.device = device;
        this.openTime = openTime;
        this.closeTime = closeTime;
        powerConsumption = calculatePower();
    }

    private double calculatePower() {
        // 计算时间差（小时）
        Duration duration = Duration.between(openTime, closeTime);
        double hours = duration.toSeconds() / 3600.0; // 转换为小时
        // 能耗 = 功率 × 时间
        return device.getPower() * hours / 1000.0;
    }

    public Device getDevice() {
        return device;
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
        // 输出示例：LOG[LOG20240115143025123](客厅电视1534) 2024-01-15T14:30:25 INFO 设备开启 - 客厅灯光已开启 功率：220W
        return "LOG[" + usageID + "] " +
                openTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " - " +
                closeTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " " +
                "功耗：" + powerConsumption + "kWh";
    }
}
