package homeSphere.domain.deviceModule.devices;

import homeSphere.domain.deviceModule.Device;
import homeSphere.domain.deviceModule.Manufacturer;

public class BathroomScale extends Device {

    public BathroomScale(Integer deviceID, String name, String OS, Manufacturer manufacturer, String brand, ConnectMode connectMode, PowerMode powerMode, double power) {
        super(deviceID, name, OS, manufacturer, brand, connectMode, powerMode, power);
        initializeAttributes();
    }

    @Override
    protected void initializeAttributes() {}

}
