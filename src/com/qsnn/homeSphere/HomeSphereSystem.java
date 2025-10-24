package com.qsnn.homeSphere;

import com.qsnn.homeSphere.domain.deviceModule.services.EnergyReporting;
import com.qsnn.homeSphere.domain.deviceModule.Device;
import com.qsnn.homeSphere.domain.house.Household;
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

    /**
     * 用户登录
     *
     * @param loginName 用户名
     * @param loginPassword 用户密码
     */
    public void login(String loginName, String loginPassword) {
        logoff();
        for (User user : household.getUsers()) {
            if (user.getLoginName().equals(loginName) && user.getLoginPassword().equals(loginPassword)) {
                currentUser = user;
                System.out.println("登录成功：" + user.toString());
                return;
            }
        }
        System.out.println("登录失败：用户名或密码错误！");
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
     * @param loginName 用户名
     * @param loginPassword 用户密码
     * @param userName 用户真实姓名
     * @param email 用户邮箱
     * @return 注册成功的用户对象，如果用户名已存在则返回null
     */
    public User register(String loginName, String loginPassword, String userName, String email) {
        if (household.containsUser(loginName)) {
            return null;
        } else {
            User user = new User(household.getUsers().size() + 1, loginName, loginPassword, userName, email);
            household.addUser(user);
            return user;
        }
    }

    /**
     * 显示所有用户列表
     */
    public void displayUsers() {
        System.out.println("=== Users List ===");
        household.getUsers().forEach(System.out::println);
        System.out.println("Total: " + household.getUsers().size() + " users");
    }

    /**
     * 显示所有房间列表
     */
    public void displayRooms() {
        System.out.println("=== Rooms List ===");
        household.getRooms().forEach(System.out::println);
        System.out.println("Total: " + household.getRooms().size() + " rooms");
    }

    /**
     * 显示所有设备列表
     */
    public void displayDevices() {
        System.out.println("=== Devices List ===");
        List<Device> devices = household.listAllDevices();
        devices.forEach(System.out::println);
        System.out.println("Total: " + devices.size() + " devices");
    }

    /**
     * 显示所有自动化场景列表
     */
    public void displayAutoScenes() {
        System.out.println("=== Automation Scenes List ===");
        household.getAutoScenes().forEach(System.out::println);
        System.out.println("Total: " + household.getAutoScenes().size() + " scenes");
    }

    /**
     * 显示能源消耗报告
     *
     * @param startTime 报告开始时间
     * @param endTime 报告结束时间
     */
    public void displayEnergyReportings(Date startTime, Date endTime) {
        System.out.println("=== Energy Consumption Report ===");
        System.out.println("Time Period: " + startTime + " to " + endTime);

        List<Device> devices = household.listAllDevices();
        double totalEnergy = 0;
        int reportingDevices = 0;

        for (Device device : devices) {
            // 检查设备是否实现了EnergyReporting接口
            if (device instanceof EnergyReporting) {
                EnergyReporting energyDevice =
                        (EnergyReporting) device;

                double energyConsumption = energyDevice.getReport(startTime, endTime);
                double power = energyDevice.getPower();

                System.out.println("Device: " + device.getName() +
                        " - Power: " + power + "W" +
                        " - Energy: " + String.format("%.2f", energyConsumption) + "kWh");

                totalEnergy += energyConsumption;
                reportingDevices++;
            }
        }

        System.out.println("=== Summary ===");
        System.out.println("Total energy consumption: " + String.format("%.2f", totalEnergy) + " kWh");
        System.out.println("Devices with energy reporting: " + reportingDevices);
    }

    /**
     * 根据场景ID手动触发自动化场景
     *
     * @param sceneId 场景ID
     */
    public void manualTrigSceneById(int sceneId) {
        household.getAutoSceneById(sceneId).manualTrig();
    }
}
