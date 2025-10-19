package com.qsnn.homeSphere.domain.deviceModule;

import com.qsnn.homeSphere.domain.deviceModule.attributes.DeviceAttribute;
import com.qsnn.homeSphere.log.Log;
import com.qsnn.homeSphere.utils.Util;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 设备抽象基类
 *
 * <p>该类是所有智能设备的基类，定义了设备的基本属性和行为。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>管理设备的基本信息和状态</li>
 *   <li>记录设备使用历史和操作日志</li>
 *   <li>提供设备属性的动态管理</li>
 *   <li>支持设备开关、连接等基本操作</li>
 *   <li>计算设备能耗</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>使用抽象类设计，具体设备类型需要继承并实现特定功能</li>
 *   <li>支持动态属性管理，可扩展设备特性</li>
 *   <li>自动记录设备操作日志和使用记录</li>
 *   <li>线程安全的集合操作</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public abstract class Device {
    /** 设备唯一序列号 */
    protected final Integer deviceID;

    /** 设备名称 */
    protected String name;

    /** 设备操作系统 */
    protected final String OS;

    /** 设备制造商信息 */
    protected final Manufacturer manufacturer;

    /** 设备品牌 */
    protected final String brand;

    /** 设备功率，单位：瓦特(W) */
    protected final double power;

    /** 设备供电模式 */
    protected final PowerMode powerMode;

    /** 设备连接模式 */
    protected final ConnectMode connectMode;

    /** 设备在线状态 */
    protected OnlineStatusType onlineStatus;

    /** 设备电源状态 */
    protected PowerStatusType powerStatus;

    /** 设备电池电量（仅对电池设备有效） */
    protected int batteryLevel;

    /** 设备上次开启时间 */
    protected LocalDateTime lastOpenTime;

    /** 设备使用记录集合，按关闭时间排序 */
    protected final Set<Usage> deviceUsages = new TreeSet<>(Comparator.comparing(Usage::getCloseTime));

    /** 设备日志记录集合，按时间排序 */
    protected final Set<Log> deviceLogs = new TreeSet<>(Comparator.comparing(Log::getT));

    /** 设备属性映射表，存储动态属性 */
    protected final Map<String, DeviceAttribute<?>> attributes = new HashMap<>();

    /**
     * 设备构造函数
     *
     * <p>初始化设备基本信息并设置默认状态，自动记录设备创建日志。</p>
     *
     * @param deviceID 设备唯一序列号
     * @param name 设备名称
     * @param OS 设备操作系统
     * @param manufacturer 设备制造商
     * @param brand 设备品牌
     * @param connectMode 设备连接模式
     * @param powerMode 设备供电模式
     * @param power 设备功率，单位：瓦特(W)
     */
    public Device(Integer deviceID, String name, String OS, Manufacturer manufacturer, String brand,
                  ConnectMode connectMode, PowerMode powerMode, double power) {
        this.deviceID = deviceID;
        this.name = name;
        this.OS = OS;
        this.manufacturer = manufacturer;
        this.brand = brand;
        this.connectMode = connectMode;
        this.powerMode = powerMode;
        this.onlineStatus = OnlineStatusType.OUTLINE;
        this.powerStatus = PowerStatusType.UNPOWERED;
        this.power = power;
        deviceLogs.add(new Log(getDeviceID().toString(),"创建设备：" + name, Log.LogType.INFO, this.toString()));
    }

    // ==================== 基本Getter/Setter方法 ====================

    /**
     * 获取设备序列号
     *
     * @return 设备唯一序列号
     */
    public Integer getDeviceID() {
        return deviceID;
    }

    /**
     * 获取设备名称
     *
     * @return 设备名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置设备名称
     *
     * @param name 新的设备名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取设备操作系统
     *
     * @return 设备操作系统
     */
    public String getOS() {
        return OS;
    }

    /**
     * 获取设备制造商
     *
     * @return 设备制造商信息
     */
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    /**
     * 获取设备品牌
     *
     * @return 设备品牌
     */
    public String getBrand() {
        return brand;
    }

    /**
     * 获取设备连接模式
     *
     * @return 设备连接模式
     */
    public ConnectMode getConnectMode() {
        return connectMode;
    }

    /**
     * 获取设备供电模式
     *
     * @return 设备供电模式
     */
    public PowerMode getPowerMode() {
        return powerMode;
    }

    /**
     * 获取设备在线状态
     *
     * @return 设备在线状态
     */
    public OnlineStatusType getOnlineStatus() {
        return onlineStatus;
    }

    /**
     * 获取设备电源状态
     *
     * @return 设备电源状态
     */
    public PowerStatusType getPowerStatus() {
        return powerStatus;
    }

    /**
     * 获取设备功率
     *
     * @return 设备功率，单位：瓦特(W)
     */
    public double getPower(){
        return power;
    }

    /**
     * 获取设备使用记录
     *
     * @return 设备使用记录集合，按关闭时间排序
     */
    public Set<Usage> getDeviceUsages() {
        return deviceUsages;
    }

    /**
     * 获取设备日志记录
     *
     * @return 设备日志记录集合，按时间排序
     */
    public Set<Log> getDeviceLogs() {
        return deviceLogs;
    }

    /**
     * 获取设备电池电量
     *
     * <p>仅对电池供电设备有效，非电池设备调用此方法将抛出异常</p>
     *
     * @return 电池电量百分比
     * @throws IllegalArgumentException 如果设备不是电池供电模式
     */
    public int getBatteryLevel() {
        if(powerMode != PowerMode.BATTERY){
            throw new IllegalArgumentException("非电池设备不能获取电池电量！");
        }
        return batteryLevel;
    }

    // ==================== 属性管理方法 ====================

    /**
     * 添加设备属性
     *
     * <p>用于向设备添加动态属性，由子类在初始化时调用</p>
     *
     * @param key 属性键名
     * @param attribute 属性对象
     */
    protected void addAttribute(String key, DeviceAttribute<?> attribute) {
        attributes.put(key, attribute);
    }

    /**
     * 检查设备是否包含指定属性
     *
     * @param attribute 属性名称
     * @return 如果包含该属性返回true，否则返回false
     */
    public boolean hasAttribute(String attribute){
        return attributes.containsKey(attribute);
    }

    /**
     * 获取设备属性值
     *
     * <p>泛型方法，自动进行类型转换</p>
     *
     * @param <T> 属性值类型
     * @param attributeName 属性名称
     * @return 属性值，如果属性不存在返回null
     */
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String attributeName) {
        DeviceAttribute<T> attribute = (DeviceAttribute<T>) attributes.get(attributeName);
        return attribute != null ? attribute.getValue() : null;
    }

    /**
     * 设置设备属性值
     *
     * <p>设置属性值并自动记录变更日志</p>
     *
     * @param <T> 属性值类型
     * @param attributeName 属性名称
     * @param value 属性值
     * @return 如果设置成功返回true，否则返回false
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

    /**
     * 初始化设备属性抽象方法
     *
     * <p>由具体设备子类实现，用于初始化设备特定的属性</p>
     */
    protected abstract void initializeAttributes();

    // ==================== 设备操作方法 ====================

    /**
     * 执行设备操作
     *
     * <p>统一的设备操作方法，处理属性设置和核心状态变更</p>
     *
     * @param device 目标设备
     * @param parameters 操作参数映射表
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

    /**
     * 连接设备到网络
     *
     * <p>将设备在线状态设置为ONLINE，并记录连接日志</p>
     */
    public void connect(){
        deviceLogs.add(new Log(getDeviceID().toString(), "连接网络", Log.LogType.INFO, null));
        this.onlineStatus = OnlineStatusType.ONLINE;
    }

    /**
     * 断开设备网络连接
     *
     * <p>将设备在线状态设置为OUTLINE，并记录断开日志</p>
     */
    public void disconnect(){
        deviceLogs.add(new Log(getDeviceID().toString(),"断开网络", Log.LogType.INFO, null));
        this.onlineStatus = OnlineStatusType.OUTLINE;
    }

    /**
     * 开启设备电源
     *
     * <p>将设备电源状态设置为POWERED，记录开启时间，并记录电源连接日志</p>
     */
    public void open(){
        deviceLogs.add(new Log(getDeviceID().toString(),"连接电源", Log.LogType.INFO, null));
        if(this.powerStatus == PowerStatusType.UNPOWERED){
            lastOpenTime = LocalDateTime.now();
        }
        this.powerStatus = PowerStatusType.POWERED;
    }

    /**
     * 关闭设备电源
     *
     * <p>将设备电源状态设置为UNPOWERED，创建使用记录，并记录电源断开日志</p>
     */
    public void close(){
        Usage u = new Usage(deviceID + "" + deviceUsages.size(),getPower(), lastOpenTime, LocalDateTime.now());
        if(this.powerStatus == PowerStatusType.POWERED){
            deviceUsages.add(u);
        }
        this.powerStatus = PowerStatusType.UNPOWERED;
        deviceLogs.add(new Log(getDeviceID().toString(),"断开电源", Log.LogType.INFO, u.toString()));
    }

    // ==================== 能耗计算方法 ====================

    /**
     * 计算指定时间段的设备用电量
     *
     * <p>电池设备返回0，非电池设备根据使用记录计算能耗</p>
     *
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @return 用电量，单位：千瓦时(kWh)
     */
    public Double calculatePowerConsumption(LocalDateTime startTime, LocalDateTime endTime){
        if(powerMode == PowerMode.BATTERY){
            return 0.0;
        }
        return Util.calculatePowerConsumption(deviceUsages, startTime, endTime);
    }

    /**
     * 计算设备总用电量
     *
     * <p>电池设备返回0，非电池设备计算所有使用记录的总能耗</p>
     *
     * @return 总用电量，单位：千瓦时(kWh)
     */
    public Double calculateAllPowerConsumption(){
        if(powerMode == PowerMode.BATTERY){
            return 0.0;
        }
        return Util.calculatePowerConsumption(deviceUsages);
    }

    // ==================== 重写方法 ====================

    /**
     * 返回设备的格式化字符串表示
     *
     * <p>格式：[设备ID - 设备名称 - 制造商 - 品牌 - 操作系统 - 功率 - 连接模式 - 供电模式]</p>
     *
     * @return 格式化的设备信息字符串
     */
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

    // ==================== 枚举类型定义 ====================

    /**
     * 设备在线状态枚举
     */
    public enum OnlineStatusType {
        /** 在线 */
        ONLINE,
        /** 离线 */
        OUTLINE
    }

    /**
     * 设备电源状态枚举
     */
    public enum PowerStatusType {
        /** 已供电 */
        POWERED,
        /** 未供电 */
        UNPOWERED
    }

    /**
     * 设备供电模式枚举
     */
    public enum PowerMode {
        /** 电池供电 */
        BATTERY,
        /** 主电源供电 */
        MAINSPOWER
    }

    /**
     * 设备连接模式枚举
     */
    public enum ConnectMode {
        /** WiFi连接 */
        WIFI,
        /** 蓝牙连接 */
        BLUETOOTH,
        /** ZigBee连接 */
        ZIGBEE,
        /** Z-Wave连接 */
        Z_WAVE,
        /** Thread连接 */
        THREAD,
        /** Matter协议连接 */
        MATTER
    }
}