package com.qsnn.homeSphere.ui;

import com.qsnn.homeSphere.HomeSphereSystem;
import com.qsnn.homeSphere.domain.deviceModule.devices.DeviceType;
import com.qsnn.homeSphere.domain.deviceModule.services.Manufacturer;
import com.qsnn.homeSphere.domain.house.Household;
import com.qsnn.homeSphere.exceptions.*;
import com.qsnn.homeSphere.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.qsnn.homeSphere.ui.PageType.*;

/**
 * 命令行用户界面类，提供智能家居系统的文本交互界面
 * 负责处理用户输入、显示系统信息和管理各种功能页面
 *
 * @author qsnn
 * @version 2.0
 * @since 2025
 */
public class CommandUI {
    /** 智能家居系统核心实例 */
    private HomeSphereSystem system;

    /** 输入扫描器，用于读取用户输入 */
    private final Scanner sc = new Scanner(System.in);
    /** 制造商列表，存储系统支持的设备制造商 */
    private final List<Manufacturer> manufacturers = new ArrayList<>();

    /**
     * 构造函数，初始化系统并启动UI控制器
     */
    public CommandUI() {
        setup();
        UIController();
    }

    /**
     * 系统初始化方法，设置默认数据
     * 包括创建用户、制造商、房间、设备和自动化场景
     */
    private void setup(){
        system = new HomeSphereSystem(new Household(1, "陕西省-西安市-长安区-东大街道-东祥路1号-西北工业大学"));

        system.register("admin", "admin", "admin@nwpu.edu.cn", true);

        manufacturers.add(new Manufacturer(1, "海尔", "WIFI, BLUETOOTH"));
        manufacturers.add(new Manufacturer(2, "小米", "Zigbee"));
        manufacturers.add(new Manufacturer(3, "Locker", "WIFI"));
        manufacturers.add(new Manufacturer(4, "华为", "WIFI, Zigbee"));

        system.createRoom("客厅", 30.0);
        system.createRoom("卧室", 20.0);
        system.createRoom("厨房", 8.62);

        system.createDevice("客厅空调", DeviceType.AIR_CONDITIONER, manufacturers.get(0) , 1);

        // 创建更多设备
        system.createDevice("卧室灯", DeviceType.LIGHT_BULB, manufacturers.get(1), 2);
        system.createDevice("大门锁", DeviceType.SMART_LOCK, manufacturers.get(2), 1);
        system.createDevice("厨房灯", DeviceType.LIGHT_BULB, manufacturers.get(3), 3);
        system.createDevice("卧室空调", DeviceType.AIR_CONDITIONER, manufacturers.get(0), 2);

        // 创建自动化场景
        int sceneId = system.createScene("回家模式", "晚上回家时自动开启灯光和空调");

        // 为场景添加操作
        try {
            // 开启客厅空调到24度
            system.addSceneOperation(sceneId, 1, "poweron", "");
            system.addSceneOperation(sceneId, 1, "settemperature", "24");

            // 开启卧室灯并设置亮度
            system.addSceneOperation(sceneId, 2, "poweron", "");
            system.addSceneOperation(sceneId, 2, "setbrightness", "80");

            // 解锁大门
            system.addSceneOperation(sceneId, 3, "unlock", "");

            // 开启厨房灯
            system.addSceneOperation(sceneId, 4, "poweron", "");

        } catch (Exception e) {
            System.out.println("创建场景操作时出错: " + e.getMessage());
        }

        // 创建第二个场景
        int sceneId2 = system.createScene("睡眠模式", "准备睡觉时关闭不必要的设备");

        try {
            // 关闭客厅空调
            system.addSceneOperation(sceneId2, 1, "poweroff", "");

            // 调暗卧室灯
            system.addSceneOperation(sceneId2, 2, "setbrightness", "30");

            // 锁门
            system.addSceneOperation(sceneId2, 3, "lock", "");

            // 关闭厨房灯
            system.addSceneOperation(sceneId2, 4, "poweroff", "");

            // 开启卧室空调到26度
            system.addSceneOperation(sceneId2, 5, "settemperature", "26");

        } catch (Exception e) {
            System.out.println("创建场景操作时出错: " + e.getMessage());
        }
    }

    /**
     * 管理用户选择的核心方法，显示当前页面的选项并处理用户输入
     *
     * @param currentPage 当前页面类型
     * @return 用户选择后应该跳转到的页面类型
     */
    private PageType manageChoices(PageType currentPage) {
        //打印信息
        System.out.println(currentPage.getTitle());
        currentPage.getOptions().forEach(System.out::println);

        Map<Integer, PageType> map = currentPage.getChoiceMap();
        System.out.print("请选择操作：");
        String choiceS = sc.nextLine();
        int choice;
        try{
            choice = Integer.parseInt(choiceS.trim());
            if(!map.containsKey(choice)) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("无效选择，请重新输入");
            return currentPage; // 重新显示页面
        }
        return map.get(choice);
    }

    /**
     * UI控制器主循环，根据当前页面类型调用相应的处理方法
     * 使用状态机模式管理页面导航
     */
    private void UIController() {
        PageType page = START_PAGE;
        // 控制UI显示和交互的逻辑
        while(true){
            System.out.println();

            // 起始页面和登录
            if (page == START_PAGE) {
                page = startPage();
            } else if (page == LOGIN) {
                page = login();
            }

            // 菜单
            else if (page == MENU_PAGE) {
                page = menuPage();
            }

            // 用户相关页面
            else if (page == MEMBER_PAGE) {
                page = memberPage();
            } else if (page == ADD_MEMBER) {
                page = addMember();
            } else if (page == REMOVE_MEMBER) {
                page = removeMember();
            } else if (page == LIST_MEMBERS) {
                page = listMembers();
            }

            // 设备相关页面
            else if (page == DEVICE_PAGE) {
                page = devicePage();
            } else if (page == ADD_DEVICE) {
                page = addDevice();
            } else if (page == REMOVE_DEVICE) {
                page = removeDevice();
            } else if (page == LIST_DEVICES) {
                page = listDevices();
            }

            // 场景相关页面
            else if (page == SCENE_PAGE) {
                page = scenePage();
            } else if (page == ADD_SCENE) {
                page = addScene();
            } else if (page == RUN_SCENE) {
                page = runScene();
            } else if (page == LIST_SCENES) {
                page = listScenes();
            }

            // 日志相关页面
            else if (page == LOG_PAGE) {
                page = logPage();
            } else if (page == DEVICE_LOGS) {
                page = deviceLogs();
            } else if (page == ENERGY_REPORT) {
                page = energyReport();
            }

            else if (page == EXIT_SYSTEM) {
                return;
            }
        }

    }

    //登录界面
    /**
     * 处理起始页面显示和用户选择
     *
     * @return 用户选择后应该跳转到的页面类型
     */
    private PageType startPage() {
        return manageChoices(START_PAGE);
    }

    /**
     * 处理用户登录流程
     *
     * @return 登录成功返回菜单页面，失败返回起始页面
     */
    private PageType login() {
        System.out.print("请输入用户名：");
        String loginName = sc.nextLine();
        System.out.print("请输入密码：");
        String password = sc.nextLine();
        try {
            system.login(loginName, password);
            System.out.println("User logged in");
            return MENU_PAGE; // 登录成功后进入菜单页面
        }catch (InvalidUserException e){
            System.out.println(e.getMessage());
            return START_PAGE; // 登录失败后返回起始页面
        }
    }


    //菜单
    /**
     * 处理主菜单页面显示和用户选择
     *
     * @return 用户选择后应该跳转到的页面类型
     */
    private PageType menuPage() {
        return manageChoices(MENU_PAGE);
    }


    //用户相关页面
    /**
     * 处理成员管理页面显示和用户选择
     *
     * @return 用户选择后应该跳转到的页面类型
     */
    private PageType memberPage() {
        return manageChoices(MEMBER_PAGE);
    }

    /**
     * 处理添加新成员流程
     *
     * @return 完成后返回成员管理页面
     */
    private PageType addMember() {
        System.out.print("请输入用户名：");
        String loginName = sc.nextLine();
        System.out.print("请输入密码：");
        String password = sc.nextLine();
        System.out.print("请输入邮箱：");
        String email = sc.nextLine();

        try {
            system.register(loginName, password, email, false);
            System.out.println("用户添加成功！");
        } catch (InvalidUserException e) {
            System.out.println(e.getMessage());
        }
        return MEMBER_PAGE;
    }

    /**
     * 处理删除成员流程
     *
     * @return 完成后返回成员管理页面
     */
    private PageType removeMember() {
        System.out.print("请输入要删除的用户ID：");
        String userIdS = sc.nextLine();

        try{
            int userId = Integer.parseInt(userIdS);
            system.removeUser(userId);
            System.out.println("用户删除成功！");
        } catch (NumberFormatException e) {
            System.out.println("ID格式错误！");
        }  catch (InvalidUserException e) {
            System.out.println(e.getMessage());
        }
        return MEMBER_PAGE;
    }

    /**
     * 显示所有成员列表
     *
     * @return 完成后返回成员管理页面
     */
    private PageType listMembers() {
        System.out.println(system.listUsers());
        return MEMBER_PAGE;
    }

    //设备相关页面
    /**
     * 处理设备管理页面显示和用户选择
     *
     * @return 用户选择后应该跳转到的页面类型
     */
    private PageType devicePage() {
        return manageChoices(DEVICE_PAGE);
    }

    /**
     * 处理添加新设备流程
     *
     * @return 完成后返回设备管理页面
     */
    private PageType addDevice() {
        System.out.print("请输入设备名称：");
        String deviceName = sc.nextLine();

        System.out.print("选择设备类型：（1.空调 2.智能灯泡 3.智能锁 4.智能体重秤）");
        String deviceTypeS = sc.nextLine();

        System.out.print("请输入要添加到的房间ID：");
        String roomIdS = sc.nextLine();

        try {
            int roomId = Integer.parseInt(roomIdS);
            int deviceType = Integer.parseInt(deviceTypeS);
            system.createDevice(deviceName, DeviceType.getType(deviceType), manufacturers.get(deviceType - 1), roomId);
            System.out.println("设备添加成功！");
        } catch (InvalidDeviceException | InvalidRoomException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("房间ID格式错误！");
        }
        return DEVICE_PAGE;
    }

    /**
     * 处理删除设备流程
     *
     * @return 完成后返回设备管理页面
     */
    private PageType removeDevice() {
        System.out.print("请输入要删除的设备ID：");
        String deviceIdS = sc.nextLine();

        try{
            int deviceId = Integer.parseInt(deviceIdS);
            system.removeDevice(deviceId);
            System.out.println("设备删除成功！");
        } catch (NumberFormatException e) {
            System.out.println("ID格式错误！");
        } catch (InvalidDeviceException e){
            System.out.println(e.getMessage());
        }
        return DEVICE_PAGE;
    }

    /**
     * 显示所有设备列表
     *
     * @return 完成后返回设备管理页面
     */
    private PageType listDevices() {
        System.out.println(system.listDevices());
        return DEVICE_PAGE;
    }


    //场景相关页面
    /**
     * 处理场景管理页面显示和用户选择
     *
     * @return 用户选择后应该跳转到的页面类型
     */
    private PageType scenePage() {
        return manageChoices(SCENE_PAGE);
    }

    /**
     * 处理添加新场景流程，包括场景操作的定义
     *
     * @return 完成后返回场景管理页面
     */
    private PageType addScene() {
        System.out.print("输入场景名称：");
        String sceneName = sc.nextLine();

        System.out.print("输入场景描述：");
        String sceneDescription = sc.nextLine();

        // 创建场景
        int sceneId = system.createScene(sceneName, sceneDescription);

        boolean addingOperations = true;
        while (addingOperations) {
            System.out.println(system.getSceneById(sceneId).listActions());

            System.out.print("添加设备操作？(y/n) ");
            String choice = sc.nextLine().trim().toLowerCase();

            if (choice.equals("y")) {
                System.out.print("输入设备ID：");
                String deviceIdS = sc.nextLine();

                try {
                    int deviceId = Integer.parseInt(deviceIdS);

                    DeviceType deviceType = system.getDeviceById(deviceId).getDeviceType();

                    System.out.print("输入操作命令（例如：" + deviceType.getSupportedOperations() + "）：");
                    String operation = sc.nextLine().trim();

                    String parameterName = deviceType.getParameterName(operation);

                    String parameter = "";
                    if (parameterName != null) {
                        System.out.print("输入" + parameterName + "：");
                        parameter = sc.nextLine().trim();
                    }

                    // 添加场景操作
                    system.addSceneOperation(sceneId, deviceId, operation, parameter);


                } catch (NumberFormatException e) {
                    System.out.println("ID格式错误！");
                } catch (InvalidDeviceException | InvalidParametersException | InvalidAutomationSceneException e) {
                    System.out.println(e.getMessage());
                }
            } else if (choice.equals("n")) {
                addingOperations = false;
            } else {
                System.out.println("请正确输入 y 或 n！");
            }
        }

        System.out.println("场景创建成功！");
        return SCENE_PAGE;
    }

    /**
     * 处理触发场景执行流程
     *
     * @return 完成后返回场景管理页面
     */
    private PageType runScene() {
        System.out.print("输入要触发的场景ID：");
        String sceneIdS = sc.nextLine();

        try {
            int sceneId = Integer.parseInt(sceneIdS);
            system.runScene(sceneId);
            System.out.println("场景已触发！");
            System.out.println("Scene with ID " + sceneId + " triggered.");
        } catch (NumberFormatException e) {
            System.out.println("场景ID格式错误！");
        } catch (InvalidAutomationSceneException | InvalidDeviceException | InvalidParametersException e) {
            System.out.println(e.getMessage());
        }
        return SCENE_PAGE;
    }

    /**
     * 显示所有场景列表
     *
     * @return 完成后返回场景管理页面
     */
    private PageType listScenes() {
        System.out.println(system.listScenes());
        return SCENE_PAGE;
    }


    //日志相关页面
    /**
     * 处理日志管理页面显示和用户选择
     *
     * @return 用户选择后应该跳转到的页面类型
     */
    private PageType logPage() {
        return manageChoices(LOG_PAGE);
    }

    /**
     * 显示设备运行日志
     *
     * @return 完成后返回日志管理页面
     */
    private PageType deviceLogs() {
        System.out.println(system.listRunningLogs());
        return LOG_PAGE;
    }

    /**
     * 生成并显示能源消耗报告
     *
     * @return 完成后返回日志管理页面
     */
    private PageType energyReport() {
        System.out.print("请输入起始时间（格式：yyyy-MM-dd）：");
        String startDateStr = sc.nextLine();

        System.out.print("请输入截止时间（格式：yyyy-MM-dd）：");
        String endDateStr = sc.nextLine();


        try {
            System.out.println(system.generateEnergyReport(
                    DateUtil.formatStringToDate(startDateStr) ,
                    DateUtil.formatStringToDate(endDateStr))
            );
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }


        return LOG_PAGE;
    }

}