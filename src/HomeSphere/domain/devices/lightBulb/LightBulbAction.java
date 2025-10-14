package OopHW.Exp1.domain.devices.lightBulb;

import OopHW.Exp1.domain.automationScene.DeviceAction;
import OopHW.Exp1.domain.devices.Device;
import OopHW.Exp1.domain.devices.OnlineStatusType;
import OopHW.Exp1.domain.devices.PowerStatusType;

import java.util.Map;

public class LightBulbAction implements DeviceAction {
    @Override
    public void execute(Device device, Map<String, Object> parameters) {
        if(!validateParameters(device, parameters)){
            throw new IllegalArgumentException("参数错误");
        }
        LightBulb d = (LightBulb) device;
        if(((OnlineStatusType) parameters.get("onlineStatus")).equals(OnlineStatusType.ONLINE)) {
            d.connect();
        } else if (((OnlineStatusType) parameters.get("onlineStatus")).equals(OnlineStatusType.OUTLINE)) {
            d.disconnect();
        }
        if (((PowerStatusType) parameters.get("powerStatus")).equals(PowerStatusType.POWERED)) {
            d.open();
        } else if (((PowerStatusType) parameters.get("powerStatus")).equals(PowerStatusType.UNPOWERED)) {
            d.close();
        }
        d.setColorTemperature((ColorTemperatureController.ColorTemperatureType) parameters.get("colorTemperature"));
        d.setLuminance((int) parameters.get("luminance"));
    }

    @Override
    public boolean validateParameters(Device device, Map<String, Object> parameters) {
        return device instanceof LightBulb &&
                parameters != null &&
                parameters.get("onlineStatus") instanceof OnlineStatusType &&
                parameters.get("powerStatus") instanceof PowerStatusType &&
                parameters.get("colorTemperature") instanceof ColorTemperatureController.ColorTemperatureType &&
                parameters.get("luminance") instanceof Integer;
    }
}
