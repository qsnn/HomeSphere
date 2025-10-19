package com.qsnn.homeSphere;

import com.qsnn.homeSphere.domain.automationScene.AutomationScene;
import com.qsnn.homeSphere.domain.deviceModule.Device;
import com.qsnn.homeSphere.domain.deviceModule.attributes.DeviceAttribute;
import com.qsnn.homeSphere.domain.deviceModule.Manufacturer;
import com.qsnn.homeSphere.domain.house.Household;
import com.qsnn.homeSphere.domain.house.Room;
import com.qsnn.homeSphere.domain.users.User;
import com.qsnn.homeSphere.domain.deviceModule.attributes.BooleanAttribute;
import com.qsnn.homeSphere.domain.deviceModule.attributes.RangeAttribute;
import com.qsnn.homeSphere.domain.deviceModule.attributes.StringChoiceAttribute;
import com.qsnn.homeSphere.log.Log;

import java.util.*;

import static com.qsnn.homeSphere.domain.deviceModule.devices.DeviceType.*;

/**
 * 基于实际框架的智能家居系统测试类
 */
public class Test {

    public static void main(String[] args) {
        System.out.println("=== 智能家居系统测试开始 ===\n");

        // 1. 初始化系统
        HomeSphereSystem system = new HomeSphereSystem();
        System.out.println("1. 系统初始化完成");

        // 2. 创建制造商
        Set<Device.ConnectMode> xiaomiModes = Set.of(Device.ConnectMode.WIFI, Device.ConnectMode.BLUETOOTH);
        Set<Device.ConnectMode> greeModes = Set.of(Device.ConnectMode.WIFI);
        Set<Device.ConnectMode> philipsModes = Set.of(Device.ConnectMode.WIFI, Device.ConnectMode.BLUETOOTH);

        Manufacturer xiaomi = new Manufacturer("小米", xiaomiModes);
        Manufacturer gree = new Manufacturer("格力", greeModes);
        Manufacturer philips = new Manufacturer("飞利浦", philipsModes);

        // 3. 注册用户
        Integer aliceId = system.registerUser("alice", "password123", "Alice", "北京市朝阳区");
        Integer bobId = system.registerUser("bob", "password456", "Bob", "上海市浦东新区");
        Integer charlieId = system.registerUser("charlie", "password789", "Charlie", "广州市天河区");

        User alice = system.getUserByID(aliceId);
        User bob = system.getUserByID(bobId);
        System.out.println("2. 用户注册完成: " + alice.getName() + ", " + bob.getName());

        // 4. 用户登录
        User loggedInAlice = system.login(aliceId, "password123");
        System.out.println("3. 用户登录: " + (loggedInAlice != null ? "成功" : "失败"));

        // 5. 创建家庭
        Integer aliceHouseholdId = system.createHousehold("Alice的家", "北京市朝阳区XX小区", aliceId);
        Integer bobHouseholdId = system.createHousehold("Bob的公寓", "上海市浦东新区XX路", bobId);

        Household aliceHousehold = system.getHouseholdByID(aliceHouseholdId);
        Household bobHousehold = system.getHouseholdByID(bobHouseholdId);
        System.out.println("4. 家庭创建完成: " + aliceHousehold.getName() + ", " + bobHousehold.getName());

        // 6. 添加家庭成员（使用正确的参数顺序：操作者ID, 用户ID, 家庭ID）
        System.out.println("5. 家庭成员添加测试:");
        try {
            // Alice（管理员）添加Bob到Alice的家
            system.createRelations(aliceId, bobId, aliceHouseholdId);
            System.out.println("  - Alice添加Bob到Alice的家: 成功");
        } catch (Exception e) {
            System.out.println("  - Alice添加Bob到Alice的家: 失败 - " + e.getMessage());
        }

        try {
            // Alice（管理员）添加Charlie到Alice的家
            system.createRelations(aliceId, charlieId, aliceHouseholdId);
            System.out.println("  - Alice添加Charlie到Alice的家: 成功");
        } catch (Exception e) {
            System.out.println("  - Alice添加Charlie到Alice的家: 失败 - " + e.getMessage());
        }

        try {
            // Bob（非管理员）尝试添加Charlie到Alice的家 - 应该失败
            system.createRelations(bobId, charlieId, aliceHouseholdId);
            System.out.println("  - Bob添加Charlie到Alice的家: 成功");
        } catch (Exception e) {
            System.out.println("  - Bob添加Charlie到Alice的家: 失败 - " + e.getMessage());
        }

        // 7. 创建房间
        Integer livingRoomId = system.createRoom(aliceHouseholdId, "客厅", 25.0);
        Integer bedroomId = system.createRoom(aliceHouseholdId, "主卧室", 15.0);
        Integer kitchenId = system.createRoom(aliceHouseholdId, "厨房", 12.0);

        Integer bobLivingRoomId = system.createRoom(bobHouseholdId, "客厅", 20.0);
        System.out.println("6. 房间创建完成");

        // 8. 使用新的createDevice方法创建设备
        System.out.println("7. 设备创建开始");

        // Alice家的设备
        Integer livingRoomACId = system.createDevice(AIR_CONDITIONER, 1, "卧室空调", "AC_OS",
                gree, 1200.0, Device.ConnectMode.WIFI, Device.PowerMode.MAINSPOWER);
        Integer livingRoomLightId = system.createDevice(LIGHT_BULB,2, "客厅主灯","LT-OS", philips, 10.0, Device.ConnectMode.WIFI, Device.PowerMode.MAINSPOWER);
        Integer mainDoorLockId = system.createDevice(SMART_LOCK, 3, "大门门锁", "LC-OS", xiaomi, 5.0, Device.ConnectMode.BLUETOOTH, Device.PowerMode.BATTERY);
        Integer bedroomLightId = system.createDevice(LIGHT_BULB, 4, "卧室夜灯", "LT-OS", xiaomi, 8.0, Device.ConnectMode.WIFI, Device.PowerMode.MAINSPOWER);

        // Bob家的设备
        Integer bobACId = system.createDevice(AIR_CONDITIONER, 5, "卧室空调", "AC_OS",
                gree, 1200.0, Device.ConnectMode.WIFI, Device.PowerMode.MAINSPOWER);

        // 创建自定义设备
        Set<DeviceAttribute<?>> customAttributes = Set.of(
                new BooleanAttribute("power", false),
                new RangeAttribute("speed", 1, 5, 3, "档"),
                new StringChoiceAttribute("mode", "normal", "normal", "turbo", "quiet")
        );
        Integer customDeviceId = system.createDevice(UNDEFINED, 6, "空气净化器", "ACN-OS", xiaomi, 50.0, Device.ConnectMode.ZIGBEE, Device.PowerMode.MAINSPOWER, customAttributes);

        // 获取设备对象
        Device livingRoomAC = system.getDeviceByID(livingRoomACId);
        Device livingRoomLight = system.getDeviceByID(livingRoomLightId);
        Device mainDoorLock = system.getDeviceByID(mainDoorLockId);
        Device bedroomLight = system.getDeviceByID(bedroomLightId);
        Device bobAC = system.getDeviceByID(bobACId);
        Device customDevice = system.getDeviceByID(customDeviceId);

        System.out.println("设备创建完成，共创建 " + system.getDevices().size() + " 个设备");

        // 9. 将设备分配到房间
        system.addDeviceToRoom(livingRoomACId, livingRoomId);
        system.addDeviceToRoom(livingRoomLightId, livingRoomId);
        system.addDeviceToRoom(mainDoorLockId, livingRoomId);
        system.addDeviceToRoom(bedroomLightId, bedroomId);
        system.addDeviceToRoom(bobACId, bobLivingRoomId);
        system.addDeviceToRoom(customDeviceId, livingRoomId);

        System.out.println("8. 设备分配到房间完成");

        // 10. 设备操作演示
        System.out.println("\n=== 设备操作演示 ===");

        livingRoomAC.connect();
        livingRoomLight.connect();
        mainDoorLock.connect();
        customDevice.connect();

        livingRoomAC.open();
        livingRoomLight.open();
        mainDoorLock.open();
        customDevice.open();

        Map<String, Object> acParams = new HashMap<>();
        acParams.put("temperature", 24);
        acParams.put("mode", "COOL");
        acParams.put("fan_speed", 3);
        livingRoomAC.execute(livingRoomAC, acParams);

        Map<String, Object> lightParams = new HashMap<>();
        lightParams.put("brightness", 80);
        lightParams.put("color_temperature", "WARM");
        livingRoomLight.execute(livingRoomLight, lightParams);

        Map<String, Object> lockParams = new HashMap<>();
        lockParams.put("lock_status", true);
        mainDoorLock.execute(mainDoorLock, lockParams);

        Map<String, Object> customParams = new HashMap<>();
        customParams.put("power", true);
        customParams.put("speed", 4);
        customParams.put("mode", "turbo");
        customDevice.execute(customDevice, customParams);

        System.out.println("9. 设备初始设置完成");

        // 11. 移动设备演示
        System.out.println("\n=== 设备移动演示 ===");
        try {
            boolean moveSuccess = system.moveDevice(aliceId, bedroomLight.getDeviceID(), livingRoomId);
            System.out.println("设备移动: " + (moveSuccess ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("设备移动失败: " + e.getMessage());
        }

        // 12. 自动化场景演示
        System.out.println("\n=== 自动化场景演示 ===");

        AutomationScene morningScene = system.getAutomationSceneByID(system.createAutomationScene(aliceHouseholdId, "早安模式", "早上自动开启的场景"));

        Map<String, Object> lightMorningOps = new HashMap<>();
        lightMorningOps.put("luminance", 70);
        lightMorningOps.put("colorTemperature", "WARM");

        Map<String, Object> acMorningOps = new HashMap<>();
        acMorningOps.put("temperature", 26);
        acMorningOps.put("mode", "COOL");
        acMorningOps.put("fan_speed", 2);

        morningScene.addDeviceOperation(livingRoomLight, lightMorningOps);
        morningScene.addDeviceOperation(livingRoomAC, acMorningOps);

        System.out.println("创建场景: " + morningScene.getName());
        System.out.println("场景设备数: " + morningScene.getDeviceCount());

        System.out.println("执行自动化场景...");
        morningScene.execute();

        // 13. 查询演示
        System.out.println("\n=== 系统查询演示 ===");

        System.out.println("Alice的家庭: " + system.getHouseholdsByUser(aliceId));
        System.out.println("Bob的家庭: " + system.getHouseholdsByUser(bobId));

        System.out.println("Alice家的成员: " + system.getUsersByHousehold(aliceHouseholdId));

        System.out.println("客厅设备数量: " + system.getDevicesByRoom(livingRoomId).size());
        System.out.println("卧室设备数量: " + system.getDevicesByRoom(bedroomId).size());

        Room acRoom = system.getRoomByDevice(livingRoomAC.getDeviceID());
        System.out.println("空调所在房间: " + (acRoom != null ? acRoom.getName() : "未知"));

        // 14. 权限验证演示
        System.out.println("\n=== 权限验证演示 ===");

        try {
            boolean canMove = system.moveDevice(aliceId, livingRoomLight.getDeviceID(), bedroomId);
            System.out.println("Alice移动设备: " + (canMove ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("Alice移动设备失败: " + e.getMessage());
        }

        try {
            boolean canMove = system.moveDevice(charlieId, bobAC.getDeviceID(), bobLivingRoomId);
            System.out.println("Charlie移动Bob家设备: " + (canMove ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("Charlie移动Bob家设备失败: " + e.getMessage());
        }

        // 15. 能耗计算演示
        System.out.println("\n=== 能耗计算演示 ===");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        livingRoomAC.close();
        livingRoomLight.close();
        customDevice.close();

        Double acConsumption = livingRoomAC.calculateAllPowerConsumption();
        Double lightConsumption = livingRoomLight.calculateAllPowerConsumption();

        System.out.println("客厅空调总能耗: " + acConsumption + " Wh");
        System.out.println("客厅主灯总能耗: " + lightConsumption + " Wh");

        // 16. 日志查询演示
        System.out.println("\n=== 日志系统演示 ===");

        Set<Log> allDeviceLogs = system.getAllDeviceLogs();
        Set<Log> allUserLogs = system.getAllUserLogs();

        System.out.println("所有设备日志数: " + allDeviceLogs.size());
        System.out.println("所有用户日志数: " + allUserLogs.size());

        System.out.println("设备操作日志示例:");
        allDeviceLogs.stream()
                .limit(3)
                .forEach(log -> System.out.println("  - " + log.getEvent() + " [" + log.getEventType() + "]"));

        // 17. 断开设备连接
        System.out.println("\n=== 断开设备连接 ===");
        livingRoomAC.disconnect();
        livingRoomLight.disconnect();
        mainDoorLock.disconnect();

        // 18. 系统统计
        System.out.println("\n=== 系统统计 ===");
        System.out.println("总用户数: " + system.getUsers().size());
        System.out.println("总家庭数: " + system.getHouseholds().size());
        System.out.println("总设备数: " + system.getDevices().size());
        System.out.println("总房间数: " + system.getRooms().size());

        System.out.println("Alice的家庭数量: " + system.countHouseholdsByUser(aliceId));
        System.out.println("Alice家的成员数量: " + system.countUsersByHousehold(aliceHouseholdId));

        System.out.println("\n=== 智能家居系统测试完成 ===");
    }
}