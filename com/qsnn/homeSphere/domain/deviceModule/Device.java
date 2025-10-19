package qsnn.homeSphere.domain.deviceModule;

import qsnn.homeSphere.domain.deviceModule.services.Manufacturer;
import qsnn.homeSphere.domain.deviceModule.services.RunningLog;

import java.util.ArrayList;
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
    protected final Integer deviceId;

    /** 设备名称 */
    protected String name;

    /** 设备制造商信息 */
    protected final Manufacturer manufacturer;

    /** 设备在线状态 */
    protected boolean isOnline;

    /** 设备电源状态 */
    protected boolean powerStatus;
    protected final List<RunningLog> runningLogs = new ArrayList<>();


    public Device(Integer deviceId, String name, Manufacturer manufacturer) {
        this.deviceId = deviceId;
        this.name = name;
        this.manufacturer = manufacturer;
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
     * 获取设备电源状态
     *
     * @return 设备电源状态
     */
    public boolean isPowerStatus() {
        return powerStatus;
    }

    public void powerOn(){
        this.powerStatus = true;
    }

    public void powerOff(){
        this.powerStatus = false;
    }

}