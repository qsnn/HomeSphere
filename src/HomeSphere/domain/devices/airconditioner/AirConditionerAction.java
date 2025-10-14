package OopHW.Exp1.domain.devices.airconditioner;

import OopHW.Exp1.domain.automationScene.DeviceAction;
import OopHW.Exp1.domain.devices.Device;
import OopHW.Exp1.domain.devices.OnlineStatusType;
import OopHW.Exp1.domain.devices.PowerStatusType;

import java.util.Map;

public class AirConditionerAction implements DeviceAction {
    @Override
    public void execute(Device device, Map<String, Object> parameters) {
        if(!validateParameters(device, parameters)){
            throw new IllegalArgumentException("参数错误");
        }
        AirConditioner a = (AirConditioner) device;
        if(((OnlineStatusType) parameters.get("onlineStatus")).equals(OnlineStatusType.ONLINE)) {
            a.connect();
        } else if (((OnlineStatusType) parameters.get("onlineStatus")).equals(OnlineStatusType.OUTLINE)) {
            a.disconnect();
        }

        if (((PowerStatusType) parameters.get("powerStatus")).equals(PowerStatusType.POWERED)) {
            a.open();
        } else if (((PowerStatusType) parameters.get("powerStatus")).equals(PowerStatusType.UNPOWERED)) {
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
