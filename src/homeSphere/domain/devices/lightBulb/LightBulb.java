package homeSphere.domain.devices.lightBulb;

import homeSphere.domain.devices.Device;
import homeSphere.runningLog.DeviceLog;
import homeSphere.runningLog.LogType;
import homeSphere.service.connectService.Bluetooth;
import homeSphere.service.connectService.WiFi;
import homeSphere.service.manufacturer.Manufacturer;
import homeSphere.service.powerService.MainsPower;

import java.util.Map;

public class LightBulb extends Device
        implements MainsPower,
        ColorTemperatureController, LuminanceController
        ,WiFi, Bluetooth {
    private ColorTemperatureType colorTemperature;  //色温
    private int luminance;  //亮度 1-100

    public LightBulb(String ID, String name, String OS, Manufacturer manufacturer, String brand, double powerConsumption) {
        super(ID, name, OS, manufacturer, brand, powerConsumption);
        colorTemperature = ColorTemperatureType.AUTO;
        luminance = 50;
    }

    @Override
    public void execute(Device device, Map<String, Object> parameters) {
        new LightBulbAction().execute(device, parameters);
    }

    @Override
    public int getLuminance() {
        return luminance;
    }

    @Override
    public void setLuminance(int luminance) {
        if(luminance < 0 || luminance > 100) {
            new DeviceLog(this, power,"设置亮度：" + luminance, LogType.WARNING, "设置亮度超出范围0-100，当前设置亮度：" + luminance);
            return;
        }
        this.luminance = luminance;
    }

    @Override
    public ColorTemperatureType getColorTemperature() {
        return colorTemperature;
    }

    @Override
    public void setColorTemperature(ColorTemperatureType colorTemperature) {
        this.colorTemperature = colorTemperature;
    }
}
