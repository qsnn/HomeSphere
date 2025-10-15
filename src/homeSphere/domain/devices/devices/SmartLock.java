package homeSphere.domain.devices.devices;

import homeSphere.domain.devices.Device;
import homeSphere.domain.devices.attributes.BooleanAttribute;
import homeSphere.domain.devices.attributes.StringChoiceAttribute;
import homeSphere.service.connectService.Zigbee;
import homeSphere.service.manufacturer.Manufacturer;
import homeSphere.service.powerService.Battery;
import homeSphere.service.powerService.MainsPower;

public class SmartLock extends Device implements MainsPower, Battery, Zigbee {
    public SmartLock(Integer deviceID, String name, String OS, Manufacturer manufacturer, double power) {
        super(deviceID, name, OS, manufacturer, manufacturer.getName(), power);
        initializeAttributes();
    }

    @Override
    protected void initializeAttributes() {
        // 供电模式
        addAttribute("powerMode", new StringChoiceAttribute("powerMode", "BATTERY", "MAINSPOWER", "BATTERY"));

        // 上锁控制
        addAttribute("lockStatus", new BooleanAttribute("luminance", false));

    }

}
