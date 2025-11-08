package com.qsnn.homeSphere.utils.formatter;

import com.qsnn.homeSphere.domain.deviceModule.Device;
import com.qsnn.homeSphere.domain.deviceModule.services.RunningLog;
import com.qsnn.homeSphere.domain.house.Household;
import com.qsnn.homeSphere.domain.house.Room;
import com.qsnn.homeSphere.utils.DateUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * XML格式运行日志格式化器
 *
 * <p>将设备运行日志格式化为XML格式输出</p>
 */
public class XmlRunningLogFormatter implements RunningLogFormatter {
    private static volatile XmlRunningLogFormatter instance;

    public static XmlRunningLogFormatter getInstance() {
        if (instance == null) {
            synchronized (XmlRunningLogFormatter.class) {
                if (instance == null) {
                    instance = new XmlRunningLogFormatter();
                }
            }
        }
        return instance;
    }

    @Override
    public String format(Household household) {
        if (household == null) {
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><household/>";
        }

        try {
            // 创建XML文档
            Document document = DocumentHelper.createDocument();

            // 创建根元素 household
            Element householdElement = document.addElement("household")
                    .addAttribute("householdId", String.valueOf(household.getHouseholdId()))
                    .addAttribute("address", household.getAddress());

            // 创建 rooms 元素
            Element roomsElement = householdElement.addElement("rooms");

            // 遍历所有房间
            for (Room room : household.getRooms().values()) {
                // 创建 room 元素
                Element roomElement = roomsElement.addElement("room")
                        .addAttribute("roomId", String.valueOf(room.getRoomId()))
                        .addAttribute("roomName", room.getName());

                // 创建 devices 元素
                Element devicesElement = roomElement.addElement("devices");

                // 遍历房间内的所有设备
                for (Device device : room.getDevices().values()) {
                    // 创建 device 元素
                    Element deviceElement = devicesElement.addElement("device")
                            .addAttribute("deviceId", String.valueOf(device.getDeviceId()))
                            .addAttribute("deviceName", device.getName());

                    // 创建 runningLogs 元素
                    Element runningLogsElement = deviceElement.addElement("runningLogs");

                    // 遍历设备的所有运行日志
                    for (RunningLog log : device.getRunningLogs()) {
                        // 创建 runningLog 元素
                        runningLogsElement.addElement("runningLog")
                                .addAttribute("dateTime", DateUtil.formatDateToString(log.getDate()))
                                .addAttribute("event", log.getEvent())
                                .addAttribute("type", log.getType().name())
                                .addAttribute("note", log.getNote());
                    }
                }
            }

            // 返回紧凑格式的XML字符串
            return document.asXML();

        } catch (Exception e) {
            throw new RuntimeException("生成XML失败: " + e.getMessage(), e);
        }
    }
}