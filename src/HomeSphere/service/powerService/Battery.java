package OopHW.Exp1.service.powerService;

public interface Battery {
    int batteryLevel = 0;
    public default int getBatteryLevel(){
        return batteryLevel;
    }
}
