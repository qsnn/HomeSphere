package homeSphere.domain.devices;

import homeSphere.log.Log;
import homeSphere.service.manufacturer.Manufacturer;

import java.time.LocalDateTime;
import java.util.*;

public abstract class Device {
    protected final Integer deviceID;
    protected String name;
    protected final String OS;
    protected final Manufacturer manufacturer;
    protected final String brand;
    protected final double power;
    protected OnlineStatusType onlineStatus;
    protected PowerStatusType powerStatus;
    protected LocalDateTime lastOpenTime;
    protected final Set<Usage> deviceUsages = new TreeSet<>(Comparator.comparing(Usage::getCloseTime));
    protected final Map<String, DeviceAttribute<?>> attributes = new HashMap<>();


    public Device(Integer deviceID, String name, String OS, Manufacturer manufacturer, String brand, double power) {
        this.deviceID = deviceID;
        this.name = name;
        this.OS = OS;
        this.manufacturer = manufacturer;
        this.brand = brand;
        onlineStatus = OnlineStatusType.OUTLINE;
        powerStatus = PowerStatusType.UNPOWERED;
        this.power = power;
        new Log(getDeviceID().toString(),"创建设备：" + name, Log.LogType.INFO, null);
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
        new Log(getDeviceID().toString(), "连接网络", Log.LogType.INFO, null);
        this.onlineStatus = OnlineStatusType.ONLINE;
    }
    public void disconnect(){
        new Log(getDeviceID().toString(),"断开网络", Log.LogType.INFO, null);
        this.onlineStatus = OnlineStatusType.OUTLINE;
    }

    public void open(){
        new Log(getDeviceID().toString(),"连接电源", Log.LogType.INFO, null);
        if(this.powerStatus == PowerStatusType.UNPOWERED){
            lastOpenTime = LocalDateTime.now();
        }
        this.powerStatus = PowerStatusType.POWERED;
    }
    public void close(){
        Usage u = new Usage(deviceID + "" + deviceUsages.size(),this, lastOpenTime, LocalDateTime.now());
        if(this.powerStatus == PowerStatusType.POWERED){
            deviceUsages.add(u);
        }
        this.powerStatus = PowerStatusType.UNPOWERED;
        new Log(getDeviceID().toString(),"断开电源", Log.LogType.INFO, u.toString());
    }


    public enum OnlineStatusType {
        ONLINE,
        OUTLINE
    }

    public enum PowerStatusType {
        POWERED,
        UNPOWERED
    }
}
