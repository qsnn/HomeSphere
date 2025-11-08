package com.qsnn.homeSphere.utils.formatter;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.qsnn.homeSphere.domain.deviceModule.Device;
import com.qsnn.homeSphere.domain.deviceModule.services.RunningLog;
import com.qsnn.homeSphere.domain.house.Household;
import com.qsnn.homeSphere.domain.house.Room;
import com.qsnn.homeSphere.utils.DateUtil;

import java.util.List;
import java.util.Map;

public class JsonRunningLogFormatter implements RunningLogFormatter{
    private static volatile JsonRunningLogFormatter instance;

    public static JsonRunningLogFormatter getInstance() {
        if (instance == null) {
            synchronized (JsonRunningLogFormatter.class) {
                if (instance == null) {
                    instance = new JsonRunningLogFormatter();
                }
            }
        }
        return instance;
    }

    @Override
    public String format(Household household) {
        if (household == null) {
            return "{}";
        }

        JSONObject householdJson = new JSONObject();
        householdJson.put("householdId", household.getHouseholdId());
        householdJson.put("address", household.getAddress());

        // 序列化房间列表
        JSONArray roomsArray = serializeRoomsToJsonArray(household.getRooms());
        householdJson.put("rooms", roomsArray);

        return householdJson.toJSONString();
    }

    /**
     * 将房间Map序列化为JSON数组
     *
     * @param roomsMap 房间Map
     * @return JSON数组
     */
    private static JSONArray serializeRoomsToJsonArray(Map<Integer, Room> roomsMap) {
        JSONArray roomsArray = new JSONArray();

        if (roomsMap != null) {
            for (Room room : roomsMap.values()) {
                JSONObject roomJson = serializeRoomToJson(room);
                roomsArray.add(roomJson);
            }
        }

        return roomsArray;
    }

    /**
     * 将单个房间序列化为JSON对象
     *
     * @param room 房间对象
     * @return JSON对象
     */
    private static JSONObject serializeRoomToJson(Room room) {
        JSONObject roomJson = new JSONObject();
        roomJson.put("roomId", room.getRoomId());
        roomJson.put("roomName", room.getName());

        // 序列化设备列表
        JSONArray devicesArray = serializeDevicesToJsonArray(room.getDevices());
        roomJson.put("devices", devicesArray);

        return roomJson;
    }

    /**
     * 将设备Map序列化为JSON数组
     *
     * @param devicesMap 设备Map
     * @return JSON数组
     */
    private static JSONArray serializeDevicesToJsonArray(Map<Integer, Device> devicesMap) {
        JSONArray devicesArray = new JSONArray();

        if (devicesMap != null) {
            for (Device device : devicesMap.values()) {
                JSONObject deviceJson = serializeDeviceToJson(device);
                devicesArray.add(deviceJson);
            }
        }

        return devicesArray;
    }

    /**
     * 将单个设备序列化为JSON对象
     *
     * @param device 设备对象
     * @return JSON对象
     */
    private static JSONObject serializeDeviceToJson(Device device) {
        JSONObject deviceJson = new JSONObject();
        deviceJson.put("deviceId", device.getDeviceId());
        deviceJson.put("deviceName", device.getName());

        // 序列化运行日志
        JSONArray logsArray = serializeRunningLogsToJsonArray(device.getRunningLogs());
        deviceJson.put("runningLogs", logsArray);

        return deviceJson;
    }

    /**
     * 将运行日志列表序列化为JSON数组
     *
     * @param runningLogs 运行日志列表
     * @return JSON数组
     */
    private static JSONArray serializeRunningLogsToJsonArray(List<RunningLog> runningLogs) {
        JSONArray logsArray = new JSONArray();

        if (runningLogs != null) {
            for (RunningLog log : runningLogs) {
                JSONObject logJson = serializeRunningLogToJson(log);
                logsArray.add(logJson);
            }
        }

        return logsArray;
    }

    /**
     * 将单个运行日志序列化为JSON对象
     *
     * @param log 运行日志对象
     * @return JSON对象
     */
    private static JSONObject serializeRunningLogToJson(RunningLog log) {
        JSONObject logJson = new JSONObject();
        logJson.put("dateTime", DateUtil.formatDateToString(log.getDate()));
        logJson.put("event", log.getEvent());
        logJson.put("type", log.getType().name());
        logJson.put("note", log.getNote());
        return logJson;
    }
}
