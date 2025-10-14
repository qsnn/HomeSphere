package homeSphere.domain.devices.smartLock;

import homeSphere.service.powerService.PowerMode;

public interface PowerModeController {
    PowerMode getPowerMode();

    void setPowerMode(PowerMode powerMode);
}
