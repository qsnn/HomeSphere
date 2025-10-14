package OopHW.Exp1.domain.devices.bathroomScale;

import OopHW.Exp1.domain.automationScene.DeviceAction;
import OopHW.Exp1.domain.devices.Device;
import OopHW.Exp1.domain.devices.OnlineStatusType;
import OopHW.Exp1.domain.devices.PowerStatusType;

import java.util.Map;

public class BathroomScaleAction implements DeviceAction {
    @Override
    public void execute(Device device, Map<String, Object> parameters) {
        if(!validateParameters(device, parameters)){
            throw new IllegalArgumentException("参数错误");
        }
        BathroomScale d = (BathroomScale) device;
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

    }

    @Override
    public boolean validateParameters(Device device, Map<String, Object> parameters) {
        return device instanceof BathroomScale &&
                parameters != null &&
                parameters.get("onlineStatus") instanceof OnlineStatusType&&
                parameters.get("powerStatus") instanceof PowerStatusType;
    }
}
