package com.qsnn.homeSphere.domain.deviceModule;

import com.qsnn.homeSphere.log.Log;
import com.qsnn.homeSphere.utils.Util;

import java.time.LocalDateTime;
import java.util.*;

public abstract class Device {
    protected final Integer deviceID;   //设备序列号
    protected String name;  //设备名
    protected final String OS;  //设备系统
    protected final Manufacturer manufacturer;  //制造商
    protected final String brand;   //品牌
    protected final double power;   //功率
    protected final PowerMode powerMode;  //供电模式
    protected final ConnectMode connectMode;    //联网模式
    protected OnlineStatusType onlineStatus;    //在线状态
    protected PowerStatusType powerStatus;  //供电状态
    protected int batteryLevel;  //供电状态
    protected LocalDateTime lastOpenTime;   //上次打开的时间
    protected final Set<Usage> deviceUsages = new TreeSet<>(Comparator.comparing(Usage::getCloseTime)); //使用记录
    protected final Set<Log> deviceLogs = new TreeSet<>(Comparator.comparing(Log::getT)); //使用记录
    protected final Map<String, DeviceAttribute<?>> attributes = new HashMap<>();   //属性

    public Device(Integer deviceID, String name, String OS, Manufacturer manufacturer, String brand, ConnectMode connectMode, PowerMode powerMode, double power) {
        this.deviceID = deviceID;
        this.name = name;
        this.OS = OS;
        this.manufacturer = manufacturer;
        this.brand = brand;
        this.connectMode = connectMode;
        this.powerMode = powerMode;
        onlineStatus = OnlineStatusType.OUTLINE;
        powerStatus = PowerStatusType.UNPOWERED;
        this.power = power;
        deviceLogs.add(new Log(getDeviceID().toString(),"创建设备：" + name, Log.LogType.INFO, this.toString()));
    }

    public Integer getDeviceID() {
        return deviceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOS() {
        return OS;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public String getBrand() {
        return brand;
    }

    public ConnectMode getConnectMode() {
        return connectMode;
    }

    public PowerMode getPowerMode() {
        return powerMode;
    }

    public OnlineStatusType getOnlineStatus() {
        return onlineStatus;
    }

    public PowerStatusType getPowerStatus() {
        return powerStatus;
    }

    public double getPower(){
        return power;
    }

    public Set<Usage> getDeviceUsages() {
        return deviceUsages;
    }

    public Set<Log> getDeviceLogs() {
        return deviceLogs;
    }

    public int getBatteryLevel() {
        if(powerMode != PowerMode.BATTERY){
            throw new IllegalArgumentException("非电池设备不能获取电池电量！");
        }
        return batteryLevel;
    }


    /**
     * 添加设备属性
     */
    protected void addAttribute(String key, DeviceAttribute<?> attribute) {
        attributes.put(key, attribute);
    }

    public boolean hasAttribute(String attribute){
        return attributes.containsKey(attribute);
    }
    /**
     * 获取设备属性值
     */
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String attributeName) {
        DeviceAttribute<T> attribute = (DeviceAttribute<T>) attributes.get(attributeName);
        return attribute != null ? attribute.getValue() : null;
    }

    /**
     * 设置设备属性值
     */
    @SuppressWarnings("unchecked")
    public <T> boolean setAttribute(String attributeName, T value) {
        DeviceAttribute<T> attribute = (DeviceAttribute<T>) attributes.get(attributeName);
        if (attribute != null && attribute.setValue(value)) {
            // 记录属性变更日志
            new Log(getDeviceID().toString(),
                    String.format("属性变更: %s = %s", attributeName, value),
                    Log.LogType.INFO, null);
            return true;
        }
        return false;
    }

    //初始化属性
    protected abstract void initializeAttributes();
    /**
     * 统一的execute方法 - 现在可以处理属性设置
     */
    public void execute(Device device, Map<String, Object> parameters) {
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            String attributeName = entry.getKey();
            Object value = entry.getValue();

            // 特殊处理核心状态
            switch (attributeName) {
                case "online_status":
                    if ("ONLINE".equals(value)) connect();
                    else if ("OUTLINE".equals(value)) disconnect();
                    break;
                case "power_status":
                    if ("POWERED".equals(value)) open();
                    else if ("UNPOWERED".equals(value)) close();
                    break;
                default:
                    // 普通属性设置
                    setAttribute(attributeName, value);
                    break;
            }
        }
    }

    public void connect(){
        deviceLogs.add(new Log(getDeviceID().toString(), "连接网络", Log.LogType.INFO, null));
        this.onlineStatus = OnlineStatusType.ONLINE;
    }
    public void disconnect(){
        deviceLogs.add(new Log(getDeviceID().toString(),"断开网络", Log.LogType.INFO, null));
        this.onlineStatus = OnlineStatusType.OUTLINE;
    }

    public void open(){
        deviceLogs.add(new Log(getDeviceID().toString(),"连接电源", Log.LogType.INFO, null));
        if(this.powerStatus == PowerStatusType.UNPOWERED){
            lastOpenTime = LocalDateTime.now();
        }
        this.powerStatus = PowerStatusType.POWERED;
    }
    public void close(){
        Usage u = new Usage(deviceID + "" + deviceUsages.size(),getPower(), lastOpenTime, LocalDateTime.now());
        if(this.powerStatus == PowerStatusType.POWERED){
            deviceUsages.add(u);
        }
        this.powerStatus = PowerStatusType.UNPOWERED;
        deviceLogs.add(new Log(getDeviceID().toString(),"断开电源", Log.LogType.INFO, u.toString()));
    }

    public Double calculatePowerConsumption(LocalDateTime startTime, LocalDateTime endTime){
        if(powerMode == PowerMode.BATTERY){
            return 0.0;
        }
        return Util.calculatePowerConsumption(deviceUsages, startTime, endTime);
    }

    public Double calculateAllPowerConsumption(){
        if(powerMode == PowerMode.BATTERY){
            return 0.0;
        }
        return Util.calculatePowerConsumption(deviceUsages);
    }

    @Override
    public String toString() {
        return new StringJoiner(" - ", "[", "]")
                .add(Integer.toString(getDeviceID()))
                .add(getName())
                .add(getManufacturer().getName())  // 制造商名称
                .add(getBrand())                   // 品牌
                .add(getOS())                      // 操作系统
                .add(Double.toString(getPower()) + "W")  // 功率
                .add(getConnectMode().name())      // 连接模式
                .add(getPowerMode().name())        // 电源模式

                .toString();
    }

    public enum OnlineStatusType {
        ONLINE,
        OUTLINE
    }

    public enum PowerStatusType {
        POWERED,
        UNPOWERED
    }

    public enum PowerMode {
        BATTERY,
        MAINSPOWER
    }

    public enum ConnectMode {
        WIFI, BLUETOOTH, ZIGBEE, Z_WAVE, THREAD, MATTER
    }
}
