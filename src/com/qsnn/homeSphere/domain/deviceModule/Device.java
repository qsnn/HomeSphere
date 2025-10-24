package com.qsnn.homeSphere.domain.deviceModule;

import com.qsnn.homeSphere.domain.deviceModule.services.Manufacturer;
import com.qsnn.homeSphere.domain.deviceModule.services.RunningLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 *   <li>支持节能减排管理</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>使用抽象类设计，具体设备类型需要继承并实现特定功能</li>
 *   <li>支持动态属性管理，可扩展设备特性</li>
 *   <li>自动记录设备操作日志和使用记录</li>
 *   <li>支持能耗计算和能源管理</li>
 *   <li>线程安全的集合操作</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public abstract class Device {
    /** 设备唯一序列号 */
    private final Integer deviceId;

    /** 设备名称 */
    private String name;

    /** 设备制造商信息 */
    private final Manufacturer manufacturer;

    /** 设备在线状态 */
    private boolean isOnline;

    /** 设备电源状态 */
    private boolean powerStatus;

    /** 设备运行日志列表 */
    private final List<RunningLog> runningLogs = new ArrayList<>();

    /** 最后开机时间，用于能耗计算 */
    private Date lastPowerOnTime;

    /**
     * 设备构造函数
     *
     * @param deviceId 设备唯一序列号
     * @param name 设备名称
     * @param manufacturer 设备制造商信息
     */
    public Device(Integer deviceId, String name, Manufacturer manufacturer) {
        this.deviceId = deviceId;
        this.name = name;
        this.manufacturer = manufacturer;
        this.isOnline = false;
        this.powerStatus = false;
    }

    // ==================== 基本Getter/Setter方法 ====================

    /**
     * 获取设备序列号
     *
     * @return 设备唯一序列号
     */
    public int getDeviceId() {
        return deviceId;
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
     * 获取设备制造商
     *
     * @return 设备制造商信息
     */
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    /**
     * 获取设备在线状态
     *
     * @return 设备在线状态
     */
    public boolean isOnline() {
        return isOnline;
    }

    /**
     * 设置设备在线状态
     *
     * @param online 在线状态
     */
    public void setOnline(boolean online) {
        boolean oldStatus = this.isOnline;
        this.isOnline = online;

        // 记录状态变化日志
        if (oldStatus != online) {
            String event = online ? "设备上线" : "设备下线";
            addRunningLog(new RunningLog(new Date(), event, RunningLog.Type.INFO,
                    "设备连接状态变化: " + (online ? "上线" : "下线")));
        }
    }

    /**
     * 获取设备电源状态
     *
     * @return 设备电源状态
     */
    public boolean isPowerStatus() {
        return powerStatus;
    }

    /**
     * 获取设备运行日志列表
     *
     * @return 运行日志列表
     */
    public List<RunningLog> getRunningLogs() {
        return new ArrayList<>(runningLogs);
    }

    // ==================== 设备操作方法 ====================

    /**
     * 打开设备电源
     */
    public void powerOn() {
        if (!this.powerStatus) {
            this.powerStatus = true;
            this.lastPowerOnTime = new Date();

            // 记录开机日志
            addRunningLog(new RunningLog(new Date(), "设备开机", RunningLog.Type.INFO,
                    "设备电源已开启"));
        }
    }

    /**
     * 关闭设备电源
     */
    public void powerOff() {
        if (this.powerStatus) {
            this.powerStatus = false;
            // 记录关机日志并计算本次运行的能耗
            Date powerOffTime = new Date();
            addRunningLog(new RunningLog(powerOffTime, "设备关机", RunningLog.Type.INFO, "设备电源已关闭"));

        }
    }

    /**
     * 连接设备
     */
    public void connect() {
        setOnline(true);
    }

    /**
     * 断开设备连接
     */
    public void disconnect() {
        setOnline(false);
    }

    /**
     * 添加运行日志
     *
     * @param log 运行日志
     */
    public void addRunningLog(RunningLog log) {
        if (log != null) {
            runningLogs.add(log);
        }
    }

    // ==================== Object类方法重写 ====================

    /**
     * 比较两个设备是否相等（基于设备ID）
     *
     * @param obj 要比较的对象
     * @return 如果设备ID相同则返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Device device = (Device) obj;
        return deviceId.equals(device.deviceId);
    }

    /**
     * 返回对象的字符串表示形式
     * 格式：Device{deviceId=值, name='值', isOnline=值, powerStatus=值}
     *
     * @return 格式化的字符串
     */
    @Override
    public String toString() {
        return "Device{" +
                "deviceId=" + deviceId +
                ", name='" + name + '\'' +
                ", isOnline=" + isOnline +
                ", powerStatus=" + powerStatus +
                '}';
    }
}