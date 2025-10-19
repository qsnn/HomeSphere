package com.qsnn.homeSphere.domain.house;

import com.qsnn.homeSphere.domain.deviceModule.Device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 房间类
 *
 * <p>该类表示房屋中的一个房间，包含房间的基本信息和设备管理。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>管理房间的基本属性（ID、名称、面积）</li>
 *   <li>维护房间内的设备集合</li>
 *   <li>提供设备的添加、移除和查询功能</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>房间ID为final，确保唯一性和不变性</li>
 *   <li>使用Map结构存储设备，便于快速查找</li>
 *   <li>提供设备管理的便捷方法</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class Room {

    /** 房间唯一标识符 */
    private final int roomId;

    /** 房间名称 */
    private String name;

    /** 房间面积，单位：平方米 */
    private double area;

    /** 房间内的设备集合，键为设备ID，值为设备对象 */
    private final Map<Integer, Device> devices = new HashMap<>();

    /**
     * 房间构造函数
     *
     * @param roomId 房间唯一标识符
     * @param name 房间名称
     * @param area 房间面积，单位：平方米
     */
    public Room(int roomId, String name, double area) {
        this.roomId = roomId;
        this.name = name;
        this.area = area;
    }

    // ==================== Getter 方法 ====================

    /**
     * 获取房间ID
     *
     * @return 房间唯一标识符
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * 获取房间名称
     *
     * @return 房间名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取房间面积
     *
     * @return 房间面积，单位：平方米
     */
    public double getArea() {
        return area;
    }

    /**
     * 获取房间内的所有设备列表
     *
     * @return 设备列表
     */
    public List<Device> getDevices() {
        return devices.values().stream().toList();
    }

    /**
     * 根据设备ID获取特定设备
     *
     * @param deviceId 设备ID
     * @return 设备对象，如果不存在则返回null
     */
    public Device getDeviceById(int deviceId) {
        return devices.get(deviceId);
    }

    // ==================== Setter 方法 ====================

    /**
     * 设置房间名称
     *
     * @param name 新的房间名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 设置房间面积
     *
     * @param area 新的房间面积，单位：平方米
     */
    public void setArea(double area) {
        this.area = area;
    }

    // ==================== 设备管理方法 ====================

    /**
     * 添加设备到房间
     *
     * @param device 要添加的设备
     */
    public void addDevice(Device device) {
        if (device != null) {
            devices.put(device.getDeviceId(), device);
        }
    }

    /**
     * 从房间移除设备
     *
     * @param deviceId 要移除的设备ID
     */
    public void removeDevice(int deviceId) {
        devices.remove(deviceId);
    }

    /**
     * 检查房间是否包含指定设备
     *
     * @param deviceId 设备ID
     * @return 如果包含则返回true，否则返回false
     */
    public boolean containsDevice(int deviceId) {
        return devices.containsKey(deviceId);
    }

    /**
     * 获取房间内的设备数量
     *
     * @return 设备数量
     */
    public int getDeviceCount() {
        return devices.size();
    }

    /**
     * 清空房间内的所有设备
     */
    public void clearDevices() {
        devices.clear();
    }

    // ==================== Object类方法重写 ====================

    /**
     * 比较两个房间是否相等（基于房间ID）
     *
     * @param obj 要比较的对象
     * @return 如果房间ID相同则返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Room room = (Room) obj;
        return roomId == room.roomId;
    }

    /**
     * 返回对象的字符串表示形式
     * 格式：类名{属性1=属性1值, 属性2='属性2值',...}
     *
     * @return 格式化的字符串
     */
    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", name='" + name + '\'' +
                ", area=" + area +
                '}';
    }
}