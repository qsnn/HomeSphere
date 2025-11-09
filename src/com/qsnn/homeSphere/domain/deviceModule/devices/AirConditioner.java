package com.qsnn.homeSphere.domain.deviceModule.devices;

import com.alibaba.fastjson2.JSON;
import com.qsnn.homeSphere.domain.deviceModule.Device;
import com.qsnn.homeSphere.domain.deviceModule.services.EnergyReporting;
import com.qsnn.homeSphere.domain.deviceModule.services.RunningLog;

import java.util.Date;

import static com.qsnn.homeSphere.domain.deviceModule.services.RunningLog.Type.INFO;

/**
 * 空调设备类
 *
 * <p>该类表示智能家居系统中的空调设备，继承自Device基类并实现能源报告功能。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>管理空调的基本运行参数（当前温度、目标温度）</li>
 *   <li>实现能源消耗报告功能</li>
 *   <li>提供温度调节和控制功能</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>继承自Device基类，具备设备通用特性</li>
 *   <li>实现EnergyReporting接口，支持能耗监控</li>
 *   <li>内置功率管理和温度控制逻辑</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class AirConditioner extends Device implements EnergyReporting {


    /** 空调功率，单位：瓦 */
    private double power = 1000;

    /** 当前温度，单位：摄氏度 */
    private double currTemp;

    /** 目标温度，单位：摄氏度 */
    private double targetTemp;

    /**
     * 空调设备构造函数
     *
     * @param deviceID 设备唯一标识符
     * @param name 设备名称
     * @param manufacturer 设备制造商信息
     */
    public AirConditioner(Integer deviceID, String name, int manufacturerId) {
        super(deviceID, name, manufacturerId);
        setDeviceType(DeviceType.AIR_CONDITIONER);
    }


    /**
     * 获取当前温度
     *
     * @return 当前温度值，单位：摄氏度
     */
    public double getCurrTemp() {
        return currTemp;
    }
    /**
     * 设置当前温度
     *
     * @param currTemp 新的当前温度值，单位：摄氏度
     */
    public void setCurrTemp(double currTemp) {
        this.currTemp = currTemp;
    }

    /**
     * 获取目标温度
     *
     * @return 目标温度值，单位：摄氏度
     */
    public double getTargetTemp() {
        return targetTemp;
    }

    /**
     * 设置目标温度
     *
     * @param targetTemp 新的目标温度值，单位：摄氏度
     */
    public void setTargetTemp(double targetTemp) {
        this.targetTemp = targetTemp;
        addRunningLog(new RunningLog(new Date(), "Target temperature set to " + targetTemp + "°C", INFO, ""));
    }

    /**
     * 获取空调功率
     *
     * @return 空调功率值，单位：瓦
     */
    @Override
    public double getPower() {
        return power;
    }

/**
 * 返回对象的字符串表示形式
 * 格式：AirConditioner{deviceId=值, name='值', manufacturer=值, currTemp=值, targetTemp=值}
 *
 * @return 格式化的字符串
 */
    @Override
    public String toString() {
        return "AirConditioner{" +
                "deviceId=" + getDeviceId() +
                ", name='" + getName() + '\'' +
                ", manufacturer=" + getManufacturerId() +
                ", currTemp=" + currTemp +
                ", targetTemp=" + targetTemp +
                '}';
    }

    /**
     * 将空调设备信息格式化为JSON字符串
     *
     * @return JSON格式的设备信息字符串
     */
    @Override
    public String formatToJsonString() {
        return JSON.toJSONString(this);
    }

    /**
     * 从JSON字符串解析空调设备信息
     *
     * @param jsonString JSON格式的设备信息字符串
     * @return 解析后的空调设备对象
     */
    @Override
    public AirConditioner parseFromJsonString(String jsonString) {
        return JSON.parseObject(jsonString, AirConditioner.class);
    }
}