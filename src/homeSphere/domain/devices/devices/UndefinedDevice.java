package homeSphere.domain.devices.devices;

import homeSphere.domain.devices.Device;
import homeSphere.domain.devices.DeviceAttribute;
import homeSphere.service.manufacturer.Manufacturer;

import java.util.Set;

public class UndefinedDevice extends Device {
    public UndefinedDevice(Integer deviceID, String name, String OS, Manufacturer manufacturer, String brand, double power, Set<DeviceAttribute<?>> attributes) {
        super(deviceID, name, OS, manufacturer, brand, power);
        for (DeviceAttribute<?> attribute : attributes) {
            addAttribute(attribute.getName(), attribute);
        }
    }

    @Override
    protected void initializeAttributes() {

    }
}
