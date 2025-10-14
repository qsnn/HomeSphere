package OopHW.Exp1.domain.devices.smartLock;

import OopHW.Exp1.domain.automationScene.DeviceAction;
import OopHW.Exp1.domain.devices.Device;
import OopHW.Exp1.domain.devices.OnlineStatusType;
import OopHW.Exp1.domain.devices.PowerStatusType;
import OopHW.Exp1.service.powerService.PowerMode;

import java.util.Map;

public class SmartLockAction implements DeviceAction {
    @Override
    public void execute(Device device, Map<String, Object> parameters) {
        if(!validateParameters(device, parameters)){
            throw new IllegalArgumentException("参数错误");
        }
        SmartLock d = (SmartLock) device;
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
        d.setLockStatus((LockStatusController.LockStatusType) parameters.get("lockStatus"));
        d.setPowerMode((PowerMode) parameters.get("powerMode"));

    }

    @Override
    public boolean validateParameters(Device device, Map<String, Object> parameters) {
        return device instanceof SmartLock &&
                parameters != null &&
                parameters.get("onlineStatus") instanceof OnlineStatusType &&
                parameters.get("powerStatus") instanceof PowerStatusType &&
                parameters.get("lockStatus") instanceof LockStatusController.LockStatusType &&
                parameters.get("powerMode") instanceof PowerMode;
    }
}
