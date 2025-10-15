package homeSphere.domain.devices.nomalDevices;

import homeSphere.domain.devices.Device;
import homeSphere.domain.devices.attributes.BooleanAttribute;
import homeSphere.domain.devices.attributes.RangeAttribute;
import homeSphere.service.manufacturer.Manufacturer;

public class AirConditioner extends Device {
    public AirConditioner(Integer deviceID, String name, Manufacturer manufacturer, double power) {
        super(deviceID, name, "AC_OS", manufacturer, manufacturer.getName(), power);
    }

//    @Override
//    protected void initializeAttributes() {
////        // 模式控制
////        addAttribute("mode", new EnumAttribute<>("mode", AirConditionerMode.class, AirConditionerMode.AUTO));
////
////        // 温度控制 (16-30度)
////        addAttribute("temperature", new RangeAttribute("temperature", 16, 30, 26, "°C"));
////
////        // 风速控制
////        addAttribute("fan_speed", new EnumAttribute<>("fan_speed", FanSpeed.class, FanSpeed.AUTO));
////
////        // 扫风模式
////        addAttribute("swing", new BooleanAttribute("swing", false));
////
////        // 节能模式
////        addAttribute("energy_saving", new BooleanAttribute("energy_saving", false));
////
////        // 定时开关
////        addAttribute("timer", new RangeAttribute("timer", 0, 24, 0, "小时"));
//    }
}
