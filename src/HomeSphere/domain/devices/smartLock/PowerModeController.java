package OopHW.Exp1.domain.devices.smartLock;

import OopHW.Exp1.service.powerService.PowerMode;

public interface PowerModeController {
    public PowerMode getPowerMode();

    public void setPowerMode(PowerMode powerMode);
}
