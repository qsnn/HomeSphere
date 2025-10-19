package qsnn.homeSphere.domain.deviceModule.devices;

import qsnn.homeSphere.domain.deviceModule.Device;
import qsnn.homeSphere.domain.deviceModule.services.Manufacturer;

public class SmartLock extends Device {
    private boolean isLocked;
    private int batteryLevel;
    public SmartLock(Integer deviceID, String name,  Manufacturer manufacturer) {
        super(deviceID, name, manufacturer);
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
}
