package OopHW.Exp1.domain.devices.smartLock;

import OopHW.Exp1.domain.devices.Device;
import OopHW.Exp1.service.connectService.Zigbee;
import OopHW.Exp1.service.manufacturer.Manufacturer;
import OopHW.Exp1.service.powerService.Battery;
import OopHW.Exp1.service.powerService.MainsPower;
import OopHW.Exp1.service.powerService.PowerMode;

import java.util.Map;

public class SmartLock extends Device
        implements MainsPower,
        LockStatusController, PowerModeController,
        Battery, Zigbee {
    private LockStatusType lockStatus;
    private PowerMode powerMode = PowerMode.MAINSPOWER;

    public SmartLock(String ID, String name, String OS, Manufacturer manufacturer, String brand, double powerConsumption) {
        super(ID, name, OS, manufacturer, brand, powerConsumption);
        lockStatus = LockStatusType.LOCK;
    }

    @Override
    public void execute(Device device, Map<String, Object> parameters) {
        new SmartLockAction().execute(device, parameters);
    }

    @Override
    public LockStatusType getLockStatus() {
        return lockStatus;
    }

    @Override
    public void setLockStatus(LockStatusType lockStatus) {
        this.lockStatus = lockStatus;
    }

    @Override
    public PowerMode getPowerMode() {
        return powerMode;
    }

    @Override
    public void setPowerMode(PowerMode powerMode) {
        this.powerMode = powerMode;
    }

}
