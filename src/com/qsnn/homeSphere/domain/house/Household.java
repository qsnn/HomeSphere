package com.qsnn.homeSphere.domain.house;

import com.qsnn.homeSphere.domain.automationScene.AutomationScene;
import com.qsnn.homeSphere.domain.deviceModule.Device;
import com.qsnn.homeSphere.domain.users.User;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 家庭类
 *
 * <p>该类表示一个智能家庭单位，包含家庭的基本信息、成员管理、房间管理和自动化场景配置。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>管理家庭的基本信息（ID、地址）</li>
 *   <li>管理家庭成员和权限设置</li>
 *   <li>管理家庭内的房间和设备</li>
 *   <li>配置和执行自动化场景</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>家庭ID为final，确保唯一性和不变性</li>
 *   <li>使用Map结构存储数据，便于快速查找</li>
 *   <li>自动设置第一个用户为管理员</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class Household {

    /** 家庭唯一标识符 */
    private final int householdId;

    /** 家庭地址 */
    private String address;

    /** 家庭成员集合，键为用户ID，值为用户对象 */
    private final Map<Integer, User> users;

    /** 下一个可用的用户ID */
    private int nextUserId = 1;

    /** 房间集合，键为房间ID，值为房间对象 */
    private final Map<Integer, Room> rooms;

    /** 下一个可用的设备ID */
    private int nextDeviceId = 1;

    /** 自动化场景集合，键为场景ID，值为场景对象 */
    private final Map<Integer, AutomationScene> autoScenes;

    /**
     * 家庭构造函数
     *
     * @param householdId 家庭唯一标识符
     * @param address 家庭地址
     */
    public Household(int householdId, String address) {
        this.householdId = householdId;
        this.address = address;
        this.users = new HashMap<>();
        this.rooms = new HashMap<>();
        this.autoScenes = new HashMap<>();
    }

    /**
     * 获取家庭ID
     *
     * @return 家庭唯一标识符
     */
    public int getHouseholdId() {
        return householdId;
    }

    /**
     * 获取家庭地址
     *
     * @return 家庭地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置家庭地址
     *
     * @param address 新的家庭地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取所有家庭成员列表
     *
     * @return 家庭成员列表
     */
    public Map<Integer, User> getUsers() {
        return users;
    }

    /**
     * 获取所有房间列表
     *
     * @return 房间列表
     */
    public Map<Integer, Room> getRooms() {
        return rooms;
    }

    /**
     * 获取所有自动化场景列表
     *
     * @return 自动化场景列表
     */
    public Map<Integer, AutomationScene> getAutoScenes() {
        return autoScenes;
    }

    /**
     * 根据场景ID获取自动化场景
     *
     * @param sceneId 场景ID
     * @return 自动化场景对象，如果不存在则返回null
     */
    public AutomationScene getAutoSceneById(int sceneId) {
        return autoScenes.get(sceneId);
    }

    /**
     * 获取下一个可用的用户ID
     *
     * @return 下一个用户ID
     */
    public int getNextUserId() {
        return nextUserId++;
    }

    /**
     * 添加用户到家庭
     *
     * @param user 要添加的用户
     */
    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    /**
     * 从家庭移除用户
     *
     * @param userId 要移除的用户ID
     */
    public void removeUser(int userId) {
        users.remove(userId);
    }

    /**
     * 添加房间到家庭
     *
     * @param room 要添加的房间
     */
    public void addRoom(Room room) {
        rooms.put(room.getRoomId(), room);
    }

    /**
     * 从家庭移除房间
     *
     * @param roomId 要移除的房间ID
     */
    public void removeRoom(int roomId) {
        rooms.remove(roomId);
    }

    /**
     * 获取下一个可用的设备ID
     *
     * @return 下一个设备ID
     */
    public int getNextDeviceId() {
        return nextDeviceId++;
    }

    /**
     * 添加设备到指定房间
     *
     * @param device 要添加的设备
     * @param roomId 房间ID
     */
    public void addDevice(Device device, int roomId) {
        rooms.get(roomId).addDevice(device);
    }

    /**
     * 从家庭中移除设备
     *
     * @param deviceId 要移除的设备ID
     */
    public void removeDevice(int deviceId) {
        for (Room room : rooms.values()) {
            if (room.getDeviceById(deviceId) != null) {
                room.removeDevice(deviceId);
                return;
            }
        }
    }

    /**
     * 根据设备ID获取设备
     *
     * @param deviceId 设备ID
     * @return 设备对象，如果不存在则返回null
     */
    public Device getDeviceById(int deviceId) {
        for (Room room : rooms.values()) {
            Device device = room.getDeviceById(deviceId);
            if (device != null) {
                return device;
            }
        }
        return null;
    }

    /**
     * 获取家庭中所有设备列表
     *
     * @return 所有设备列表
     */
    public Map<Integer, Device> getAllDevices() {
        return rooms.values().stream()
                .flatMap(room -> room.getDevices().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    /**
     * 添加自动化场景到家庭
     *
     * @param autoScene 要添加的自动化场景
     */
    public void addAutoScene(AutomationScene autoScene) {
        autoScenes.put(autoScene.getSceneId(), autoScene);
    }

    /**
     * 从家庭移除自动化场景
     *
     * @param sceneId 要移除的场景ID
     */
    public void removeAutoScene(int sceneId) {
        autoScenes.remove(sceneId);
    }

    /**
     * 检查是否存在指定用户名的用户
     *
     * @param loginName 用户名
     * @return 如果存在返回对应的用户对象，否则返回null
     */
    public User containsUser(String loginName) {
        for (User user : users.values()) {
            if (user.getLoginName().equals(loginName)) {
                return user;
            }
        }
        return null;
    }

    /**
     * 检查是否存在指定ID的房间
     *
     * @param roomId 房间ID
     * @return 如果存在返回true，否则返回false
     */
    public boolean containsRoom(int roomId) {
        return rooms.containsKey(roomId);
    }

    /**
     * 检查是否存在指定ID的自动化场景
     *
     * @param sceneId 场景ID
     * @return 如果存在返回true，否则返回false
     */
    public boolean containsAutoScene(int sceneId) {
        return autoScenes.containsKey(sceneId);
    }
}