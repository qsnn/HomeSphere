package OopHW.Exp1.domain.automationScene;

import OopHW.Exp1.domain.devices.Device;

import java.util.Map;

public interface DeviceAction {
    void execute(Device device, Map<String, Object> parameters);
    boolean validateParameters(Device device, Map<String, Object> parameters);
}
