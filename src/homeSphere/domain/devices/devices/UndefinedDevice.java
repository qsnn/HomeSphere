package homeSphere.domain.devices.devices;

import homeSphere.domain.devices.Device;
import homeSphere.domain.devices.DeviceAttribute;
import homeSphere.service.connectService.ConnectMode;
import homeSphere.service.manufacturer.Manufacturer;
import homeSphere.service.powerService.PowerMode;

import java.util.Set;

public class UndefinedDevice extends Device {
    public UndefinedDevice(Integer deviceID, String name, String OS, Manufacturer manufacturer, String brand, ConnectMode connectMode, PowerMode powerMode, double power, Set<DeviceAttribute<?>> attributes ) {
        super(deviceID, name, OS, manufacturer, brand, connectMode, powerMode, power);
        for (DeviceAttribute<?> attribute : attributes) {
            addAttribute(attribute.getName(), attribute);
        }
    }

    @Override
    protected void initializeAttributes() {

    }
}
