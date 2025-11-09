package com.qsnn.homeSphere.utils.parser;

import com.qsnn.homeSphere.domain.automationScene.AutomationScene;
import com.qsnn.homeSphere.domain.automationScene.DeviceAction;
import com.qsnn.homeSphere.domain.deviceModule.devices.AirConditioner;
import com.qsnn.homeSphere.domain.deviceModule.devices.BathroomScale;
import com.qsnn.homeSphere.domain.deviceModule.devices.LightBulb;
import com.qsnn.homeSphere.domain.deviceModule.devices.SmartLock;
import com.qsnn.homeSphere.domain.deviceModule.services.Manufacturer;
import com.qsnn.homeSphere.domain.house.Household;
import com.qsnn.homeSphere.domain.house.Room;
import com.qsnn.homeSphere.domain.users.User;

public class Parser {
    public static Object parseLine(String line) {
        if (line == null){
            return null;
        }
        if (line.startsWith("User")){
            return parseUser(formatLine(line));
        } else if (line.startsWith("Household")) {
            return parseHousehold(formatLine(line));
        } else if (line.startsWith("Room")) {
            return parseRoom(formatLine(line));
        } else if (line.startsWith("Manufacturer")) {
            return parseManufacturer(formatLine(line));
        } else if (line.startsWith("AirConditioner")) {
            return parseAirConditioner(formatLine(line));
        } else if (line.startsWith("BathroomScale")) {
            return parseBathroomScale(formatLine(line));
        } else if (line.startsWith("LightBulb")) {
            return parseLightBulb(formatLine(line));
        } else if (line.startsWith("SmartLock")) {
            return parseSmartLock(formatLine(line));
        } else if (line.startsWith("AutomationScene")) {
            return parseAutomationScene(formatLine(line));
        } else {
            return null;
        }
    }

    private static String formatLine(String line){
        return line.substring(line.indexOf("{") + 1, line.lastIndexOf("}"));
    }

    private static User parseUser(String line) {
        int userId = 0;
        String username = null;
        String password = null;
        String email = null;
        boolean isAdmin = false;
        for (String s : line.split(",")) {
            String key = s.split("=")[0];
            String value = s.split("=")[1];

            if ("userId".equals(key)) {
                userId = Integer.parseInt(value);
            } else if ("username".equals(key)) {
                username = value;
            } else if ("password".equals(key)) {
                password = value;
            } else if ("email".equals(key)) {
                email = value;
            } else if ("isAdmin".equals(key)) {
                isAdmin = Boolean.parseBoolean(value);
            }
        }
        return new User(userId, username, password, email, isAdmin);
    }

    private static Household parseHousehold(String line) {
        int householdId = 0;
        String address = null;
        int adminId = 0;
        for (String s : line.split(",")) {
            String key = s.split("=")[0];
            String value = s.split("=")[1];
            if ("householdId".equals(key)) {
                householdId = Integer.parseInt(value);
            } else if ("address".equals(key)) {
                address = value;
            } else if ("adminId".equals(key)) {
                adminId = Integer.parseInt(value);
            }
        }
        return new Household(householdId, address, adminId);
    }

    private static Room parseRoom(String line) {
        int roomId = 0;
        String name = null;
        double area = 0.0;
        for (String s : line.split(",")) {
            String key = s.split("=")[0];
            String value = s.split("=")[1];
            if ("roomId".equals(key)) {
                roomId = Integer.parseInt(value);
            } else if ("name".equals(key)) {
                name = value;
            } else if ("area".equals(key)) {
                area = Double.parseDouble(value);
            }
        }
        return new Room(roomId, name, area);
    }

    private static Manufacturer parseManufacturer(String line) {
        int manufacturerId = 0;
        String name = null;
        String protocols = null;
        for (String s : line.split(",")) {
            String key = s.split("=")[0];
            String value = s.split("=")[1];
            if ("manufacturerId".equals(key)) {
                manufacturerId = Integer.parseInt(value);
            } else if ("name".equals(key)) {
                name = value;
            } else if ("protocols".equals(key)) {
                protocols = value;
            }
        }
        return new Manufacturer(manufacturerId, name, protocols);
    }

    private static AirConditioner parseAirConditioner(String line) {
        int deviceId = 0;
        String name = null;
        int manufacturerId = 0;
        double currTemp = 0.0;
        double targetTemp = 0.0;
        for (String s : line.split(",")){
            String key = s.split("=")[0];
            String value = s.split("=")[1];
            if ("deviceId".equals(key)) {
                deviceId = Integer.parseInt(value);
            } else if ("name".equals(key)) {
                name = value;
            } else if ("manufacturerId".equals(key)) {
                manufacturerId = Integer.parseInt(value);
            } else if ("currTemp".equals(key)) {
                currTemp = Double.parseDouble(value);
            } else if ("targetTemp".equals(key)) {
                targetTemp = Double.parseDouble(value);
            }
        }
        AirConditioner ac = new AirConditioner(deviceId, name, manufacturerId);
        ac.setCurrTemp(currTemp);
        ac.setTargetTemp(targetTemp);
        return ac;
    }

    private static BathroomScale parseBathroomScale(String line) {
        int deviceId = 0;
        String name = null;
        int manufacturerId = 0;
        return null;
    }

    private static LightBulb parseLightBulb(String line) {
        int deviceId = 0;
        String name = null;
        int manufacturerId = 0;
        int brightness = 0;
        int colorTemp = 0;
        for (String s : line.split(",")){
            String key = s.split("=")[0];
            String value = s.split("=")[1];
            if ("deviceId".equals(key)) {
                deviceId = Integer.parseInt(value);
            } else if ("name".equals(key)) {
                name = value;
            } else if ("manufacturerId".equals(key)) {
                manufacturerId = Integer.parseInt(value);
            } else if ("brightness".equals(key)) {
                brightness = Integer.parseInt(value);
            } else if ("colorTemp".equals(key)) {
                colorTemp = Integer.parseInt(value);
            }
        }
        LightBulb lb = new LightBulb(deviceId, name, manufacturerId);
        lb.setBrightness(brightness );
        lb.setColorTemp(colorTemp);
        return lb;
    }

    private static SmartLock parseSmartLock(String line) {
        int deviceId = 0;
        String name = null;
        int manufacturerId = 0;
        boolean isLocked = false;
        int batteryLevel = 0;
        for (String s : line.split(",")) {
            String key = s.split("=")[0];
            String value = s.split("=")[1];
            if ("deviceId".equals(key)) {
                deviceId = Integer.parseInt(value);
            } else if ("name".equals(key)) {
                name = value;
            } else if ("manufacturerId".equals(key)) {
                manufacturerId = Integer.parseInt(value);
            } else if ("isLocked".equals(key)) {
                isLocked = Boolean.parseBoolean(value);
            } else if ("batteryLevel".equals(key)) {
                batteryLevel = Integer.parseInt(value);
            }
        }
        SmartLock sl = new SmartLock(deviceId, name, manufacturerId);
        sl.setLocked(isLocked);
        sl.setBatteryLevel(batteryLevel);
        return sl;
    }

    private static AutomationScene parseAutomationScene(String line) {
        return null;
    }

    private static DeviceAction parseDeviceAction(String line) {
        return null;
    }
}
