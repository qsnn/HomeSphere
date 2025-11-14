package com.qsnn.homeSphere.utils.initialer;

import com.qsnn.homeSphere.HomeSphereSystem;
import com.qsnn.homeSphere.domain.automationScene.AutomationScene;
import com.qsnn.homeSphere.domain.automationScene.DeviceAction;
import com.qsnn.homeSphere.domain.deviceModule.devices.*;
import com.qsnn.homeSphere.domain.house.Household;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Initialer {

    private static HomeSphereSystem system;
    public static void initialLine(String line) {
        if (line == null || line.isEmpty()) {
            return;
        }
        if (line.startsWith("Household")) {
            initialHousehold(formatLine(line));
        } else if (system == null){
            throw new IllegalArgumentException("dat文件损坏：未初始化Household");
        } else if (line.startsWith("User")) {
            initialUser(formatLine(line));
        } else if (line.startsWith("Room")) {
            initialRoom(formatLine(line));
        } else if (line.startsWith("Manufacturer")) {
            initialManufacturer(formatLine(line));
        } else if (line.startsWith("AirConditioner")) {
            initialAirConditioner(formatLine(line));
        } else if (line.startsWith("BathroomScale")) {
            initialBathroomScale(formatLine(line));
        } else if (line.startsWith("LightBulb")) {
            initialLightBulb(formatLine(line));
        } else if (line.startsWith("SmartLock")) {
            initialSmartLock(formatLine(line));
        } else if (line.startsWith("AutomationScene")) {
            initialAutomationScene(formatLine(line));
        }

    }

    private static String formatLine(String line){
        return line.substring(line.indexOf("{") + 1, line.lastIndexOf("}")).trim();
    }

    private static void initialHousehold(String line) {
        int householdId = -1;
        String address = null;
        int adminId = -1;
        for (String s : line.split(",")) {
            String key = s.split("=")[0].trim();
            String value = s.split("=")[1].trim();
            if ("householdId".equals(key)) {
                householdId = Integer.parseInt(value);
            } else if ("address".equals(key)) {
                address = value.substring(1, value.length() - 1);
            } else if ("adminId".equals(key)) {
                adminId = Integer.parseInt(value);
            }
        }
        if (householdId == -1 || address == null || adminId == -1) {
            throw new IllegalArgumentException("dat文件损坏：Household信息不完整");
        }
        Household h = new Household(householdId, address, adminId);
        HomeSphereSystem.initialize(h);
        system = HomeSphereSystem.getInstance();
    }

    private static void initialUser(String line) {
        int userId = -1;
        String username = null;
        String password = null;
        String email = null;
        boolean isAdmin = false;
        for (String s : line.split(",")) {
            String key = s.split("=")[0].trim();
            String value = s.split("=")[1].trim();

            if ("userId".equals(key)) {
                userId = Integer.parseInt(value);
            } else if ("username".equals(key)) {
                username = value.substring(1, value.length() - 1);
            } else if ("password".equals(key)) {
                password = value.substring(1, value.length() - 1);
            } else if ("email".equals(key)) {
                email = value.substring(1, value.length() - 1);
            } else if ("isAdmin".equals(key)) {
                isAdmin = Boolean.parseBoolean(value);
            }
        }
        if (userId == -1 || username == null || email == null) {
            throw new IllegalArgumentException("dat文件损坏：User信息不完整");
        }
        system.register(userId, username, password, email, isAdmin);
    }

    private static void initialRoom(String line) {
        int roomId = -1;
        String name = null;
        double area = -1;
        for (String s : line.split(",")) {
            if (s.trim().isEmpty()) {
                continue; // 跳过空字符串
            }
            String key = s.split("=")[0].trim();
            String value = s.split("=")[1].trim();
            if ("roomId".equals(key)) {
                roomId = Integer.parseInt(value);
            } else if ("name".equals(key)) {
                name = value.substring(1, value.length() - 1);
            } else if ("area".equals(key)) {
                area = Double.parseDouble(value);
            }
        }
        if (roomId == -1 || name == null || area == -1) {
            throw new IllegalArgumentException("dat文件损坏：Room信息不完整");
        }
        system.createRoom(roomId, name, area);
    }

    private static void initialManufacturer(String line) {
        int manufacturerId = -1;
        String name = null;
        String protocols = null;
        for (String s : line.split(",")) {
            String key = s.split("=")[0].trim();
            String value = s.split("=")[1].trim();
            if ("manufacturerId".equals(key)) {
                manufacturerId = Integer.parseInt(value);
            } else if ("name".equals(key)) {
                name = value.substring(1, value.length() - 1);
            } else if ("protocols".equals(key)) {
                protocols = value.substring(1, value.length() - 1);
            }
        }
        if (manufacturerId == -1 || name == null || protocols == null) {
            throw new IllegalArgumentException("dat文件损坏：Manufacturer信息不完整");
        }
        system.addManufacturer(manufacturerId, name, protocols);
    }

    private static void initialAirConditioner(String line) {
        int deviceId = -1;
        String name = null;
        int manufacturerId = -1;
        int roomId = -1;
        double currTemp = 0.0;
        double targetTemp = 0.0;
        for (String s : line.split(",")){
            String key = s.split("=")[0].trim();
            String value = s.split("=")[1].trim();
            if ("deviceId".equals(key)) {
                deviceId = Integer.parseInt(value);
            } else if ("name".equals(key)) {
                name = value.substring(1, value.length() - 1);
            } else if ("manufacturerId".equals(key)) {
                manufacturerId = Integer.parseInt(value);
            } else if ("roomId".equals(key)) {
                roomId = Integer.parseInt(value);
            } else if ("currTemp".equals(key)) {
                currTemp = Double.parseDouble(value);
            } else if ("targetTemp".equals(key)) {
                targetTemp = Double.parseDouble(value);
            }
        }
        if (deviceId == -1 || name == null || manufacturerId == -1 || roomId == -1) {
            throw new IllegalArgumentException("dat文件损坏：AirConditioner信息不完整");
        }
        system.createDevice (deviceId, name, DeviceType.AIR_CONDITIONER, manufacturerId, roomId);
        AirConditioner ac = (AirConditioner) system.getDeviceById(deviceId);
        ac.setCurrTemp(currTemp);
        ac.setTargetTemp(targetTemp);
    }

    private static void initialBathroomScale(String line) {
        int deviceId = -1;
        String name = null;
        int manufacturerId = -1;
        int roomId = -1;
        double bodyMass = 0.0;
        int batteryLevel = 0;
        for (String s : line.split(",")){
            String key = s.split("=")[0].trim();
            String value = s.split("=")[1].trim();
            if ("deviceId".equals(key)) {
                deviceId = Integer.parseInt(value);
            } else if ("name".equals(key)) {
                name = value.substring(1, value.length() - 1);
            } else if ("roomId".equals(key)) {
                roomId = Integer.parseInt(value);
            } else if ("manufacturerId".equals(key)) {
                manufacturerId = Integer.parseInt(value);
            } else if ("bodyMass".equals(key)) {
                bodyMass = Double.parseDouble(value);
            } else if ("batteryLevel".equals(key)) {
                batteryLevel = Integer.parseInt(value);
            }
        }
        if (deviceId == -1 || name == null || manufacturerId == -1 || roomId == -1) {
            throw new IllegalArgumentException("dat文件损坏：BathroomScale信息不完整");
        }
        system.createDevice (deviceId, name, DeviceType.BATHROOM_SCALE, manufacturerId, roomId);
        BathroomScale bs = (BathroomScale) system.getDeviceById(deviceId);
        bs.setBodyMass(bodyMass);
        bs.setBatteryLevel(batteryLevel);
    }

    private static void initialLightBulb(String line) {
        int deviceId = -1;
        String name = null;
        int manufacturerId = -1;
        int roomId = -1;
        int brightness = 0;
        int colorTemp = 0;
        for (String s : line.split(",")){
            String key = s.split("=")[0].trim();
            String value = s.split("=")[1].trim();
            if ("deviceId".equals(key)) {
                deviceId = Integer.parseInt(value);
            } else if ("name".equals(key)) {
                name = value.substring(1, value.length() - 1);
            } else if ("manufacturerId".equals(key)) {
                manufacturerId = Integer.parseInt(value);
            } else if ("roomId".equals(key)) {
                roomId = Integer.parseInt(value);
            } else if ("brightness".equals(key)) {
                brightness = Integer.parseInt(value);
            } else if ("colorTemp".equals(key)) {
                colorTemp = Integer.parseInt(value);
            }
        }
        if (deviceId == -1 || name == null || manufacturerId == -1 || roomId == -1) {
            throw new IllegalArgumentException("dat文件损坏：LightBulb信息不完整");
        }
        system.createDevice (deviceId, name, DeviceType.LIGHT_BULB, manufacturerId, roomId);
        LightBulb lb = (LightBulb) system.getDeviceById(deviceId);
        lb.setBrightness(brightness );
        lb.setColorTemp(colorTemp);
    }

    private static void initialSmartLock(String line) {
        int deviceId = -1;
        String name = null;
        int manufacturerId = -1;
        int roomId = -1;
        boolean isLocked = false;
        int batteryLevel = 0;
        for (String s : line.split(",")) {
            String key = s.split("=")[0].trim();
            String value = s.split("=")[1].trim();
            if ("deviceId".equals(key)) {
                deviceId = Integer.parseInt(value);
            } else if ("name".equals(key)) {
                name = value.substring(1, value.length() - 1);
            } else if ("manufacturerId".equals(key)) {
                manufacturerId = Integer.parseInt(value);
            } else if ("roomId".equals(key)) {
                roomId = Integer.parseInt(value);
            } else if ("isLocked".equals(key)) {
                isLocked = Boolean.parseBoolean(value);
            } else if ("batteryLevel".equals(key)) {
                batteryLevel = Integer.parseInt(value);
            }
        }
        if (deviceId == -1 || name == null || manufacturerId == -1 || roomId == -1) {
            throw new IllegalArgumentException("dat文件损坏：LightBulb信息不完整");
        }
        system.createDevice (deviceId, name, DeviceType.SMART_LOCK, manufacturerId, roomId);
        SmartLock sl = (SmartLock) system.getDeviceById(deviceId);
        sl.setLocked(isLocked);
        sl.setBatteryLevel(batteryLevel);
    }

    private static void initialAutomationScene(String line) {
        int sceneId = -1;
        String name = null;
        String description = null;
        List<DeviceAction> actions;

        //先把actions处理掉，因为里面的，和=会影响后续的分割
        String actionsStr = line.substring(line.indexOf("actions=[") + 8, line.lastIndexOf("]"));
        line.replace("actions=[" + actionsStr + "]", "");

        for (String s : line.split(",")) {
            String key = s.split("=")[0].trim();
            String value = s.split("=")[1].trim();

            if ("sceneId".equals(key) || "sceneld".equals(key)) {
                sceneId = Integer.parseInt(value);
            } else if ("name".equals(key)) {
                name = value.substring(1, value.length() - 1); // 去除单引号
            } else if ("description".equals(key)) {
                description = value.substring(1, value.length() - 1); // 去除单引号
            }
        }

        actions = parseDeviceActions(actionsStr);
        if (sceneId == -1 || name == null) {
            throw new IllegalArgumentException("dat文件损坏：AutomationScene信息不完整");
        }
        system.createScene(sceneId, name, description);
        AutomationScene scene = system.getSceneById(sceneId);
        for (DeviceAction action : actions) {
            scene.addAction(action);
        }
    }

    private static List<DeviceAction> parseDeviceActions(String line) {
        List<DeviceAction> actions = new ArrayList<>();
        Pattern pattern = Pattern.compile("DeviceAction\\{[^{}]*\\}");
        Matcher matcher = pattern.matcher(line);

        while(matcher.find()){
            String actionStr = matcher.group();
            DeviceAction action = parseDeviceAction(actionStr.substring(actionStr.indexOf("{") + 1, actionStr.lastIndexOf("}")));
            if (action != null) {
                actions.add(action);
            }
        }
        return actions;
    }

    private static DeviceAction parseDeviceAction(String line) {
        String command = null;
        String parameters = null;
        int deviceId = 0;

        for (String s : line.split(",")) {
            String key = s.split("=")[0].trim();
            String value = s.split("=")[1].trim();

            if ("command".equals(key)) {
                command = value.substring(1, value.length() - 1); // 去除单引号
            } else if ("parameters".equals(key)) {
                parameters = value.substring(1, value.length() - 1); // 去除单引号
            } else if ("deviceId".equals(key)) {
                deviceId = Integer.parseInt(value);
            }
        }

        if (command != null && deviceId != 0) {
            return new DeviceAction(command, parameters, system.getDeviceById(deviceId));
        }
        return null;
    }


}
