package homeSphere.domain.devices.airconditioner;

import homeSphere.domain.automationScene.DeviceAction;
import homeSphere.domain.devices.Device;
import homeSphere.domain.devices.OnlineStatusType;
import homeSphere.domain.devices.PowerStatusType;

import java.util.Map;

public class AirConditionerAction implements DeviceAction {
    @Override
    public void execute(Device device, Map<String, Object> parameters) {
        if(!validateParameters(device, parameters)){
            throw new IllegalArgumentException("参数错误");
        }
        AirConditioner a = (AirConditioner) device;
        if((parameters.get("onlineStatus")).equals(OnlineStatusType.ONLINE)) {
            a.connect();
        } else if ((parameters.get("onlineStatus")).equals(OnlineStatusType.OUTLINE)) {
            a.disconnect();
        }

        if ((parameters.get("powerStatus")).equals(PowerStatusType.POWERED)) {
            a.open();
        } else if ((parameters.get("powerStatus")).equals(PowerStatusType.UNPOWERED)) {
            a.close();
        }

        a.setModel((AirConditionerModelController.AirConditionerModelType) parameters.get("model"));
        a.setTemperature((int) parameters.get("temperature"));
    }

    @Override
    public boolean validateParameters(Device device, Map<String, Object> parameters) {
        return device instanceof AirConditioner &&
                parameters != null &&
                parameters.get("model") instanceof AirConditionerModelController.AirConditionerModelType &&
                parameters.get("temperature") instanceof Integer;
    }
}
