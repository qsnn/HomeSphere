package com.qsnn.homeSphere.domain.deviceModule.devices;

import com.alibaba.fastjson2.JSON;
import com.qsnn.homeSphere.domain.deviceModule.Device;
import com.qsnn.homeSphere.domain.deviceModule.services.Manufacturer;
import com.qsnn.homeSphere.domain.deviceModule.services.RunningLog;

import java.util.Date;

import static com.qsnn.homeSphere.domain.deviceModule.services.RunningLog.Type.INFO;

/**
 * 智能门锁设备类
 *
 * <p>该类表示智能家居系统中的智能门锁设备，继承自Device基类。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>控制门锁的开关状态</li>
 *   <li>监控设备电池电量</li>
 *   <li>提供门锁状态管理功能</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>继承自Device基类，具备设备通用特性</li>
 *   <li>支持门锁状态控制</li>
 *   <li>内置电池电量监控</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class SmartLock extends Device {

    /** 门锁状态，true表示已锁定，false表示未锁定 */
    private boolean isLocked;

    /** 电池电量，单位：百分比 */
    private int batteryLevel = 0;

    /**
     * 智能门锁设备构造函数
     *
     * @param deviceID 设备唯一标识符
     * @param name 设备名称
     * @param manufacturer 设备制造商信息
     */
    public SmartLock(Integer deviceID, String name, Manufacturer manufacturer) {
        super(deviceID, name, manufacturer);
        setDeviceType(DeviceType.SMART_LOCK);
    }

    /**
     * 获取门锁状态
     *
     * @return true表示门锁已锁定，false表示门锁未锁定
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * 设置门锁状态
     *
     * @param locked 新的门锁状态，true表示锁定，false表示解锁
     */
    public void setLocked(boolean locked) {
        isLocked = locked;
        addRunningLog(new RunningLog(new Date(), "Target lockStatus set to " + isLocked, INFO, ""));
    }

    /**
     * 获取电池电量
     *
     * @return 电池电量百分比
     */
    public int getBatteryLevel() {
        return batteryLevel;
    }

    @Override
    public String toString() {
        return "SmartLock{" +
                "deviceId=" + getDeviceId() +
                ", name='" + getName() + '\'' +
                ", manufacturer=" + getManufacturer() +
                ", isLocked=" + isLocked +
                ", batteryLevel=" + batteryLevel +
                '}';
    }

    @Override
    public String formatToJsonString() {
        return JSON.toJSONString(this);
    }

    @Override
    public SmartLock ParseFromJsonString(String jsonString) {
        return JSON.parseObject(jsonString, SmartLock.class);
    }
}