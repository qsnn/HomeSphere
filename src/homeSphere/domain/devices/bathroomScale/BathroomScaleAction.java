package homeSphere.domain.devices.bathroomScale;

import homeSphere.domain.automationScene.DeviceAction;
import homeSphere.domain.devices.Device;
import homeSphere.domain.devices.OnlineStatus;
import homeSphere.domain.devices.PowerStatus;

import java.util.Map;

public class BathroomScaleAction implements DeviceAction {
    @Override
    public void execute(Device device, Map<String, Object> parameters) {
        if(!validateParameters(device, parameters)){
            throw new IllegalArgumentException("参数错误");
        }
        BathroomScale d = (BathroomScale) device;
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

    }

    @Override
    public boolean validateParameters(Device device, Map<String, Object> parameters) {
        return device instanceof BathroomScale &&
                parameters != null &&
                parameters.get("onlineStatus") instanceof OnlineStatus.OnlineStatusType &&
                parameters.get("powerStatus") instanceof PowerStatus.PowerStatusType;
    }
}
