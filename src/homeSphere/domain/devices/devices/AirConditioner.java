package homeSphere.domain.devices.devices;

import homeSphere.domain.devices.Device;
import homeSphere.domain.devices.attributes.BooleanAttribute;
import homeSphere.domain.devices.attributes.RangeAttribute;
import homeSphere.domain.devices.attributes.StringChoiceAttribute;
import homeSphere.service.connectService.ConnectMode;
import homeSphere.service.manufacturer.Manufacturer;
import homeSphere.service.powerService.PowerMode;

public class AirConditioner extends Device {
    public AirConditioner(Integer deviceID, String name, String OS, Manufacturer manufacturer, String brand, ConnectMode connectMode, PowerMode powerMode, double power) {
        super(deviceID, name, OS, manufacturer, brand, connectMode, powerMode, power);
        initializeAttributes();
    }

    @Override
    protected void initializeAttributes() {
        // 模式控制
        addAttribute("mode", new StringChoiceAttribute("mode", "AUTO", "AUTO", "WARM", "COOL", "WIND", "DRY"));

        // 温度控制 (16-30度)
        addAttribute("temperature", new RangeAttribute("temperature", 16, 30, 26, "摄氏度"));

        // 风速控制
        addAttribute("fan_speed", new RangeAttribute("fan_speed", 1, 5, 1, "档"));

        // 扫风模式
        addAttribute("swing", new BooleanAttribute("swing", false));

        // 节能模式
        addAttribute("energy_saving", new BooleanAttribute("energy_saving", false));
    }
}
