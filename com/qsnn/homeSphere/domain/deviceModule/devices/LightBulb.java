package qsnn.homeSphere.domain.deviceModule.devices;

import qsnn.homeSphere.domain.deviceModule.Device;
import qsnn.homeSphere.domain.deviceModule.services.EnergyReporting;
import qsnn.homeSphere.domain.deviceModule.services.Manufacturer;

import java.util.Date;

public class LightBulb extends Device implements EnergyReporting {
    private double power = 100;
    private double brightness;
    private double colorTemp;
    public LightBulb(Integer deviceID, String name,  Manufacturer manufacturer) {
        super(deviceID, name, manufacturer);
    }

    public double getBrightness() {
        return brightness;
    }

    public void setBrightness(double brightness) {
        this.brightness = brightness;
    }

    public double getColorTemp() {
        return colorTemp;
    }

    public void setColorTemp(double colorTemp) {
        this.colorTemp = colorTemp;
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
