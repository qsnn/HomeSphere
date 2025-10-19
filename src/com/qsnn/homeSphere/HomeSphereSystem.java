package com.qsnn.homeSphere;

import com.qsnn.homeSphere.domain.deviceModule.Device;
import com.qsnn.homeSphere.domain.deviceModule.attributes.DeviceAttribute;
import com.qsnn.homeSphere.domain.deviceModule.Manufacturer;
import com.qsnn.homeSphere.domain.deviceModule.devices.*;
import com.qsnn.homeSphere.domain.house.Household;
import com.qsnn.homeSphere.domain.house.Room;
import com.qsnn.homeSphere.domain.users.User;
import com.qsnn.homeSphere.log.Log;

import java.util.*;
import java.util.stream.Collectors;

import static com.qsnn.homeSphere.utils.Util.createFreeID;

/**
 * 智能家居系统核心类
 *
 * <p>该类是整个智能家居系统的核心管理类，负责统一管理用户、家庭、房间和设备等所有实体。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>用户注册、登录和权限管理</li>
 *   <li>家庭和房间的创建与管理</li>
 *   <li>设备的创建、分配和移动</li>
 *   <li>实体关系的维护和查询</li>
 *   <li>系统日志的统一管理</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>使用多个映射表维护实体间的关系</li>
 *   <li>提供完整的CRUD操作和关系管理</li>
 *   <li>包含权限验证和安全检查机制</li>
 *   <li>支持灵活的查询和统计功能</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class HomeSphereSystem {
    /** 系统版本号 */
    private final String version = "v1.0";

    // ==================== 统一数据源 ====================

    /** 家庭数据映射表 */
    private final Map<Integer, Household> households = new HashMap<>();

    /** 用户数据映射表 */
    private final Map<Integer, User> users = new HashMap<>();

    /** 房间数据映射表 */
    private final Map<Integer, Room> rooms = new HashMap<>();

    /** 设备数据映射表 */
    private final Map<Integer, Device> devices = new HashMap<>();

    // ==================== 统一的关系映射表 ====================

    /** 用户到家庭的映射关系（用户 -> 家庭集合） */
    private final Map<Integer, Set<Integer>> userToHouseholds = new HashMap<>();

    /** 家庭到用户的映射关系（家庭 -> 用户集合） */
    private final Map<Integer, Set<Integer>> householdToUsers = new HashMap<>();

    /** 家庭到房间的映射关系（家庭 -> 房间集合） */
    private final Map<Integer, Set<Integer>> householdToRooms = new HashMap<>();

    /** 房间到家庭的映射关系（房间 -> 家庭） */
    private final Map<Integer, Integer> roomToHousehold = new HashMap<>();

    /** 设备到房间的映射关系（设备 -> 房间） */
    private final Map<Integer, Integer> deviceToRoom = new HashMap<>();

    /**
     * 系统主入口方法
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 系统启动入口
    }

    // ==================== 用户相关方法 ====================

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 密码
     * @param name 用户真实姓名
     * @param address 用户地址
     * @return 新注册用户的ID
     */
    public Integer registerUser(String username, String password, String name, String address){
        User u = new User(createFreeID(users.keySet()), username, password, name, address);
        users.put(u.getUserID(), u);
        return u.getUserID();
    }

    /**
     * 用户登录
     *
     * @param userID 用户ID
     * @param password 密码
     * @return 登录成功的用户对象，登录失败返回null
     */
    public User login(int userID, String password){
        if(users.get(userID) == null || !users.get(userID).getPassword().equals(password)){
            return null;
        }
        return users.get(userID);
    }

    /**
     * 用户退出登录
     *
     * @param userID 用户ID
     */
    public void logout(int userID){
        // 退出登录逻辑
    }

    // ==================== 家庭相关方法 ====================

    /**
     * 创建家庭
     *
     * @param name 家庭名称
     * @param address 家庭地址
     * @param creatorID 创建者用户ID
     * @return 新创建家庭的ID
     */
    public Integer createHousehold(String name, String address, Integer creatorID){
        Household h = new Household(createFreeID(households.keySet()), name, address, creatorID);
        households.put(h.getHouseholdID(), h);
        createRelations(creatorID, h.getAdministratorID(), h.getHouseholdID());
        return h.getHouseholdID();
    }

    // ==================== 房间相关方法 ====================

    /**
     * 创建房间
     *
     * @param householdID 所属家庭ID
     * @param name 房间名称
     * @param area 房间面积
     * @return 新创建房间的ID
     */
    public Integer createRoom(Integer householdID, String name, Double area) {
        validateHousehold(householdID);

        Integer roomID = createFreeID(rooms.keySet());
        Room room = new Room(roomID, name, area);
        rooms.put(roomID, room);

        householdToRooms.computeIfAbsent(householdID, k -> new HashSet<>()).add(roomID);
        roomToHousehold.put(roomID, householdID);
        return room.getRoomID();
    }

    // ==================== 设备相关方法 ====================

    /**
     * 创建设备（不带自定义属性）
     *
     * @param deviceType 设备类型
     * @param deviceID 设备ID
     * @param name 设备名称
     * @param OS 设备操作系统
     * @param manufacturer 设备制造商
     * @param power 设备功率
     * @param connectMode 设备连接模式
     * @param powerMode 设备供电模式
     * @return 设备ID
     */
    public Integer createDevice(DeviceType deviceType, Integer deviceID, String name, String OS,
                                Manufacturer manufacturer, double power,
                                Device.ConnectMode connectMode, Device.PowerMode powerMode) {
        return createDevice(deviceType, deviceID, name, OS, manufacturer, power,
                connectMode, powerMode, null);
    }

    /**
     * 创建设备（带自定义属性）
     *
     * @param deviceType 设备类型
     * @param deviceID 设备ID
     * @param name 设备名称
     * @param OS 设备操作系统
     * @param manufacturer 设备制造商
     * @param power 设备功率
     * @param connectMode 设备连接模式
     * @param powerMode 设备供电模式
     * @param customAttributes 自定义属性集合
     * @return 设备ID
     */
    public Integer createDevice(DeviceType deviceType, Integer deviceID, String name, String OS,
                                Manufacturer manufacturer, double power,
                                Device.ConnectMode connectMode, Device.PowerMode powerMode,
                                Set<DeviceAttribute<?>> customAttributes) {

        // 1. 参数验证
        if (name == null || manufacturer == null) {
            throw new IllegalArgumentException("设备名称和制造商不能为空");
        }

        // 2. 根据设备类型创建具体设备
        Device device = createDeviceByType(deviceType, deviceID, name, OS, manufacturer,
                power, connectMode, powerMode, customAttributes);

        // 3. 记录到设备映射表
        devices.put(deviceID, device);

        return deviceID;
    }

    /**
     * 添加设备到房间
     *
     * @param deviceID 设备ID
     * @param roomID 房间ID
     * @return 添加是否成功
     */
    public boolean addDeviceToRoom(Integer deviceID, Integer roomID) {
        validateRoom(roomID);
        deviceToRoom.put(deviceID, roomID);
        return true;
    }

    /**
     * 移动设备到其他房间
     *
     * @param operatorUserID 操作者用户ID
     * @param deviceID 设备ID
     * @param targetRoomID 目标房间ID
     * @return 移动是否成功
     */
    public boolean moveDevice(Integer operatorUserID, Integer deviceID, Integer targetRoomID) {
        // 1. 参数基础验证
        if (operatorUserID == null || deviceID == null || targetRoomID == null) {
            throw new IllegalArgumentException("参数不能为空");
        }

        // 2. 验证设备存在
        Device device = devices.get(deviceID);
        if (device == null) {
            throw new IllegalArgumentException("设备不存在！");
        }

        // 3. 验证设备当前所在房间
        Integer currentRoomID = deviceToRoom.get(deviceID);
        if (currentRoomID == null) {
            throw new IllegalArgumentException("设备未分配到任何房间！");
        }

        // 4. 验证目标房间存在
        Room targetRoom = rooms.get(targetRoomID);
        if (targetRoom == null) {
            throw new IllegalArgumentException("目标房间不存在！");
        }

        // 5. 验证源房间和目标房间属于同一个家庭
        Integer sourceHouseholdID = roomToHousehold.get(currentRoomID);
        Integer targetHouseholdID = roomToHousehold.get(targetRoomID);

        if (!Objects.equals(sourceHouseholdID, targetHouseholdID)) {
            throw new IllegalArgumentException("只能在同一家庭内的房间之间移动设备！");
        }

        // 6. 验证操作者权限
        Set<Integer> householdUsers = householdToUsers.get(sourceHouseholdID);
        if (householdUsers == null || !householdUsers.contains(operatorUserID)) {
            throw new IllegalArgumentException("权限不足：只有家庭成员方可移动设备！");
        }

        // 7. 执行设备移动操作
        try {
            // 更新设备到房间的映射
            deviceToRoom.put(deviceID, targetRoomID);

            // 记录操作日志
            System.out.println("设备 " + deviceID + " 从房间 " + currentRoomID + " 移动到房间 " + targetRoomID);

            return true;

        } catch (Exception e) {
            System.err.println("移动设备失败: " + e.getMessage());
            return false;
        }
    }

    // ==================== 关系管理方法 ====================

    /**
     * 创建用户和家庭的关系
     *
     * @param operatorID 操作者用户ID
     * @param userId 要关联的用户ID
     * @param householdId 要关联的家庭ID
     */
    public void createRelations(Integer operatorID, Integer userId, Integer householdId) {
        if (!users.containsKey(userId) || !households.containsKey(householdId)) {
            throw new IllegalArgumentException("用户或家庭不存在");
        }
        if(!households.get(householdId).getAdministratorID().equals(operatorID)){
            throw new IllegalArgumentException("操作者权限不足");
        }

        userToHouseholds.computeIfAbsent(userId, k -> new HashSet<>()).add(householdId);
        householdToUsers.computeIfAbsent(householdId, k -> new HashSet<>()).add(userId);
    }

    /**
     * 从家庭中移除用户
     *
     * @param userId 用户ID
     * @param householdId 家庭ID
     * @return 移除是否成功
     */
    public boolean removeUserFromHousehold(Integer userId, Integer householdId) {
        boolean removedFromUser = false;
        boolean removedFromHousehold = false;

        // 从用户的家庭集合中删除
        Set<Integer> userHouseholds = userToHouseholds.get(userId);
        if (userHouseholds != null) {
            removedFromUser = userHouseholds.remove(householdId);
            if (userHouseholds.isEmpty()) {
                userToHouseholds.remove(userId);
            }
        }

        // 从家庭的用户集合中删除
        Set<Integer> householdUsers = householdToUsers.get(householdId);
        if (householdUsers != null) {
            removedFromHousehold = householdUsers.remove(userId);
            if (householdUsers.isEmpty()) {
                householdToUsers.remove(householdId);
            }
        }

        return removedFromUser || removedFromHousehold;
    }

    // ==================== 查询方法 ====================

    /**
     * 通过ID获取用户
     *
     * @param userId 用户ID
     * @return 用户对象
     */
    public User getUserByID(Integer userId) {
        return users.get(userId);
    }

    /**
     * 通过ID获取家庭
     *
     * @param householdId 家庭ID
     * @return 家庭对象
     */
    public Household getHouseholdByID(Integer householdId) {
        return households.get(householdId);
    }

    /**
     * 通过ID获取房间
     *
     * @param roomId 房间ID
     * @return 房间对象
     */
    public Room getRoomByID(Integer roomId) {
        return rooms.get(roomId);
    }

    /**
     * 通过ID获取设备
     *
     * @param deviceId 设备ID
     * @return 设备对象
     */
    public Device getDeviceByID(Integer deviceId) {
        return devices.get(deviceId);
    }

    /**
     * 获取用户的所有家庭
     *
     * @param userId 用户ID
     * @return 家庭集合
     */
    public Set<Household> getHouseholdsByUser(Integer userId) {
        Set<Integer> householdIds = userToHouseholds.getOrDefault(userId, Collections.emptySet());
        return householdIds.stream()
                .map(households::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /**
     * 获取家庭的所有用户
     *
     * @param householdId 家庭ID
     * @return 用户集合
     */
    public Set<User> getUsersByHousehold(Integer householdId) {
        Set<Integer> userIds = householdToUsers.getOrDefault(householdId, Collections.emptySet());
        return userIds.stream()
                .map(users::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /**
     * 获取家庭内的房间
     *
     * @param householdID 家庭ID
     * @return 房间集合
     */
    public Set<Room> getRoomsByHousehold(Integer householdID) {
        Set<Integer> roomIDs = householdToRooms.getOrDefault(householdID, Collections.emptySet());
        return roomIDs.stream()
                .map(rooms::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /**
     * 获取房间所在的家庭
     *
     * @param roomID 房间ID
     * @return 家庭对象
     */
    public Household getHouseholdByRoom(Integer roomID) {
        Integer householdID = roomToHousehold.get(roomID);
        return householdID != null ? households.get(householdID) : null;
    }

    /**
     * 获取房间内设备
     *
     * @param roomID 房间ID
     * @return 设备集合
     */
    public Set<Device> getDevicesByRoom(Integer roomID) {
        return devices.values().stream()
                .filter(device -> roomID.equals(deviceToRoom.get(device.getDeviceID())))
                .collect(Collectors.toSet());
    }

    /**
     * 获取设备所在房间
     *
     * @param deviceID 设备ID
     * @return 房间对象
     */
    public Room getRoomByDevice(Integer deviceID) {
        Integer roomID = deviceToRoom.get(deviceID);
        return roomID != null ? rooms.get(roomID) : null;
    }

    // ==================== 验证和统计方法 ====================

    /**
     * 检查用户是否在家庭中
     *
     * @param userId 用户ID
     * @param householdId 家庭ID
     * @return 如果用户在家庭中返回true，否则返回false
     */
    public boolean isUserInHousehold(Integer userId, Integer householdId) {
        Set<Integer> userHouseholds = userToHouseholds.get(userId);
        return userHouseholds != null && userHouseholds.contains(householdId);
    }

    /**
     * 统计用户的家庭数量
     *
     * @param userId 用户ID
     * @return 家庭数量
     */
    public int countHouseholdsByUser(Integer userId) {
        Set<Integer> userHouseholds = userToHouseholds.get(userId);
        return userHouseholds != null ? userHouseholds.size() : 0;
    }

    /**
     * 统计家庭的用户数量
     *
     * @param householdId 家庭ID
     * @return 用户数量
     */
    public int countUsersByHousehold(Integer householdId) {
        Set<Integer> householdUsers = householdToUsers.get(householdId);
        return householdUsers != null ? householdUsers.size() : 0;
    }

    // ==================== 日志相关方法 ====================

    /**
     * 获取所有设备日志
     *
     * @return 设备日志集合
     */
    public Set<Log> getAllDeviceLogs(){
        return devices.values().stream().map(Device::getDeviceLogs).flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    /**
     * 获取所有用户日志
     *
     * @return 用户日志集合
     */
    public Set<Log> getAllUserLogs(){
        return users.values().stream().map(User::getUserLogs).flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    /**
     * 获取所有房间日志
     *
     * @return 房间日志集合
     */
    public Set<Log> getAllRoomLogs(){
        return rooms.values().stream().map(Room::getRoomLogs).flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    /**
     * 获取所有家庭日志
     *
     * @return 家庭日志集合
     */
    public Set<Log> getAllHouseholdLogs(){
        return households.values().stream().map(Household::getHouseholdLogs).flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    // ==================== 私有方法 ====================

    /**
     * 根据设备类型创建具体设备实例
     */
    private Device createDeviceByType(DeviceType deviceType, Integer deviceID, String name, String OS,
                                      Manufacturer manufacturer, double power,
                                      Device.ConnectMode connectMode, Device.PowerMode powerMode,
                                      Set<DeviceAttribute<?>> customAttributes) {

        String brand = manufacturer.getName();

        switch (deviceType) {
            case AIR_CONDITIONER:
                return new AirConditioner(deviceID, name, OS, manufacturer, brand,
                        connectMode, powerMode, power);

            case LIGHT_BULB:
                return new LightBulb(deviceID, name, OS, manufacturer, brand,
                        connectMode, powerMode, power);

            case SMART_LOCK:
                return new SmartLock(deviceID, name, OS, manufacturer, brand,
                        connectMode, powerMode, power);

            case BATHROOM_SCALE:
                return new BathroomScale(deviceID, name, OS, manufacturer, brand,
                        connectMode, powerMode, power);

            case UNDEFINED:
                if (customAttributes == null || customAttributes.isEmpty()) {
                    throw new IllegalArgumentException("UndefinedDevice必须提供自定义属性");
                }
                return new UndefinedDevice(deviceID, name, OS, manufacturer, brand,
                        connectMode, powerMode, power, customAttributes);

            default:
                throw new IllegalArgumentException("不支持的设备类型: " + deviceType);
        }
    }

    /**
     * 检测用户是否存在
     */
    private void validateUser(Integer userID) {
        if (!users.containsKey(userID)) {
            throw new IllegalArgumentException("用户不存在");
        }
    }

    /**
     * 检测家庭是否存在
     */
    private void validateHousehold(Integer householdID) {
        if (!households.containsKey(householdID)) {
            throw new IllegalArgumentException("家庭不存在");
        }
    }

    /**
     * 检测房间是否存在
     */
    private void validateRoom(Integer roomID) {
        if (!rooms.containsKey(roomID)) {
            throw new IllegalArgumentException("房间不存在");
        }
    }

    // ==================== Getter方法 ====================

    public Map<Integer, User> getUsers() {
        return users;
    }

    public Map<Integer, Household> getHouseholds() {
        return households;
    }

    public Map<Integer, Room> getRooms() {
        return rooms;
    }

    public Map<Integer, Device> getDevices() {
        return devices;
    }
}