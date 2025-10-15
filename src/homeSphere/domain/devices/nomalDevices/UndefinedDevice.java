package homeSphere.domain.devices.nomalDevices;

import homeSphere.domain.devices.Device;
import homeSphere.service.manufacturer.Manufacturer;

public class UndefinedDevice extends Device {
    public UndefinedDevice(String deviceID, String name, String OS, Manufacturer manufacturer, String brand, double power) {
        super(deviceID, name, OS, manufacturer, brand, power);
    }
}
