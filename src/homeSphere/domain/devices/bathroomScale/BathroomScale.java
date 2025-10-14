package homeSphere.domain.devices.bathroomScale;

import homeSphere.domain.devices.Device;
import homeSphere.service.connectService.WiFi;
import homeSphere.service.manufacturer.Manufacturer;
import homeSphere.service.powerService.Battery;

import java.util.Map;

public class BathroomScale extends Device implements Battery, WiFi {

    public BathroomScale(String ID, String name, String OS, Manufacturer manufacturer, String brand, double powerConsumption) {
        super(ID, name, OS, manufacturer, brand, powerConsumption);
    }
    @Override
    public void execute(Device device, Map<String, Object> parameters) {
        new BathroomScaleAction().execute(device, parameters);
    }


}
