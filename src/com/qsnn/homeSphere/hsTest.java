package com.qsnn.homeSphere;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.qsnn.homeSphere.domain.deviceModule.devices.LightBulb;
import com.qsnn.homeSphere.domain.deviceModule.services.Manufacturer;
import com.qsnn.homeSphere.domain.house.Household;
import com.qsnn.homeSphere.domain.house.Room;
import com.qsnn.homeSphere.domain.users.User;

import static org.junit.Assert.*;

public class hsTest {

    private Household household;
    private Room livingRoom;
    private LightBulb light;
    private Manufacturer manufacturer;
    private User user;

    @Before
    public void setUp() {
        // 创建基础对象
        household = new Household(1, "测试家庭地址");
        manufacturer = new Manufacturer(1, "测试制造商", "WiFi");
        light = new LightBulb(1, "客厅灯", manufacturer);
        livingRoom = new Room(1, "客厅", 20.0);
        user = new User(1, "testuser", "password", "测试用户", "test@email.com");

        // 设置设备状态
        light.setBrightness(50);
        light.setColorTemp(3000);

        // 组装对象关系
        livingRoom.addDevice(light);
        household.addRoom(livingRoom);
        household.addUser(user);
    }

    @After
    public void tearDown() {
        household = null;
        livingRoom = null;
        light = null;
        manufacturer = null;
        user = null;
    }

    @Test
    public void testLightBulbBasicOperations() {
        System.out.println("=== 测试灯泡基础功能 ===");

        // 测试电源控制
        assertFalse("初始状态应为关闭", light.isPowerStatus());

        light.powerOn();
        assertTrue("开机后状态应为开启", light.isPowerStatus());
        System.out.println("灯泡开机测试通过");

        light.powerOff();
        assertFalse("关机后状态应为关闭", light.isPowerStatus());
        System.out.println("灯泡关机测试通过");
    }

    @Test
    public void testLightBulbSettings() {
        System.out.println("=== 测试灯泡设置 ===");

        // 测试亮度设置
        light.setBrightness(75);
        assertEquals("亮度应设置为75", 75, light.getBrightness());
        System.out.println("亮度设置: " + light.getBrightness());

        // 测试色温设置
        light.setColorTemp(4000);
        assertEquals("色温应设置为4000", 4000, light.getColorTemp());
        System.out.println("色温设置: " + light.getColorTemp());
    }

    @Test
    public void testRoomDeviceManagement() {
        System.out.println("=== 测试房间设备管理 ===");

        // 测试房间中的设备
        assertEquals("房间应有1个设备", 1, livingRoom.getDevices().size());

        LightBulb roomLight = (LightBulb) livingRoom.getDevices().get(0);
        assertEquals("设备名称应匹配", "客厅灯", roomLight.getName());
        System.out.println("房间设备: " + roomLight.getName());
    }

    @Test
    public void testHouseholdStructure() {
        System.out.println("=== 测试家庭结构 ===");

        // 测试家庭信息
        assertEquals("家庭ID应为1", 1, household.getHouseholdId());
        assertEquals("地址应匹配", "测试家庭地址", household.getAddress());

        // 测试房间管理
        assertEquals("家庭应有1个房间", 1, household.getRooms().size());
        assertEquals("房间名称应为客厅", "客厅", household.getRooms().get(0).getName());

        // 测试用户管理
        assertEquals("家庭应有1个用户", 1, household.getUsers().size());
        assertEquals("用户名应匹配", "测试用户", household.getUsers().get(0).getUserName());

        System.out.println("家庭结构测试通过");
    }

    @Test
    public void testDevicePowerCycle() {
        System.out.println("=== 测试设备电源循环 ===");

        // 完整的电源循环测试
        light.powerOn();
        assertTrue("第一次开机应成功", light.isPowerStatus());

        light.powerOff();
        assertFalse("第一次关机应成功", light.isPowerStatus());

        light.powerOn();
        assertTrue("第二次开机应成功", light.isPowerStatus());

        System.out.println("电源循环测试通过");
    }

    @Test
    public void testBoundaryConditions() {
        System.out.println("=== 测试边界条件 ===");

        // 测试亮度边界值
        light.setBrightness(0);
        assertEquals("最小亮度应为0", 0, light.getBrightness());

        light.setBrightness(100);
        assertEquals("最大亮度应为100", 100, light.getBrightness());

        System.out.println("边界条件测试通过");
    }

    @Test
    public void testDeviceInformation() {
        System.out.println("=== 测试设备信息 ===");

        // 测试设备基本信息
        assertEquals("设备ID应为1", 1, light.getDeviceId());
        assertEquals("设备名称应匹配", "客厅灯", light.getName());
        assertEquals("制造商应匹配", manufacturer, light.getManufacturer());

        System.out.println("设备ID: " + light.getDeviceId());
        System.out.println("设备名称: " + light.getName());
        System.out.println("制造商: " + light.getManufacturer().getName());
    }
}