package com.qsnn.homeSphere.domain.deviceModule.services;

import com.qsnn.homeSphere.domain.deviceModule.Device;
import com.qsnn.homeSphere.domain.deviceModule.devices.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备制造商类
 *
 * <p>该类表示设备制造商信息，包含制造商名称和所支持的设备连接方式。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>存储制造商基本信息</li>
 *   <li>管理制造商支持的设备连接方式</li>
 *   <li>提供制造商信息的访问接口</li>
 *   <li>管理制造商生产的所有设备</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>制造商ID为final，确保唯一性和不变性</li>
 *   <li>支持设备连接协议的定义和管理</li>
 *   <li>维护制造商与设备的关联关系</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class Manufacturer implements DeviceFactory{

    /** 制造商唯一标识符 */
    private final int manufacturerId;

    /**
     * 制造商名称
     * <p>制造商的唯一标识名称，如："小米", "华为", "苹果"等</p>
     */
    private String name;

    /**
     * 设备连接协议
     * <p>该制造商设备支持的连接协议，如：WIFI、BLUETOOTH、ZIGBEE等</p>
     */
    private String protocols;

    /** 制造商生产的所有设备列表 */
    private List<Device> devices = new ArrayList<>();

    /**
     * 制造商构造函数（仅ID）
     *
     * @param manufacturerId 制造商唯一标识符
     */
    public Manufacturer(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    /**
     * 制造商构造函数（完整信息）
     *
     * @param manufacturerId 制造商唯一标识符
     * @param name 制造商名称
     * @param protocols 设备连接协议
     */
    public Manufacturer(int manufacturerId, String name, String protocols) {
        this.manufacturerId = manufacturerId;
        this.name = name;
        this.protocols = protocols;
    }

    // ==================== Getter and Setter方法 ====================

    /**
     * 获取制造商ID
     *
     * @return 制造商唯一标识符
     */
    public int getManufacturerId() {
        return manufacturerId;
    }

    /**
     * 获取制造商名称
     *
     * @return 制造商名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置制造商名称
     *
     * @param name 新的制造商名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取设备连接协议
     *
     * @return 连接协议字符串
     */
    public String getProtocols() {
        return protocols;
    }

    /**
     * 设置设备连接协议
     *
     * @param protocols 新的连接协议
     */
    public void setProtocols(String protocols) {
        this.protocols = protocols;
    }

    /**
     * 获取设备列表
     *
     * @return 制造商生产的所有设备列表
     */
    public List<Device> getDevices() {
        return devices;
    }

    /**
     * 设置设备列表
     *
     * @param devices 新的设备列表
     */
    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    // ==================== 业务逻辑 ====================

    /**
     * 添加设备到制造商
     *
     * @param device 要添加的设备
     */
    public void addDevice(Device device){
        devices.add(device);
    }

    /**
     * 从制造商移除设备
     *
     * @param device 要移除的设备
     */
    public void removeDevice(Device device){
        devices.remove(device);
    }

    /**
     * 返回对象的字符串表示形式
     * 格式：Manufacturer{manufacturerId=值, name='值', protocols='值'}
     *
     * @return 格式化的字符串
     */
    @Override
    public String toString() {
        return "Manufacturer{" +
                "manufacturerId=" + manufacturerId +
                ", name='" + name + '\'' +
                ", protocols='" + protocols + '\'' +
                '}';
    }

    @Override
    public Device createDevice(Integer deviceId, String name, DeviceType deviceType) {
        Device device;

        switch (deviceType) {
            case AIR_CONDITIONER:
                device = new AirConditioner(deviceId, name, this);
                break;
            case LIGHT_BULB:
                device = new LightBulb(deviceId, name, this);
                break;
            case SMART_LOCK:
                device = new SmartLock(deviceId, name, this);
                break;
            case BATHROOM_SCALE:
                device = new BathroomScale(deviceId, name, this);
                break;
            default:
                throw new IllegalArgumentException("不支持的设备类型: " + deviceType);
        }

        // 自动添加到制造商的设备列表中
        this.addDevice(device);
        return device;
    }

    @Override
    public Manufacturer getManufacturer() {
        return this;
    }
}