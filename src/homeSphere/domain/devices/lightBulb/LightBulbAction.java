package homeSphere.domain.devices.lightBulb;

import homeSphere.domain.automationScene.DeviceAction;
import homeSphere.domain.devices.Device;
import homeSphere.domain.devices.OnlineStatus;
import homeSphere.domain.devices.PowerStatus;

import java.util.Map;

public class LightBulbAction implements DeviceAction {
    @Override
    public void execute(Device device, Map<String, Object> parameters) {
        if(!validateParameters(device, parameters)){
            throw new IllegalArgumentException("参数错误");
        }
        LightBulb d = (LightBulb) device;
        if((parameters.get("onlineStatus")).equals(OnlineStatus.OnlineStatusType.ONLINE)) {
            d.connect();
        } else if ((parameters.get("onlineStatus")).equals(OnlineStatus.OnlineStatusType.OUTLINE)) {
            d.disconnect();
        }
        if ((parameters.get("powerStatus")).equals(PowerStatus.PowerStatusType.POWERED)) {
            d.open();
        } else if ((parameters.get("powerStatus")).equals(PowerStatus.PowerStatusType.UNPOWERED)) {
            d.close();
        }
        d.setColorTemperature((ColorTemperatureController.ColorTemperatureType) parameters.get("colorTemperature"));
        d.setLuminance((int) parameters.get("luminance"));
    }

    @Override
    public boolean validateParameters(Device device, Map<String, Object> parameters) {
        return device instanceof LightBulb &&
                parameters != null &&
                parameters.get("onlineStatus") instanceof OnlineStatus.OnlineStatusType &&
                parameters.get("powerStatus") instanceof PowerStatus.PowerStatusType &&
                parameters.get("colorTemperature") instanceof ColorTemperatureController.ColorTemperatureType &&
                parameters.get("luminance") instanceof Integer;
    }
}
