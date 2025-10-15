package homeSphere.domain.devices.devices;

import homeSphere.domain.devices.Device;
import homeSphere.domain.devices.attributes.RangeAttribute;
import homeSphere.domain.devices.attributes.StringChoiceAttribute;
import homeSphere.service.connectService.Bluetooth;
import homeSphere.service.connectService.WiFi;
import homeSphere.service.manufacturer.Manufacturer;
import homeSphere.service.powerService.MainsPower;

public class LightBulb extends Device implements MainsPower, WiFi, Bluetooth {
    public LightBulb(Integer deviceID, String name, String OS, Manufacturer manufacturer, double power) {
        super(deviceID, name, OS, manufacturer, manufacturer.getName(), power);
    }

    @Override
    protected void initializeAttributes() {
        // 色温
        addAttribute("colorTemperature", new StringChoiceAttribute("colorTemperature", "WARM", "WARM", "COOL", "NORMAL"));

        // 光强控制 (0-100%)
        addAttribute("luminance", new RangeAttribute("luminance", 0, 100, 10, "%"));

    }
}
