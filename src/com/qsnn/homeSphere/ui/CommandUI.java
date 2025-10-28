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

public class CommandUI {
    private HomeSphereSystem system;

    private final Scanner sc = new Scanner(System.in);
    private final List<Manufacturer> manufacturers = new ArrayList<>();

    public CommandUI() {
        setup();
        UIController();
    }

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

    }

    private PageType manageChoices(PageType currentPage) {
        //打印信息
        System.out.println(currentPage.getTitle());
        currentPage.getOptions().forEach(System.out::println);

        Map<Integer, PageType> map = currentPage.getChoiceMap();
        System.out.print("请选择操作：");
        String choiceS = sc.nextLine();
        int choice;
        try{
            choice = Integer.parseInt(choiceS);
            if(!map.containsKey(choice)) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("无效选择，请重新输入");
            return currentPage; // 重新显示页面
        }
        return map.get(choice);
    }

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
    private PageType startPage() {
        return manageChoices(START_PAGE);
    }

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
    private PageType menuPage() {
        return manageChoices(MENU_PAGE);
    }

    
    //用户相关页面
    private PageType memberPage() {
        return manageChoices(MEMBER_PAGE);
    }

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

    private PageType removeMember() {
        System.out.print("请输入要删除的用户ID：");
        int userId = sc.nextInt();
        sc.nextLine();
        try {
            system.removeUser(userId);
            System.out.println("用户删除成功！");
        } catch (InvalidUserException e) {
            System.out.println(e.getMessage());
        }
        return MEMBER_PAGE;
    }

    private PageType listMembers() {
        System.out.println(system.listUsers());
        return MEMBER_PAGE;
    }

    //设备相关页面
    private PageType devicePage() {
        return manageChoices(DEVICE_PAGE);
    }

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

    private PageType listDevices() {
        System.out.println(system.listDevices());
        return DEVICE_PAGE;
    }


    //场景相关页面
    private PageType scenePage() {
        return manageChoices(SCENE_PAGE);
    }

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

                    // 获取设备类型以提供相应的操作提示
                    DeviceType deviceType = system.getDeviceById(deviceId).getDeviceType();

                    // 根据设备类型提供不同的操作提示
                    System.out.print("输入操作命令：");
                    String operation = sc.nextLine().trim();

                    // 使用DeviceType枚举的方法获取参数名
                    String parameterName = deviceType.getParameterName(operation);

                    // 处理需要额外参数的命令
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

    private PageType runScene() {
        System.out.print("输入要触发的场景ID：");
        String sceneIdS = sc.nextLine();

        try {
            int sceneId = Integer.parseInt(sceneIdS);
            system.manualTrigSceneById(sceneId);
            System.out.println("场景已触发！");
        } catch (NumberFormatException e) {
            System.out.println("ID格式错误！");
        } catch (InvalidAutomationSceneException e) {
            System.out.println(e.getMessage());
        }
        return SCENE_PAGE;
    }
    private PageType listScenes() {
        System.out.println(system.listScenes());
        return SCENE_PAGE;
    }


    //日志相关页面
    private PageType logPage() {
        return manageChoices(LOG_PAGE);
    }

    private PageType deviceLogs() {
        System.out.println(system.listRunningLogs());
        return LOG_PAGE;
    }

    private PageType energyReport() {
        System.out.print("请输入起始时间（格式：yyyy-MM-dd）：");
        String startDateStr = sc.nextLine();

        System.out.print("请输入起始时间（格式：yyyy-MM-dd）：");
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
