package qsnn.homeSphere.domain.deviceModule.services;


import java.util.Date;

public interface EnergyReporting {
    double getPower();
    default double getReport(Date startTime, Date endTime) {
        // 计算时间差（毫秒）
        long timeDiff = endTime.getTime() - startTime.getTime();

        // 将毫秒转换为小时
        double hours = timeDiff / (1000.0 * 60 * 60);

        // 计算用电量：功率(kW) × 时间(小时)
        return getPower() * hours;
    }
}
