package homeSphere.domain.devices.smartLock;

import homeSphere.domain.devices.Device;
import homeSphere.domain.house.Room;
import homeSphere.service.connectService.Zigbee;
import homeSphere.service.manufacturer.Manufacturer;
import homeSphere.service.powerService.Battery;
import homeSphere.service.powerService.MainsPower;
import homeSphere.service.powerService.PowerMode;

import java.util.Map;

public class SmartLock extends Device
        implements MainsPower,
        LockStatusController, PowerModeController,
        Battery, Zigbee {
    private LockStatusType lockStatus;
    private PowerMode powerMode = PowerMode.MAINSPOWER;

    public SmartLock(String ID, String name, String OS, Manufacturer manufacturer, String brand, double powerConsumption, Room room) {
        super(ID, name, OS, manufacturer, brand, powerConsumption, room);
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
