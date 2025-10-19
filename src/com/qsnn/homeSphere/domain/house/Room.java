package com.qsnn.homeSphere.domain.house;

import com.qsnn.homeSphere.log.Log;

import java.util.*;

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
 * @since 2024
 */
public class Room {

    /** 房间唯一标识符 */
    private final int roomID;

    /** 房间名称 */
    private String name;

    /** 房间面积，单位：平方米 */
    private double area;

    /** 房间介绍说明，默认为"null"字符串 */
    private String introduction = "null";

    /**
     * 房间日志记录集合
     * 使用TreeSet并按日志时间排序，确保日志按时间顺序存储
     */
    protected final Set<Log> roomLogs = new TreeSet<>(Comparator.comparing(Log::getT));

    /**
     * 房间构造函数
     *
     * <p>创建房间时会自动记录创建日志到房间日志集合中。</p>
     *
     * @param roomID 房间唯一标识符
     * @param name 房间名称
     * @param area 房间面积，单位：平方米
     */
    public Room(int roomID, String name, double area) {
        this.roomID = roomID;
        this.name = name;
        this.area = area;
        // 自动记录房间创建日志
        roomLogs.add(new Log(Integer.toString(getRoomID()),"创建房间：" + name, Log.LogType.INFO, this.toString())) ;
    }

    // ==================== Getter 方法 ====================

    /**
     * 获取房间ID
     *
     * @return 房间唯一标识符
     */
    public int getRoomID() {
        return roomID;
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
     * 获取房间介绍
     *
     * @return 房间介绍信息，默认为"null"字符串
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 获取房间日志集合
     *
     * <p>返回的日志集合按时间顺序排序</p>
     *
     * @return 房间的日志记录集合，按时间排序
     */
    public Set<Log> getRoomLogs() {
        return roomLogs;
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

    /**
     * 设置房间介绍
     *
     * @param introduction 新的房间介绍信息
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    // ==================== 重写方法 ====================

    /**
     * 返回房间的格式化字符串表示
     *
     * <p>格式：[房间ID - 房间名称 - 房间面积 - 房间介绍]</p>
     * <p>示例：[101 - 主卧室 - 15.5 - 朝南带阳台]</p>
     *
     * @return 格式化的房间信息字符串
     */
    @Override
    public String toString() {
        return new StringJoiner(" - ", "[", "]")
                .add(Integer.toString(roomID))
                .add(name)
                .add(Double.toString(area))
                .add(introduction)
                .toString();
    }

    /**
     * 比较两个房间对象是否相等
     *
     * <p>基于房间ID进行比较，因为房间ID是唯一标识符</p>
     *
     * @param obj 要比较的对象
     * @return 如果房间ID相同返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Room room = (Room) obj;
        return roomID == room.roomID;
    }

    /**
     * 生成房间对象的哈希码
     *
     * <p>基于房间ID生成哈希码，确保相同ID的房间有相同的哈希码</p>
     *
     * @return 房间的哈希码值
     */
    @Override
    public int hashCode() {
        return Objects.hash(roomID);
    }

    // ==================== 业务方法 ====================

    /**
     * 添加日志到房间日志记录
     *
     * <p>由于roomLogs是protected，子类可以直接访问添加日志</p>
     *
     * @param log 要添加的日志对象
     * @return 如果日志添加成功返回true，如果日志已存在返回false
     */
    protected boolean addLog(Log log) {
        return roomLogs.add(log);
    }

    /**
     * 获取房间基本信息摘要
     *
     * <p>返回房间的核心信息，不包含介绍和日志</p>
     *
     * @return 房间基本信息字符串
     */
    public String getRoomSummary() {
        return String.format("房间%d: %s (%.1f㎡)", roomID, name, area);
    }

    /**
     * 检查房间是否有介绍信息
     *
     * <p>判断介绍是否为null或默认的"null"字符串</p>
     *
     * @return 如果有有效的介绍信息返回true，否则返回false
     */
    public boolean hasIntroduction() {
        return introduction != null && !introduction.equals("null") && !introduction.trim().isEmpty();
    }
}