package qsnn.homeSphere.domain.deviceModule.services;

import java.time.Date;

public interface EnergyReporting {
    double getPower();
    double getReport(Date startTime, Date endTime);
}
