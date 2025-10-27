package com.qsnn.homeSphere.ui;

import com.qsnn.homeSphere.HomeSphereSystem;
import com.qsnn.homeSphere.domain.deviceModule.devices.AirConditioner;
import com.qsnn.homeSphere.domain.deviceModule.services.Manufacturer;
import com.qsnn.homeSphere.domain.house.Household;
import com.qsnn.homeSphere.domain.house.Room;

import java.util.*;

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

        system.getHousehold().addRoom(new Room(1, "客厅", 30.0));
        system.getHousehold().addRoom(new Room(2, "卧室", 20.0));
        system.getHousehold().addRoom(new Room(3, "厨房", 8.62));

        system.getHousehold().addDevice(new AirConditioner(1, "客厅空调", manufacturers.get(0)) , 1);

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
        if(system.login(loginName, password)) {
            return MENU_PAGE; // 登录成功后进入菜单页面
        }else {
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

        system.register(loginName, password, email, false);
        return MEMBER_PAGE;
    }

    private PageType removeMember() {
        System.out.print("请输入要删除的用户ID：");
        int userId = sc.nextInt();
        system.removeUser(userId);
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
        int deviceType = sc.nextInt();
        sc.nextLine();

        if(deviceType < 1 || deviceType > 4){
            System.out.println("无效的设备类型选择！");
        }else {
            System.out.print("请输入要添加到的房间ID：");
            int roomId = sc.nextInt();
            sc.nextLine();
            system.createDevice(deviceName, deviceType, manufacturers.get(deviceType - 1), roomId);
        }
        return DEVICE_PAGE;
    }

    private PageType removeDevice() {
        System.out.print("请输入要删除的设备ID：");
        int deviceId = sc.nextInt();
        system.removeDevice(deviceId);
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
                int deviceId;
                try {
                    deviceId = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("无效的设备ID！");
                    continue;
                }

                // 获取设备类型以提供相应的操作提示
                String deviceType = system.getDeviceType(deviceId);
                if (deviceType == null) {
                    System.out.println("设备不存在！");
                    continue;
                }

                // 根据设备类型提供不同的操作提示
                String operationHint = getOperationHint(deviceType);
                System.out.print("输入操作命令" + operationHint + "：");
                String operation = sc.nextLine().trim();

                // 处理需要额外参数的命令
                String parameter = "";
                if (operation.equals("setbrightness") || operation.equals("setcolortemp") ||
                        operation.equals("settemperature") ) {

                    System.out.print("输入" + getParameterName(operation) + "：");
                    parameter = sc.nextLine().trim();

                }

                // 添加场景操作
                system.addSceneOperation(sceneId, deviceId, operation, parameter);

            } else if (choice.equals("n")) {
                addingOperations = false;
            } else {
                System.out.println("请输入 y 或 n");
            }
        }

        System.out.println("场景创建成功！");
        return SCENE_PAGE;
    }

    private PageType runScene() {
        System.out.print("输入要触发的场景ID：");
        int sceneId;
        try {
            sceneId = Integer.parseInt(sc.nextLine());
            system.manualTrigSceneById(sceneId);
            System.out.println("场景已触发！");
        } catch (NumberFormatException e) {
            System.out.println("无效的场景ID！");
        }
        return SCENE_PAGE;
    }

    private PageType listScenes() {
        System.out.println(system.listScenes());
        return SCENE_PAGE;
    }

    private String getOperationHint(String deviceType) {
        return switch (deviceType.toLowerCase()) {
            case "lightbulb" -> "（例如poweron/poweroff/setbrightness/setcolortemp）";
            case "airconditioner" -> "（例如poweron/settemperature）";
            case "smartlock" -> "（例如lock/unlock）";
            case "bathroomscale" -> "（例如例如poweron/poweroff）";
            default -> "（请参考设备支持的命令）";
        };
    }


    private String getParameterName(String operation) {
        return switch (operation.toLowerCase()) {
            case "setbrightness" -> "亮度值(0-100)";
            case "setcolortemp" -> "色温值(K)";
            case "settemperature" -> "目标温度(°C)";
            default -> "参数值";
        };
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

        // 使用正则表达式验证格式
        String dateRegex = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
        if (!startDateStr.matches(dateRegex)) {
            System.out.println("日期格式错误，请按照 yyyy-MM-dd 格式输入");
            return LOG_PAGE;
        }

        Date startDate = new Date(
                Integer.parseInt(startDateStr.split("-")[0]),
                Integer.parseInt(startDateStr.split("-")[1]),
                Integer.parseInt(startDateStr.split("-")[2])
             );


        System.out.print("请输入起始时间（格式：yyyy-MM-dd）：");
        String endDateStr = sc.nextLine();

        if (!endDateStr.matches(dateRegex)) {
            System.out.println("日期格式错误，请按照 yyyy-MM-dd 格式输入");
            return LOG_PAGE;
        }

        Date endDate = new Date(
                Integer.parseInt(endDateStr.split("-")[0]),
                Integer.parseInt(endDateStr.split("-")[1]),
                Integer.parseInt(endDateStr.split("-")[2])
        );

        System.out.println(system.generateEnergyReport(startDate, endDate));


        return LOG_PAGE;
    }

}
