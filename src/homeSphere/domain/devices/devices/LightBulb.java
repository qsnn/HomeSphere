package homeSphere.domain.devices.devices;

import homeSphere.domain.devices.Device;
import homeSphere.domain.devices.attributes.RangeAttribute;
import homeSphere.domain.devices.attributes.StringChoiceAttribute;
import homeSphere.service.connectService.ConnectMode;
import homeSphere.service.manufacturer.Manufacturer;
import homeSphere.service.powerService.PowerMode;

public class LightBulb extends Device {
    public LightBulb(Integer deviceID, String name, String OS, Manufacturer manufacturer, String brand, ConnectMode connectMode, PowerMode powerMode, double power) {
        super(deviceID, name, OS, manufacturer, brand, connectMode, powerMode, power);
        initializeAttributes();
    }

    @Override
    protected void initializeAttributes() {
        // 色温
        addAttribute("colorTemperature", new StringChoiceAttribute("colorTemperature", "WARM", "WARM", "COOL", "NORMAL"));

        // 光强控制 (0-100%)
        addAttribute("luminance", new RangeAttribute("luminance", 0, 100, 10, "%"));

    }
}
