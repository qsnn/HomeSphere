package qsnn.homeSphere.domain.deviceModule.devices;

import qsnn.homeSphere.domain.deviceModule.Device;
import qsnn.homeSphere.domain.deviceModule.services.Manufacturer;

public class BathroomScale extends Device {
    private double bodyMass;
    private int batteryLevel;
    public BathroomScale(Integer deviceID, String name,  Manufacturer manufacturer) {
        super(deviceID, name, manufacturer);
    }

    public double getBodyMass() {
        return bodyMass;
    }

    public void setBodyMass(double bodyMass) {
        this.bodyMass = bodyMass;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
}
