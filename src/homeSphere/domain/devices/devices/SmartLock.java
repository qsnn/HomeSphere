package homeSphere.domain.devices.devices;

import homeSphere.domain.devices.Device;
import homeSphere.domain.devices.attributes.BooleanAttribute;
import homeSphere.domain.devices.attributes.StringChoiceAttribute;
import homeSphere.service.connectService.ConnectMode;
import homeSphere.service.manufacturer.Manufacturer;
import homeSphere.service.powerService.PowerMode;

public class SmartLock extends Device {
    public SmartLock(Integer deviceID, String name, String OS, Manufacturer manufacturer, String brand, ConnectMode connectMode, PowerMode powerMode, double power) {
        super(deviceID, name, OS, manufacturer, brand, connectMode, powerMode, power);
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
