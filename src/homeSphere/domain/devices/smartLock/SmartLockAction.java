package homeSphere.domain.devices.smartLock;

import homeSphere.domain.automationScene.DeviceAction;
import homeSphere.domain.devices.Device;
import homeSphere.service.powerService.PowerMode;

import java.util.Map;

public class SmartLockAction implements DeviceAction {
    @Override
    public void execute(Device device, Map<String, Object> parameters) {
        if(!validateParameters(device, parameters)){
            throw new IllegalArgumentException("参数错误");
        }
        SmartLock d = (SmartLock) device;
        if((parameters.get("onlineStatus")).equals(Device.OnlineStatusType.ONLINE)) {
            d.connect();
        } else if ((parameters.get("onlineStatus")).equals(Device.OnlineStatusType.OUTLINE)) {
            d.disconnect();
        }

        if ((parameters.get("powerStatus")).equals(Device.PowerStatusType.POWERED)) {
            d.open();
        } else if ((parameters.get("powerStatus")).equals(Device.PowerStatusType.UNPOWERED)) {
            d.close();
        }
        d.setLockStatus((LockStatusController.LockStatusType) parameters.get("lockStatus"));
        d.setPowerMode((PowerMode) parameters.get("powerMode"));

    }

    @Override
    public boolean validateParameters(Device device, Map<String, Object> parameters) {
        return device instanceof SmartLock &&
                parameters != null &&
                parameters.get("onlineStatus") instanceof Device.OnlineStatusType &&
                parameters.get("powerStatus") instanceof Device.PowerStatusType &&
                parameters.get("lockStatus") instanceof LockStatusController.LockStatusType &&
                parameters.get("powerMode") instanceof PowerMode;
    }
}
