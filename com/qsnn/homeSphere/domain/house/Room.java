package qsnn.homeSphere.domain.house;

import qsnn.homeSphere.domain.deviceModule.Device;

import java.util.List;

/**
 * 房间类
 *
 * <p>该类表示房屋中的一个房间，包含房间的基本信息和相关日志记录。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>管理房间的基本属性（ID、名称、面积、介绍）</li>
 *   <li>维护房间相关的操作日志</li>
 *   <li>提供房间信息的字符串表示</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>房间ID为final，确保唯一性和不变性</li>
 *   <li>使用TreeSet存储日志，按时间排序</li>
 *   <li>在构造时自动记录房间创建日志</li>
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

    private List<Device> devices;

    /**
     * 房间构造函数
     *
     * <p>创建房间时会自动记录创建日志到房间日志集合中。</p>
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

    public List<Device> getDevices() {
        return devices;
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