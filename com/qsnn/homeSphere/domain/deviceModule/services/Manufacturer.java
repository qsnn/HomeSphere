package qsnn.homeSphere.domain.deviceModule.services;

import qsnn.homeSphere.domain.deviceModule.Device;

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
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>所有字段均为final，确保对象不可变性</li>
 *   <li>支持多种连接方式的定义和管理</li>
 *   <li>简单的值对象设计，专注于数据存储</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class Manufacturer {
    private final int manufactureId;

    /**
     * 制造商名称
     * <p>制造商的唯一标识名称，如："小米", "华为", "苹果"等</p>
     */
    private String name;

    /**
     * 所支持的连接方式集合
     * <p>包含该制造商设备支持的所有连接方式，如：WIFI、BLUETOOTH等</p>
     * <p>使用Set确保连接方式的唯一性</p>
     */
    private String protocols;
    private List<Device> devices = new ArrayList<>();


    public Manufacturer(int manufactureId) {
        this.manufactureId = manufactureId;
    }

    public Manufacturer(int manufactureId, String name, String protocols) {
        this.manufactureId = manufactureId;
        this.name = name;
        this.protocols = protocols;
    }
    // ==================== Getter and Setter方法 ====================


    public int getManufactureId() {
        return manufactureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProtocols() {
        return protocols;
    }

    public void setProtocols(String protocols) {
        this.protocols = protocols;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    // ==================== 业务逻辑 ====================
    public void addDevices(Device device){
        devices.add(device);
    }

    public void removeDevices(Device device){
        devices.remove(device);
    }
}