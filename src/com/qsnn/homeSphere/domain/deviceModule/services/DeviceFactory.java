package com.qsnn.homeSphere.domain.deviceModule.services;

import com.qsnn.homeSphere.domain.deviceModule.Device;
import com.qsnn.homeSphere.domain.deviceModule.devices.DeviceType;

/**
 * 设备工厂接口
 * 使用工厂方法模式，允许不同厂家生产多种类型的智能家居产品
 */
public interface DeviceFactory {

    /**
     * 创建设备
     * @param deviceId 设备ID
     * @param name 设备名称
     * @param deviceType 设备类型
     * @return 创建的设备实例
     */
    Device createDevice(Integer deviceId, String name, DeviceType deviceType);

    /**
     * 获取制造商信息
     * @return 制造商对象
     */
    Manufacturer getManufacturer();
}