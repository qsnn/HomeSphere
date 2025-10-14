package OopHW.Exp1.domain.devices.bathroomScale;

import OopHW.Exp1.domain.devices.Device;
import OopHW.Exp1.service.connectService.WiFi;
import OopHW.Exp1.service.manufacturer.Manufacturer;
import OopHW.Exp1.service.powerService.Battery;

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
