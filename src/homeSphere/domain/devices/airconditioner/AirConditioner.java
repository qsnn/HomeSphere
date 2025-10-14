package homeSphere.domain.devices.airconditioner;

import homeSphere.domain.devices.Device;
import homeSphere.domain.houseSystem.Room;
import homeSphere.runningLog.DeviceLog;
import homeSphere.runningLog.LogType;
import homeSphere.service.connectService.Bluetooth;
import homeSphere.service.connectService.WiFi;
import homeSphere.service.manufacturer.Manufacturer;
import homeSphere.service.powerService.MainsPower;

import java.util.Map;

public class AirConditioner extends Device implements MainsPower,
        TemperatureController, AirConditionerModelController,
        WiFi, Bluetooth {
    private AirConditionerModelType model;  //空调模式
    private int temperature = 26;    //温度

    public AirConditioner(String ID, String name, String OS, Manufacturer manufacturer, String brand, double powerConsumption, Room room) {
        super(ID, name, OS, manufacturer, brand, powerConsumption, room);
        model = AirConditionerModelType.COOL;
    }

    @Override
    public void execute(Device device, Map<String, Object> parameters) {
        new AirConditionerAction().execute(device, parameters);
    }

    @Override
    public AirConditionerModelType getModel() {
        return model;
    }

    @Override
    public void setModel(AirConditionerModelType model) {
        new DeviceLog(this, power,"设置空调模式：" + model, LogType.INFO, this.model + "->" + model);
        this.model = model;
    }

    @Override
    public int getTemperature() {
        return temperature;
    }

    @Override
    public void setTemperature(int temperature) {
        if(temperature < 16 || temperature > 30){
            new DeviceLog(this, power,"设置空调温度：" + temperature, LogType.WARNING, "设置温度超出范围16-30，当前设置温度：" + temperature);
            return;
        }
        new DeviceLog(this, power,"设置空调温度：" + temperature, LogType.INFO, this.temperature + "->" + temperature);
        this.temperature = temperature;
    }

    @Override
    public int getCurrentTemperature() {
        return 0;
    }
}
