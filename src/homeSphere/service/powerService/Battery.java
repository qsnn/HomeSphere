package homeSphere.service.powerService;

public interface Battery {
    int batteryLevel = 0;
    default int getBatteryLevel(){
        return batteryLevel;
    }
}
