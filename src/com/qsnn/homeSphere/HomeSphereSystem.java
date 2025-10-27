package com.qsnn.homeSphere;

import com.qsnn.homeSphere.domain.automationScene.AutomationScene;
import com.qsnn.homeSphere.domain.automationScene.DeviceAction;
import com.qsnn.homeSphere.domain.deviceModule.Device;
import com.qsnn.homeSphere.domain.deviceModule.devices.AirConditioner;
import com.qsnn.homeSphere.domain.deviceModule.devices.BathroomScale;
import com.qsnn.homeSphere.domain.deviceModule.devices.LightBulb;
import com.qsnn.homeSphere.domain.deviceModule.devices.SmartLock;
import com.qsnn.homeSphere.domain.deviceModule.services.EnergyReporting;
import com.qsnn.homeSphere.domain.deviceModule.services.Manufacturer;
import com.qsnn.homeSphere.domain.deviceModule.services.RunningLog;
import com.qsnn.homeSphere.domain.house.Household;
import com.qsnn.homeSphere.domain.house.Room;
import com.qsnn.homeSphere.domain.users.User;

import java.util.Date;
import java.util.List;

/**
 * 智能家居系统主控类
 *
 * <p>该类是整个智能家居系统的核心控制器，负责用户管理、设备展示和场景控制等功能。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>用户登录、注销和注册管理</li>
 *   <li>系统各模块信息的展示和查询</li>
 *   <li>自动化场景的手动触发</li>
 *   <li>能源消耗报告生成</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>作为系统入口点，协调各个域模块的交互</li>
 *   <li>提供统一的用户界面操作方法</li>
 *   <li>支持系统状态的实时监控和展示</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class HomeSphereSystem {

    /** 家庭住户信息 */
    private Household household;

    /** 当前登录用户 */
    private User currentUser;


    /**
     * 系统构造函数
     *
     * @param household 家庭住户信息
     */
    public HomeSphereSystem(Household household) {
        this.household = household;
    }

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户对象，如果未登录则返回null
     */
    public User getCurrentUser() {
        return currentUser;
    }

    public Household getHousehold() {
        return household;
    }


    /**
     * 用户登录
     *
     * @param loginName 用户名
     * @param loginPassword 用户密码
     */
    public boolean login(String loginName, String loginPassword) {
        logoff();
        User correctUser = containsUser(loginName);
        if (correctUser == null || !correctUser.getLoginPassword().equals(loginPassword)) {
            System.out.println("登录失败：用户名或密码错误！");
            return false;
        }
        System.out.println("User logged in");
        currentUser = correctUser;
        return true;
    }

    /**
     * 用户注销
     */
    public void logoff() {
        currentUser = null;
    }

    /**
     * 用户注册
     *
     * @param loginName     用户名
     * @param loginPassword 用户密码
     * @param email         用户邮箱
     */
    public void register(String loginName, String loginPassword, String email, boolean isAdmin) {
        if (household.containsUser(loginName) != null) {
        } else {
            User user = new User(household.getUsers().size() + 1, loginName, loginPassword, email, isAdmin);
            household.addUser(user);
        }
    }

    public void removeUser(int userId) {
        household.removeUser(userId);
    }

    public String listUsers() {
        StringBuilder sb = new StringBuilder();
        sb.append("===家庭成员列表===\n");
        household.getUsers().values().forEach(user -> {
            sb.append("ID：").append(user.getUserId()).append("\n");
            sb.append("用户名：").append(user.getLoginName()).append("\n");
            sb.append("邮箱：").append(user.getEmail()).append("\n");
            sb.append("管理员：").append(user.isAdmin() ? "是" : "否").append("\n");
            sb.append("-------------------\n");
        });
        return sb.toString();
    }

    public void createDevice(String deviceName, int deviceType, Manufacturer manufacturer, int roomId) {
        if (!household.getRooms().containsKey(roomId)) {
            return;
        }
        int deviceId = household.getAllDevices().size() + 1;
        Device device = switch (deviceType) {
            case 1 -> new AirConditioner(deviceId, deviceName, manufacturer);
            case 2 -> new LightBulb(deviceId, deviceName, manufacturer);
            case 3 -> new SmartLock(deviceId, deviceName, manufacturer);
            case 4 -> new BathroomScale(deviceId, deviceName, manufacturer);
            default -> null;
        };
        if (device == null) {
            return;
        }
        household.getRooms().get(roomId).addDevice(device);
    }

    public void removeDevice(int deviceId) {
        household.removeDevice(deviceId);
    }

    public String listDevices() {
        StringBuilder sb = new StringBuilder();
        sb.append("===设备列表===\n");
        household.getRooms().values().forEach(room -> {
            sb.append("房间：").append(room.getName()).append("\n");
            room.getDevices().forEach(device -> sb.append(device.toString()).append("\n"));
            sb.append("-------------------\n");
        });
        return sb.toString();
    }

    public String getDeviceType(int deviceId) {
        Device device = household.getDeviceById(deviceId);
        if (device != null) {
            return device.getClass().getSimpleName();
        }
        return null;
    }

    public int createScene(String sceneName, String description) {
        int sceneId = household.getAutoScenes().size() + 1;
        household.addAutoScene(new AutomationScene(sceneId, sceneName, description));
        return sceneId;
    }

    public void addSceneOperation(int sceneId, int deviceId, String operation, String parameter) {
        AutomationScene scene = household.getAutoSceneById(sceneId);
        if (scene != null) {
            scene.addAction(new DeviceAction(operation, parameter, household.getDeviceById(deviceId)));
        }
    }

    public AutomationScene getSceneById(int sceneId) {
        return household.getAutoSceneById(sceneId);
    }

    public String listScenes() {
        StringBuilder sb = new StringBuilder();
        sb.append("===智能场景列表===\n");
        household.getAutoScenes().values().forEach(scene -> {
            sb.append("ID：").append(scene.getSceneId()).append("\n");
            sb.append("名称：").append(scene.getName()).append("\n");
            sb.append("描述：").append(scene.getDescription()).append("\n");
            sb.append("-------------------\n");
        });
        return sb.toString();
    }

    public String listRunningLogs() {
        StringBuilder res = new StringBuilder();
        res.append("== 设备运行日志 ==\n");

        household.getRooms().values().forEach(room -> {
            res.append("房间：").append(room.getName()).append("\n");

            if (room.getDevices().isEmpty()) {
                res.append("（无设备）\n");
            } else {
                room.getDevices().forEach(device -> {
                    res.append("ID: ").append(device.getDeviceId()).append("\n");
                    res.append("名称：").append(device.getName()).append("\n");
                    res.append("类型：").append(device.getClass().getSimpleName()).append("\n");
                    res.append("运行日志：\n");

                    // 获取设备运行日志
                    List<RunningLog> logs = device.getRunningLogs();
                    if (logs == null || logs.isEmpty()) {
                        res.append("---\n");
                    } else {
                        logs.forEach(log -> res.append(log.toString()).append("\n"));
                    }
                    res.append("\n");
                });
            }
            res.append("-------------------\n");
        });
        return res.toString();
    }

    public String generateEnergyReport(Date startTime, Date endTime) {
        StringBuilder res = new StringBuilder();
        res.append("== 能耗报告 ==\n");
        double totalEnergy = 0.0;

        for (Room room : household.getRooms().values()) {
            res.append("房间：").append(room.getName()).append("\n");
            for (Device device : room.getDevices()) {
                if (device instanceof EnergyReporting) {
                    double deviceEnergy = ((EnergyReporting) device).getReport(startTime, endTime);
                    totalEnergy += deviceEnergy;
                    res.append("设备: ").append(device.getName()).append(" - ")
                            .append(String.format("%.1f", deviceEnergy)).append(" kWh\n");
                }
            }
            res.append("\n");
        }

        res.append("总能耗: ").append(String.format("%.1f", totalEnergy)).append(" kWh\n");
        return res.toString();
    }

    /**
     * 根据场景ID手动触发自动化场景
     *
     * @param sceneId 场景ID
     */
    public void manualTrigSceneById(int sceneId) {
        household.getAutoSceneById(sceneId).manualTrig();
    }

    public User containsUser(String loginName) {
        return household.containsUser(loginName);
    }
}
