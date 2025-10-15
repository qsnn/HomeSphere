package homeSphere.domain.devices.devices;

import homeSphere.domain.devices.Device;
import homeSphere.domain.devices.attributes.BooleanAttribute;
import homeSphere.domain.devices.attributes.RangeAttribute;
import homeSphere.domain.devices.attributes.StringChoiceAttribute;
import homeSphere.service.connectService.Bluetooth;
import homeSphere.service.connectService.WiFi;
import homeSphere.service.manufacturer.Manufacturer;
import homeSphere.service.powerService.MainsPower;

public class AirConditioner extends Device implements MainsPower, WiFi, Bluetooth {
    public AirConditioner(Integer deviceID, String name, String OS, Manufacturer manufacturer, double power) {
        super(deviceID, name, OS, manufacturer, manufacturer.getName(), power);
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
