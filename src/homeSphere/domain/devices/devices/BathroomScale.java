package homeSphere.domain.devices.devices;

import homeSphere.domain.devices.Device;
import homeSphere.service.connectService.ConnectMode;
import homeSphere.service.manufacturer.Manufacturer;
import homeSphere.service.powerService.PowerMode;

public class BathroomScale extends Device {

    public BathroomScale(Integer deviceID, String name, String OS, Manufacturer manufacturer, String brand, ConnectMode connectMode, PowerMode powerMode, double power) {
        super(deviceID, name, OS, manufacturer, brand, connectMode, powerMode, power);
        initializeAttributes();
    }

    @Override
    protected void initializeAttributes() {}

}
