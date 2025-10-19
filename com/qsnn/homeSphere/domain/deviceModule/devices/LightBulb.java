package qsnn.homeSphere.domain.deviceModule.devices;

import qsnn.homeSphere.domain.deviceModule.Device;
import com.qsnn.homeSphere.domain.deviceModule.attributes.RangeAttribute;
import com.qsnn.homeSphere.domain.deviceModule.attributes.StringChoiceAttribute;
import qsnn.homeSphere.domain.deviceModule.services.Manufacturer;

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
