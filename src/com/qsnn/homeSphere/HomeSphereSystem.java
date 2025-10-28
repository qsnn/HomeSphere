package com.qsnn.homeSphere;

import com.qsnn.homeSphere.domain.automationScene.AutomationScene;
import com.qsnn.homeSphere.domain.automationScene.DeviceAction;
import com.qsnn.homeSphere.domain.deviceModule.Device;
import com.qsnn.homeSphere.domain.deviceModule.devices.*;
import com.qsnn.homeSphere.domain.deviceModule.services.EnergyReporting;
import com.qsnn.homeSphere.domain.deviceModule.services.Manufacturer;
import com.qsnn.homeSphere.domain.deviceModule.services.RunningLog;
import com.qsnn.homeSphere.domain.house.Household;
import com.qsnn.homeSphere.domain.house.Room;
import com.qsnn.homeSphere.domain.users.User;
import com.qsnn.homeSphere.exceptions.*;

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
    private final Household household;

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
    public void login(String loginName, String loginPassword) throws InvalidUserException {
        logoff();
        User correctUser = household.containsUser(loginName);
        if (correctUser == null || !correctUser.getLoginPassword().equals(loginPassword)) {
            throw new InvalidUserException("登录失败：用户名或密码错误！");
        }
        currentUser = correctUser;
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
        // 添加参数验证
        if (loginName == null || loginName.trim().isEmpty()) {
            throw new InvalidUserException("注册失败：用户名不能为空！");
        }
        if (loginPassword == null || loginPassword.trim().isEmpty()) {
            throw new InvalidUserException("注册失败：密码不能为空！");
        }
        if (email == null) {
            throw new InvalidUserException("注册失败：邮箱不能为空！");
        }

        if (household.containsUser(loginName) != null) {
            throw new InvalidUserException("注册失败：用户名已存在！");
        }
        User user = new User(household.getNextUserId(), loginName, loginPassword, email, isAdmin);
        household.addUser(user);
    }

    public void removeUser(int userId) {
        if (!household.getUsers().containsKey(userId)) {
            throw new InvalidUserException("删除失败：用户不存在！");
        }
        if (household.getUsers().get(userId).isAdmin()) {
            throw new InvalidUserException("删除失败：不能删除管理员用户！");
        }
        if (currentUser != null && currentUser.getUserId() == userId) {
            throw new InvalidUserException("删除失败：不能删除当前登录用户！");
        }
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

    public void createRoom(String roomName, double area) {
        if (roomName == null || roomName.trim().isEmpty()) {
            throw new InvalidRoomException("添加失败：房间名称不能为空！");
        }
        int roomId = household.getRooms().size() + 1;
        Room room = new Room(roomId, roomName, area);
        household.addRoom(room);
    }

    public Room getRoomById(int roomId) {
        if (!household.getRooms().containsKey(roomId)) {
            throw new InvalidRoomException("房间不存在！");
        }
        return household.getRooms().get(roomId);
    }

    public void createDevice(String deviceName, DeviceType deviceType, Manufacturer manufacturer, int roomId) {
        if (!household.containsRoom(roomId)) {
            throw new InvalidRoomException("添加失败：房间不存在！");
        }
        if (deviceName == null || deviceName.trim().isEmpty()) {
            throw new InvalidDeviceException("添加失败：设备名称不能为空！");
        }
        int deviceId = household.getNextDeviceId();
        Device device = switch (deviceType) {
            case AIR_CONDITIONER -> new AirConditioner(deviceId, deviceName, manufacturer);
            case LIGHT_BULB -> new LightBulb(deviceId, deviceName, manufacturer);
            case SMART_LOCK -> new SmartLock(deviceId, deviceName, manufacturer);
            case BATHROOM_SCALE -> new BathroomScale(deviceId, deviceName, manufacturer);
        };
        household.addDevice(device, roomId);
    }

    public void removeDevice(int deviceId) {
        if(getDeviceById(deviceId) == null){
            throw new InvalidDeviceException("删除失败：设备不存在！");
        }
        household.removeDevice(deviceId);
    }

    public Device getDeviceById(int deviceId) {
        if (!household.getAllDevices().containsKey(deviceId)){
            throw new InvalidDeviceException("设备不存在！");
        }
        return household.getDeviceById(deviceId);
    }

    public String listDevices() {
        StringBuilder sb = new StringBuilder();
        sb.append("===设备列表===\n");
        household.getRooms().values().forEach(room -> {
            sb.append("房间：").append(room.getName()).append("\n");
            room.getDevices().values().forEach(device -> sb.append(device.toString()).append("\n"));
            sb.append("-------------------\n");
        });
        return sb.toString();
    }

    public int createScene(String sceneName, String description) {
        if (sceneName == null || sceneName.trim().isEmpty()) {
            throw new InvalidAutomationSceneException("创建失败：场景名称不能为空！");
        }
        int sceneId = household.getAutoScenes().size() + 1;
        household.addAutoScene(new AutomationScene(sceneId, sceneName, description));
        return sceneId;
    }

    public void addSceneOperation(int sceneId, int deviceId, String operation, String parameter) {
        // 验证参数（根据操作类型）
        validateOperationParameters(operation, parameter);

        AutomationScene scene = getSceneById(sceneId);
        scene.addAction(new DeviceAction(operation, parameter, getDeviceById(deviceId)));
    }

    /**
     * 根据操作类型验证参数
     */
    private void validateOperationParameters(String operation, String parameter) {
        String op = operation.toLowerCase().trim();

        switch (op) {
            case "settemperature":
                if (parameter == null || parameter.trim().isEmpty()) {
                    throw new InvalidParametersException("settemperature操作需要温度参数");
                }
                try {
                    double temperature = Double.parseDouble(parameter.trim());
                    if (temperature < 16.0 || temperature > 32.0) {
                        throw new InvalidParametersException("空调温度设置应在16.0-32.0度之间");
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidParametersException("无效的温度参数格式");
                }
                break;

            case "setbrightness":
                if (parameter == null || parameter.trim().isEmpty()) {
                    throw new InvalidParametersException("setbrightness操作需要亮度参数");
                }
                try {
                    int brightness = Integer.parseInt(parameter.trim());
                    if (brightness < 0 || brightness > 100) {
                        throw new InvalidParametersException("灯泡亮度设置应在0-100之间的整数");
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidParametersException("无效的亮度参数格式");
                }
                break;

            case "setcolortemp":
                if (parameter == null || parameter.trim().isEmpty()) {
                    throw new InvalidParametersException("setcolortemp操作需要色温参数");
                }
                try {
                    int colorTemp = Integer.parseInt(parameter.trim());
                    if (colorTemp < 2300 || colorTemp > 7000) {
                        throw new InvalidParametersException("色温设置应在2300K至7000K之间的整数");
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidParametersException("无效的色温参数格式");
                }
                break;

            // 对于不需要参数的操作，确保参数为空或null
            case "poweron":
            case "poweroff":
            case "lock":
            case "unlock":
                if (parameter != null && !parameter.trim().isEmpty()) {
                    throw new InvalidParametersException(op + "操作不需要参数");
                }
                break;

            default:
                // 对于未知操作，不在这里验证参数，留给DeviceAction处理
                break;
        }
    }

    public AutomationScene getSceneById(int sceneId) {
        if (!household.containsAutoScene(sceneId)){
            throw new InvalidAutomationSceneException("场景不存在！");
        }
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
                room.getDevices().values().forEach(device -> {
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
            for (Device device : room.getDevices().values()) {
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
    public void runScene(int sceneId) {
        AutomationScene scene = getSceneById(sceneId);
        scene.manualTrig();
    }

}
