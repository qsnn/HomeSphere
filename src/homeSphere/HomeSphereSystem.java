package homeSphere;

import homeSphere.domain.devices.Device;
import homeSphere.domain.house.Household;
import homeSphere.domain.house.Room;
import homeSphere.domain.users.User;
import homeSphere.service.manufacturer.Manufacturer;

import java.util.*;
import java.util.stream.Collectors;

import static homeSphere.utils.Util.createFreeID;

public class HomeSphereSystem {
    private final String version = "v1.0";
    //统一数据源
    private final Map<Integer, Household> households = new HashMap<>();
    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Room> rooms = new HashMap<>();
    private final Map<Integer, Device> devices = new HashMap<>();

    // 统一的关系映射表
    private final Map<Integer, Set<Integer>> userToHouseholds = new HashMap<>(); // 用户 -> 家庭集合
    private final Map<Integer, Set<Integer>> householdToUsers = new HashMap<>(); // 家庭 -> 用户集合
    private final Map<Integer, Set<Integer>> householdToRooms = new HashMap<>(); // 家庭 -> 房间集合
    private final Map<Integer, Integer> roomToHousehold = new HashMap<>();       // 房间 -> 家庭
    private final Map<Integer, Integer> deviceToRoom = new HashMap<>();          // 设备 -> 房间


    //创建家庭
    public Household createHousehold(String name, String address, User creator){
        Household h = new Household(createFreeID(households.keySet()), name, address, creator);
        households.put(h.getHouseholdID(), h);
        creator.addHousehold(h);
        return h;
    }

    //注册用户
    public User registerUser(String username, String password, String name, String address){
        return new User(createFreeID(users.keySet()), username, password, name, address);
    }

    // 获取用户
    public User getUserByID(Integer userId) {
        return users.get(userId);
    }

    // 获取家庭
    public Household getHouseholdByID(Integer householdId) {
        return households.get(householdId);
    }

    // 添加用户和家庭的关系
    public void createRelations(Integer userId, Integer householdId) {
        if (!users.containsKey(userId) || !households.containsKey(householdId)) {
            throw new IllegalArgumentException("用户或家庭不存在");
        }

        userToHouseholds.computeIfAbsent(userId, k -> new HashSet<>()).add(householdId);
        householdToUsers.computeIfAbsent(householdId, k -> new HashSet<>()).add(userId);
    }

    // 获取用户的所有家庭
    public Set<Household> getHouseholdsByUser(Integer userId) {
        Set<Integer> householdIds = userToHouseholds.getOrDefault(userId, Collections.emptySet());
        return householdIds.stream()
                .map(households::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    // 获取家庭的所有用户
    public Set<User> getUsersByHousehold(Integer householdId) {
        Set<Integer> userIds = householdToUsers.getOrDefault(householdId, Collections.emptySet());
        return userIds.stream()
                .map(users::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    // 删除用户从家庭的关系
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

    // 检查用户是否在家庭中
    public boolean isUserInHousehold(Integer userId, Integer householdId) {
        Set<Integer> userHouseholds = userToHouseholds.get(userId);
        return userHouseholds != null && userHouseholds.contains(householdId);
    }

    // 统计用户的家庭数量
    public int countHouseholdsByUser(Integer userId) {
        Set<Integer> userHouseholds = userToHouseholds.get(userId);
        return userHouseholds != null ? userHouseholds.size() : 0;
    }

    // 统计家庭的用户数量
    public int countUsersByHousehold(Integer householdId) {
        Set<Integer> householdUsers = householdToUsers.get(householdId);
        return householdUsers != null ? householdUsers.size() : 0;
    }

    //创造房间
    public Room createRoom(Integer householdID, String name, Double area) {
        validateHousehold(householdID);

        Integer roomID = createFreeID(rooms.keySet());
        Room room = new Room(roomID, name, area);
        rooms.put(roomID, room);

        householdToRooms.computeIfAbsent(householdID, k -> new HashSet<>()).add(roomID);
        roomToHousehold.put(roomID, householdID);
        return room;
    }

    // === 设备管理 ===
    public Device createDevice(){
        return null;
    }
    public boolean addDeviceToRoom(Device device, Integer roomID) {
        validateRoom(roomID);
        devices.put(device.getDeviceID(), device);
        deviceToRoom.put(device.getDeviceID(), roomID);
        return true;
    }

    public boolean moveDevice(Integer operatorUserID, Integer deviceID, Integer targetRoomID) {
        // 1. 参数验证
        if (operatorUserID == null || deviceID == null || targetRoomID == null) {
            throw new IllegalArgumentException("参数不能为空");
        }

        // 2. 验证设备存在且获取当前房间
        Device device = devices.get(deviceID);
        if (device == null) {
            throw new IllegalArgumentException("设备不存在！");
        }

        Integer currentRoomID = deviceToRoom.get(deviceID);
        if (currentRoomID == null) {
            throw new IllegalArgumentException("设备未分配到任何房间！");
        }

        // 3. 验证目标房间存在
        Room targetRoom = rooms.get(targetRoomID);
        if (targetRoom == null) {
            throw new IllegalArgumentException("目标房间不存在！");
        }

        // 4. 验证源房间和目标房间属于同一个家庭
        Integer sourceHouseholdID = roomToHousehold.get(currentRoomID);
        Integer targetHouseholdID = roomToHousehold.get(targetRoomID);

        if (!Objects.equals(sourceHouseholdID, targetHouseholdID)) {
            throw new IllegalArgumentException("只能在同一家庭内的房间之间移动设备！");
        }

        // 5. 验证操作者权限
        if (!hasPermission(operatorUserID, sourceHouseholdID)) {
            throw new IllegalArgumentException("权限不足：只有家庭成员方可移动设备！");
        }

        // 6. 执行移动操作
        return performDeviceMove(deviceID, currentRoomID, targetRoomID);
    }

    /**
     * 检查用户是否有操作权限
     */
    private boolean hasPermission(Integer userID, Integer householdID) {
        Set<Integer> householdUsers = householdToUsers.get(householdID);
        return householdUsers != null && householdUsers.contains(userID);
    }

    /**
     * 执行设备移动的核心逻辑
     */
    private boolean performDeviceMove(Integer deviceID, Integer fromRoomID, Integer toRoomID) {
        try {
            // 更新设备到房间的映射
            deviceToRoom.put(deviceID, toRoomID);

            // 这里可以添加额外的业务逻辑，比如：
            // - 记录操作日志
            // - 发送设备状态更新通知
            // - 更新设备最后移动时间等

            System.out.println("设备 " + deviceID + " 从房间 " + fromRoomID + " 移动到房间 " + toRoomID);
            return true;

        } catch (Exception e) {
            System.err.println("移动设备失败: " + e.getMessage());
            return false;
        }
    }


    // 房间相关查询
    public Set<Room> getRoomsByHousehold(Integer householdID) {
        Set<Integer> roomIDs = householdToRooms.getOrDefault(householdID, Collections.emptySet());
        return roomIDs.stream()
                .map(rooms::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public Household getHouseholdByRoom(Integer roomID) {
        Integer householdID = roomToHousehold.get(roomID);
        return householdID != null ? households.get(householdID) : null;
    }

    public Set<Device> getDevicesByRoom(Integer roomID) {
        return devices.values().stream()
                .filter(device -> roomID.equals(deviceToRoom.get(device.getDeviceID())))
                .collect(Collectors.toSet());
    }

    // 设备相关查询
    public Room getRoomByDevice(Integer deviceID) {
        Integer roomID = deviceToRoom.get(deviceID);
        return roomID != null ? rooms.get(roomID) : null;
    }

    //登录
    public User login(int userID, String password){
        if(users.get(userID) == null || !users.get(userID).getPassword().equals(password)){
            return null;
        }
        return users.get(userID);
    }

    //退出登录
    public void logout(int userID){
    }

    //检测用户是否存在
    private void validateUser(Integer userID) {
        if (!users.containsKey(userID)) {
            throw new IllegalArgumentException("用户不存在");
        }
    }

    //检测家庭是否存在
    private void validateHousehold(Integer householdID) {
        if (!households.containsKey(householdID)) {
            throw new IllegalArgumentException("家庭不存在");
        }
    }

    //检测房间是否存在
    private void validateRoom(Integer roomID) {
        if (!rooms.containsKey(roomID)) {
            throw new IllegalArgumentException("房间不存在");
        }
    }
}
