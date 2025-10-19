package com.qsnn.homeSphere;

import com.qsnn.homeSphere.domain.automationScene.AutomationScene;
import com.qsnn.homeSphere.domain.deviceModule.Device;
import com.qsnn.homeSphere.domain.deviceModule.Manufacturer;
import com.qsnn.homeSphere.domain.deviceModule.attributes.BooleanAttribute;
import com.qsnn.homeSphere.domain.deviceModule.attributes.DeviceAttribute;
import com.qsnn.homeSphere.domain.deviceModule.attributes.RangeAttribute;
import com.qsnn.homeSphere.domain.deviceModule.attributes.StringChoiceAttribute;
import com.qsnn.homeSphere.domain.house.Household;
import com.qsnn.homeSphere.domain.house.Room;
import com.qsnn.homeSphere.domain.users.User;
import com.qsnn.homeSphere.log.Log;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.qsnn.homeSphere.domain.deviceModule.devices.DeviceType.*;
import static org.junit.Assert.*;

/**
 * 基于 JUnit 4.13.1 的智能家居系统测试类
 */
public class HomeSphereSystemTest {

    private static HomeSphereSystem system;
    private static Integer aliceId;
    private static Integer bobId;
    private static Integer charlieId;
    private static Integer aliceHouseholdId;
    private static Integer bobHouseholdId;
    private static Integer livingRoomId;
    private static Integer bedroomId;

    // 测试设备ID
    private static Integer livingRoomACId;
    private static Integer livingRoomLightId;
    private static Integer mainDoorLockId;
    private static Integer bedroomLightId;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpClass() {
        System.out.println("=== 智能家居系统测试开始 ===\n");
        system = new HomeSphereSystem();
        System.out.println("1. 系统初始化完成");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("\n=== 智能家居系统测试完成 ===");
    }

    @Before
    public void setUp() {
        // 在每个测试方法执行前初始化基础数据
        initializeTestData();
    }

    @After
    public void tearDown() {
        // 清理资源（如果需要）
    }

    private void initializeTestData() {
        // 创建制造商
        Set<Device.ConnectMode> xiaomiModes = Set.of(Device.ConnectMode.WIFI, Device.ConnectMode.BLUETOOTH);
        Set<Device.ConnectMode> greeModes = Set.of(Device.ConnectMode.WIFI);
        Set<Device.ConnectMode> philipsModes = Set.of(Device.ConnectMode.WIFI, Device.ConnectMode.BLUETOOTH);

        Manufacturer xiaomi = new Manufacturer("小米", xiaomiModes);
        Manufacturer gree = new Manufacturer("格力", greeModes);
        Manufacturer philips = new Manufacturer("飞利浦", philipsModes);

        // 注册用户
        aliceId = system.registerUser("alice", "password123", "Alice", "北京市朝阳区");
        bobId = system.registerUser("bob", "password456", "Bob", "上海市浦东新区");
        charlieId = system.registerUser("charlie", "password789", "Charlie", "广州市天河区");

        // 用户登录
        User loggedInAlice = system.login(aliceId, "password123");

        // 创建家庭
        aliceHouseholdId = system.createHousehold("Alice的家", "北京市朝阳区XX小区", aliceId);
        bobHouseholdId = system.createHousehold("Bob的公寓", "上海市浦东新区XX路", bobId);

        // 创建房间
        livingRoomId = system.createRoom(aliceHouseholdId, "客厅", 25.0);
        bedroomId = system.createRoom(aliceHouseholdId, "主卧室", 15.0);

        // 创建设备
        livingRoomACId = system.createDevice(AIR_CONDITIONER, 1, "卧室空调", "AC_OS",
                gree, 1200.0, Device.ConnectMode.WIFI, Device.PowerMode.MAINSPOWER);
        livingRoomLightId = system.createDevice(LIGHT_BULB, 2, "客厅主灯", "LT-OS",
                philips, 10.0, Device.ConnectMode.WIFI, Device.PowerMode.MAINSPOWER);
        mainDoorLockId = system.createDevice(SMART_LOCK, 3, "大门门锁", "LC-OS",
                xiaomi, 5.0, Device.ConnectMode.BLUETOOTH, Device.PowerMode.BATTERY);
        bedroomLightId = system.createDevice(LIGHT_BULB, 4, "卧室夜灯", "LT-OS",
                xiaomi, 8.0, Device.ConnectMode.WIFI, Device.PowerMode.MAINSPOWER);

        // 将设备分配到房间
        system.addDeviceToRoom(livingRoomACId, livingRoomId);
        system.addDeviceToRoom(livingRoomLightId, livingRoomId);
        system.addDeviceToRoom(mainDoorLockId, livingRoomId);
        system.addDeviceToRoom(bedroomLightId, bedroomId);
    }

    @Test
    public void testUserRegistrationAndLogin() {
        System.out.println("测试用户注册和登录");

        User alice = system.getUserByID(aliceId);
        User bob = system.getUserByID(bobId);

        assertNotNull("Alice用户应该存在", alice);
        assertNotNull("Bob用户应该存在", bob);
        assertEquals("Alice姓名应该正确", "Alice", alice.getName());
        assertEquals("Bob姓名应该正确", "Bob", bob.getName());

        // 测试登录
        User loggedInUser = system.login(aliceId, "password123");
        assertNotNull("登录应该成功", loggedInUser);
        assertEquals("登录用户ID应该匹配", (int)aliceId, loggedInUser.getUserID());

        // 测试错误密码
        User failedLogin = system.login(aliceId, "wrongpassword");
        assertNull("错误密码应该登录失败", failedLogin);
    }

    @Test
    public void testHouseholdCreation() {
        System.out.println("测试家庭创建");

        Household aliceHousehold = system.getHouseholdByID(aliceHouseholdId);
        Household bobHousehold = system.getHouseholdByID(bobHouseholdId);

        assertNotNull("Alice家庭应该存在", aliceHousehold);
        assertNotNull("Bob家庭应该存在", bobHousehold);
        assertEquals("Alice家庭名称应该正确", "Alice的家", aliceHousehold.getName());
        assertEquals("Bob家庭名称应该正确", "Bob的公寓", bobHousehold.getName());
    }

    @Test
    public void testRoomCreation() {
        System.out.println("测试房间创建");

        Room livingRoom = system.getRoomByID(livingRoomId);
        Room bedroom = system.getRoomByID(bedroomId);

        assertNotNull("客厅应该存在", livingRoom);
        assertNotNull("卧室应该存在", bedroom);
        assertEquals("客厅名称应该正确", "客厅", livingRoom.getName());
        assertEquals("卧室名称应该正确", "主卧室", bedroom.getName());
        assertEquals("客厅面积应该正确", 25.0, livingRoom.getArea(), 0.01);
    }

    @Test
    public void testDeviceCreationAndAssignment() {
        System.out.println("测试设备创建和分配");

        Device livingRoomAC = system.getDeviceByID(livingRoomACId);
        Device livingRoomLight = system.getDeviceByID(livingRoomLightId);

        assertNotNull("空调设备应该存在", livingRoomAC);
        assertNotNull("灯光设备应该存在", livingRoomLight);
        assertEquals("空调设备名称应该正确", "卧室空调", livingRoomAC.getName());
        assertEquals("灯光设备名称应该正确", "客厅主灯", livingRoomLight.getName());

        // 测试设备分配到的房间
        Room acRoom = system.getRoomByDevice(livingRoomACId);
        Room lightRoom = system.getRoomByDevice(livingRoomLightId);

        assertNotNull("空调应该有分配的房间", acRoom);
        assertNotNull("灯光应该有分配的房间", lightRoom);
        assertEquals("空调应该在客厅", "客厅", acRoom.getName());
        assertEquals("灯光应该在客厅", "客厅", lightRoom.getName());
    }

    @Test
    public void testFamilyMemberManagement() {
        System.out.println("测试家庭成员管理");

        // Alice（管理员）添加Bob到Alice的家
        try {
            system.createRelations(aliceId, bobId, aliceHouseholdId);
            System.out.println("  - Alice添加Bob到Alice的家: 成功");
        } catch (Exception e) {
            fail("Alice添加Bob应该成功: " + e.getMessage());
        }

        // Alice（管理员）添加Charlie到Alice的家
        try {
            system.createRelations(aliceId, charlieId, aliceHouseholdId);
            System.out.println("  - Alice添加Charlie到Alice的家: 成功");
        } catch (Exception e) {
            fail("Alice添加Charlie应该成功: " + e.getMessage());
        }

        // 验证家庭成员数量
        Set<User> householdMembers = system.getUsersByHousehold(aliceHouseholdId);
        assertTrue("家庭成员应该包含添加的用户", householdMembers.size() >= 2);
    }

    @Test
    public void testDeviceOperations() {
        System.out.println("测试设备操作");

        Device livingRoomAC = system.getDeviceByID(livingRoomACId);
        Device livingRoomLight = system.getDeviceByID(livingRoomLightId);

        // 连接设备
        livingRoomAC.connect();
        livingRoomLight.connect();

        // 打开设备
        livingRoomAC.open();
        livingRoomLight.open();

        // 执行设备操作
        Map<String, Object> acParams = new HashMap<>();
        acParams.put("temperature", 24);
        acParams.put("mode", "COOL");
        acParams.put("fan_speed", 3);
        livingRoomAC.execute(livingRoomAC, acParams);

        Map<String, Object> lightParams = new HashMap<>();
        lightParams.put("brightness", 80);
        lightParams.put("color_temperature", "WARM");
        livingRoomLight.execute(livingRoomLight, lightParams);

        // 关闭设备
        livingRoomAC.close();
        livingRoomLight.close();

        // 断开连接
        livingRoomAC.disconnect();
        livingRoomLight.disconnect();

        System.out.println("设备操作测试完成");
    }

    @Test
    public void testDeviceMovement() {
        System.out.println("测试设备移动");

        // 将卧室灯光移动到客厅
        boolean moveSuccess = system.moveDevice(aliceId, bedroomLightId, livingRoomId);
        assertTrue("设备移动应该成功", moveSuccess);

        // 验证设备移动后的位置
        Room newRoom = system.getRoomByDevice(bedroomLightId);
        assertNotNull("移动后设备应该有房间", newRoom);
        assertEquals("设备应该在新的房间", "客厅", newRoom.getName());
    }

    @Test
    public void testAutomationScene() {
        System.out.println("测试自动化场景");

        // 创建自动化场景
        Integer sceneId = system.createAutomationScene(aliceHouseholdId, "早安模式", "早上自动开启的场景");
        AutomationScene morningScene = system.getAutomationSceneByID(sceneId);

        assertNotNull("场景应该创建成功", morningScene);
        assertEquals("场景名称应该正确", "早安模式", morningScene.getName());

        // 获取设备并添加到场景
        Device livingRoomLight = system.getDeviceByID(livingRoomLightId);
        Device livingRoomAC = system.getDeviceByID(livingRoomACId);

        Map<String, Object> lightMorningOps = new HashMap<>();
        lightMorningOps.put("luminance", 70);
        lightMorningOps.put("colorTemperature", "WARM");

        Map<String, Object> acMorningOps = new HashMap<>();
        acMorningOps.put("temperature", 26);
        acMorningOps.put("mode", "COOL");
        acMorningOps.put("fan_speed", 2);

        morningScene.addDeviceOperation(livingRoomLight, lightMorningOps);
        morningScene.addDeviceOperation(livingRoomAC, acMorningOps);

        assertEquals("场景应该包含2个设备", 2, morningScene.getDeviceCount());

        // 执行场景
        morningScene.execute();
        System.out.println("自动化场景执行完成");
    }

    @Test
    public void testPowerConsumptionCalculation() throws InterruptedException {
        System.out.println("测试能耗计算");

        Device livingRoomAC = system.getDeviceByID(livingRoomACId);
        Device livingRoomLight = system.getDeviceByID(livingRoomLightId);

        // 连接并打开设备
        livingRoomAC.connect();
        livingRoomLight.connect();
        livingRoomAC.open();
        livingRoomLight.open();

        // 模拟设备运行一段时间
        Thread.sleep(2000);

        // 关闭设备
        livingRoomAC.close();
        livingRoomLight.close();

        // 计算能耗
        Double acConsumption = livingRoomAC.calculateAllPowerConsumption();
        Double lightConsumption = livingRoomLight.calculateAllPowerConsumption();

        assertNotNull("空调能耗应该可以计算", acConsumption);
        assertNotNull("灯光能耗应该可以计算", lightConsumption);
        assertTrue("能耗应该大于0", acConsumption >= 0);
        assertTrue("能耗应该大于0", lightConsumption >= 0);

        System.out.println("客厅空调总能耗: " + acConsumption + " Wh");
        System.out.println("客厅主灯总能耗: " + lightConsumption + " Wh");
    }

    @Test
    public void testSystemQueries() {
        System.out.println("测试系统查询");

        // 查询用户家庭
        Set<Household> aliceHouseholds = system.getHouseholdsByUser(aliceId);
        Set<Household> bobHouseholds = system.getHouseholdsByUser(bobId);

        assertFalse("Alice应该有家庭", aliceHouseholds.isEmpty());
        assertFalse("Bob应该有家庭", bobHouseholds.isEmpty());

        // 查询家庭成员
        Set<User> aliceHouseholdMembers = system.getUsersByHousehold(aliceHouseholdId);
        assertFalse("Alice家庭应该有成员", aliceHouseholdMembers.isEmpty());

        // 查询房间设备
        Set<Device> livingRoomDevices = system.getDevicesByRoom(livingRoomId);
        Set<Device> bedroomDevices = system.getDevicesByRoom(bedroomId);

        assertTrue("客厅应该有设备", livingRoomDevices.size() >= 3);
        assertTrue("卧室应该有设备", bedroomDevices.size() >= 1);
    }

    @Test
    public void testLoggingSystem() {
        System.out.println("测试日志系统");

        Set<Log> allDeviceLogs = system.getAllDeviceLogs();
        Set<Log> allUserLogs = system.getAllUserLogs();

        assertNotNull("设备日志应该存在", allDeviceLogs);
        assertNotNull("用户日志应该存在", allUserLogs);

        System.out.println("所有设备日志数: " + allDeviceLogs.size());
        System.out.println("所有用户日志数: " + allUserLogs.size());

        // 显示前几条日志作为示例
        allDeviceLogs.stream()
                .limit(2)
                .forEach(log -> System.out.println("  - " + log.getEvent() + " [" + log.getEventType() + "]"));
    }

    @Test
    public void testSystemStatistics() {
        System.out.println("测试系统统计");

        int totalUsers = system.getUsers().size();
        int totalHouseholds = system.getHouseholds().size();
        int totalDevices = system.getDevices().size();
        int totalRooms = system.getRooms().size();

        assertTrue("总用户数应该大于0", totalUsers >= 3);
        assertTrue("总家庭数应该大于0", totalHouseholds >= 2);
        assertTrue("总设备数应该大于0", totalDevices >= 4);
        assertTrue("总房间数应该大于0", totalRooms >= 2);

        int aliceHouseholdCount = system.countHouseholdsByUser(aliceId);
        int aliceHouseholdMemberCount = system.countUsersByHousehold(aliceHouseholdId);

        assertTrue("Alice的家庭数量应该大于0", aliceHouseholdCount > 0);
        assertTrue("Alice家的成员数量应该大于0", aliceHouseholdMemberCount > 0);

        System.out.println("总用户数: " + totalUsers);
        System.out.println("总家庭数: " + totalHouseholds);
        System.out.println("总设备数: " + totalDevices);
        System.out.println("总房间数: " + totalRooms);
        System.out.println("Alice的家庭数量: " + aliceHouseholdCount);
        System.out.println("Alice家的成员数量: " + aliceHouseholdMemberCount);
    }

    @Test
    public void testCustomDeviceCreation() {
        System.out.println("测试自定义设备创建");

        Set<Device.ConnectMode> xiaomiModes = Set.of(Device.ConnectMode.WIFI, Device.ConnectMode.BLUETOOTH);
        Manufacturer xiaomi = new Manufacturer("小米", xiaomiModes);

        Set<DeviceAttribute<?>> customAttributes = Set.of(
                new BooleanAttribute("power", false),
                new RangeAttribute("speed", 1, 5, 3, "档"),
                new StringChoiceAttribute("mode", "normal", "normal", "turbo", "quiet")
        );

        Integer customDeviceId = system.createDevice(UNDEFINED, 6, "空气净化器", "ACN-OS",
                xiaomi, 50.0, Device.ConnectMode.ZIGBEE, Device.PowerMode.MAINSPOWER, customAttributes);

        Device customDevice = system.getDeviceByID(customDeviceId);
        assertNotNull("自定义设备应该创建成功", customDevice);
        assertEquals("自定义设备名称应该正确", "空气净化器", customDevice.getName());

        // 测试自定义设备操作
        customDevice.connect();
        customDevice.open();

        Map<String, Object> customParams = new HashMap<>();
        customParams.put("power", true);
        customParams.put("speed", 4);
        customParams.put("mode", "turbo");
        customDevice.execute(customDevice, customParams);

        customDevice.close();
        customDevice.disconnect();

        System.out.println("自定义设备测试完成");
    }
}