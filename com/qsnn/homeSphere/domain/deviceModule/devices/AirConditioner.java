package qsnn.homeSphere.domain.deviceModule.devices;

import qsnn.homeSphere.domain.deviceModule.Device;
import qsnn.homeSphere.domain.deviceModule.services.EnergyReporting;
import qsnn.homeSphere.domain.deviceModule.services.Manufacturer;

import java.util.Date;

public class AirConditioner extends Device implements EnergyReporting {
    private double power = 1000;
    private double currTemp;
    private double targetTemp;
    public AirConditioner(Integer deviceID, String name,  Manufacturer manufacturer) {
        super(deviceID, name, manufacturer);
    }

    public double getCurrTemp() {
        return currTemp;
    }

    public void setCurrTemp(double currTemp) {
        this.currTemp = currTemp;
    }

    public double getTargetTemp() {
        return targetTemp;
    }

    public void setTargetTemp(double targetTemp) {
        this.targetTemp = targetTemp;
    }

    @Override
    public double getPower() {
        return power;
    }

    @Override
    public double getReport(Date startTime, Date endTime) {
        return 0;
    }
}
