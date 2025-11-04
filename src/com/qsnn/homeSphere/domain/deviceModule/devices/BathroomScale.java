package com.qsnn.homeSphere.domain.deviceModule.devices;

import com.alibaba.fastjson2.JSON;
import com.qsnn.homeSphere.domain.deviceModule.Device;
import com.qsnn.homeSphere.domain.deviceModule.services.Manufacturer;

/**
 * 体重秤设备类
 *
 * <p>该类表示智能家居系统中的体重秤设备，继承自Device基类。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>测量和记录用户体重数据</li>
 *   <li>监控设备电池电量</li>
 *   <li>提供体重数据管理功能</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>继承自Device基类，具备设备通用特性</li>
 *   <li>支持体重数据测量和存储</li>
 *   <li>内置电池电量监控</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class BathroomScale extends Device {

    /** 体重数据，单位：千克 */
    private double bodyMass;

    /** 电池电量，单位：百分比 */
    private int batteryLevel = 0;

    /**
     * 体重秤设备构造函数
     *
     * @param deviceID 设备唯一标识符
     * @param name 设备名称
     * @param manufacturer 设备制造商信息
     */
    public BathroomScale(Integer deviceID, String name, Manufacturer manufacturer) {
        super(deviceID, name, manufacturer);
        setDeviceType(DeviceType.BATHROOM_SCALE);
    }

    /**
     * 获取体重数据
     *
     * @return 体重值，单位：千克
     */
    public double getBodyMass() {
        return bodyMass;
    }


    /**
     * 获取电池电量
     *
     * @return 电池电量百分比
     */
    public int getBatteryLevel() {
        return batteryLevel;
    }

    /**
     * 返回对象的字符串表示形式
     * 格式：BathroomScale{deviceId=值, name='值', manufacturer=值, bodyMass=值, batteryLevel=值}
     *
     * @return 格式化的字符串
     */
    @Override
    public String toString() {
        return "BathroomScale{" +
                "deviceId=" + getDeviceId() +
                ", name='" + getName() + '\'' +
                ", manufacturer=" + getManufacturer() +
                ", bodyMass=" + bodyMass +
                ", batteryLevel=" + batteryLevel +
                '}';
    }

    /**
     * 将体重秤设备信息格式化为JSON字符串
     *
     * @return JSON格式的设备信息字符串
     */
    @Override
    public String formatToJsonString() {
        return JSON.toJSONString(this);
    }

    /**
     * 从JSON字符串解析体重秤设备信息
     *
     * @param jsonString JSON格式的设备信息字符串
     * @return 解析后的体重秤设备对象
     */
    @Override
    public BathroomScale parseFromJsonString(String jsonString) {
        return JSON.parseObject(jsonString, BathroomScale.class);
    }
}