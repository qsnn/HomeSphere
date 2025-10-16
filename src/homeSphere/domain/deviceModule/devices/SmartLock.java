package homeSphere.domain.deviceModule.devices;

import homeSphere.domain.deviceModule.Device;
import homeSphere.domain.deviceModule.attributes.BooleanAttribute;
import homeSphere.domain.deviceModule.attributes.StringChoiceAttribute;
import homeSphere.domain.deviceModule.Manufacturer;

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
