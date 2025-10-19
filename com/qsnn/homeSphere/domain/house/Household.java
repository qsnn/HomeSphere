package qsnn.homeSphere.domain.house;

import qsnn.homeSphere.domain.automationScene.AutomationScene;
import qsnn.homeSphere.domain.deviceModule.Device;
import qsnn.homeSphere.domain.users.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 家庭类
 *
 * <p>该类表示一个智能家庭单位，包含家庭的基本信息、管理员设置和操作日志记录。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>管理家庭的基本信息（ID、名称、地址）</li>
 *   <li>设置和管理家庭管理员</li>
 *   <li>记录家庭相关的操作日志</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>家庭ID为final，确保唯一性和不变性</li>
 *   <li>使用TreeSet存储日志，按时间排序</li>
 *   <li>在构造时自动记录家庭创建日志</li>
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

    private User admin;

    private Map<Integer, User> users;

    private Map<Integer, Room> rooms;

    private Map<Integer, AutomationScene> autoScenes;

    public Household(int householdId, String address) {
        this.householdId = householdId;
        this.address = address;
        this.users = new HashMap<>();
        this.rooms = new HashMap<>();
        this.autoScenes = new HashMap<>();
    }

    public int getHouseholdId() {
        return householdId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public List<User> getUsers() {
        return users.values().stream().toList();
    }

    public List<Room> getRooms() {
        return rooms.values().stream().toList();
    }

    public List<AutomationScene> getAutoScenes() {
        return autoScenes.values().stream().toList();
    }

    public AutomationScene getAutoSceneById(int sceneId) {
        return autoScenes.get(sceneId);
    }

    public void addUser(User user){
        users.put(user.getUserId(), user);
    }

    public void removeUser(int userId){
        users.remove(userId);
    }

    public void addRoom(Room room){
        rooms.put(room.getRoomId(), room);
    }

    public void removeRoom(int roomId){
        rooms.remove(roomId);
    }

    public void addAutoScene(AutomationScene autoScene){
        autoScenes.put(autoScene.getSceneId(), autoScene);
    }

    public void removeAutoScene(int sceneId){
        autoScenes.remove(sceneId);
    }

    public List<Device> listAllDevices(){
        return rooms.values().stream().flatMap(room -> room.getDevices().stream()).toList();
    }


    // 检查是否存在的方法
    public boolean containsUser(String loginName) {
        return users.values().stream().anyMatch(u -> u.getLoginName().equals(loginName));
    }

    public boolean containsRoom(int roomId) {
        return rooms.containsKey(roomId);
    }

    public boolean containsAutoScene(int sceneId) {
        return autoScenes.containsKey(sceneId);
    }
}