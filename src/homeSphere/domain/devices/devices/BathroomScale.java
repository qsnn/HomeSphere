package homeSphere.domain.devices.devices;

import homeSphere.domain.devices.Device;
import homeSphere.service.connectService.WiFi;
import homeSphere.service.manufacturer.Manufacturer;
import homeSphere.service.powerService.Battery;

public class BathroomScale extends Device implements Battery, WiFi {

    public BathroomScale(Integer deviceID, String name, String OS, Manufacturer manufacturer, double power) {
        super(deviceID, name, OS, manufacturer, manufacturer.getName(), power);
        initializeAttributes();
    }

    @Override
    protected void initializeAttributes() {}

}
