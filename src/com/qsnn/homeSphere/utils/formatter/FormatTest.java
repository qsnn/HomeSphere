package com.qsnn.homeSphere.utils.formatter;

import com.qsnn.homeSphere.HomeSphereSystem;
import com.qsnn.homeSphere.domain.deviceModule.devices.DeviceType;
import com.qsnn.homeSphere.domain.deviceModule.services.Manufacturer;
import com.qsnn.homeSphere.domain.house.Household;

import java.util.ArrayList;
import java.util.List;

public class FormatTest {
    public static void main(String[] args) {
        List<Manufacturer> manufacturers = new ArrayList<>();
        HomeSphereSystem system = null;
        try {
            HomeSphereSystem.initialize(new Household(1, "陕西省-西安市-长安区-东大街道-东祥路1号-西北工业大学"));
            system = HomeSphereSystem.getInstance();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

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

        system.runScene(1);

        System.out.println(new XmlRunningLogFormatter().format(system.getHousehold()));


    }
}
