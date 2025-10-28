package com.qsnn.homeSphere.domain.deviceModule.devices;

import com.qsnn.homeSphere.exceptions.InvalidParametersException;

public enum DeviceType {
    AIR_CONDITIONER(1){
        @Override
        public String getParameterName(String operation) {
            return switch (operation.toLowerCase()) {
                case "poweron" -> null;
                case "poweroff" -> null;
                case "settemperature" -> "目标温度(°C)";
                default -> throw new InvalidParametersException("AIR_CONDITIONER不支持操作: " + operation);
            };
        }
    },
    LIGHT_BULB(2){
        @Override
        public String getParameterName(String operation) {
            return switch (operation.toLowerCase()) {
                case "poweron" -> null;
                case "poweroff" -> null;
                case "setbrightness" -> "亮度值(0-100)";
                case "setcolortemp" -> "色温值(K)";
                default -> throw new InvalidParametersException("LIGHT_BULB不支持操作: " + operation); // 修正错误消息
            };
        }
    },
    SMART_LOCK(3){
        @Override
        public String getParameterName(String operation) {
            return switch (operation.toLowerCase()) {
                case "poweron" -> null;
                case "poweroff" -> null;
                case "lock" -> null;
                case "unlock" -> null;
                default -> throw new InvalidParametersException("SMART_LOCK不支持操作: " + operation); // 修正错误消息
            };
        }
    },
    BATHROOM_SCALE(4){
        @Override
        public String getParameterName(String operation) {
            return switch (operation.toLowerCase()) {
                case "poweron" -> null;
                case "poweroff" -> null;
                default -> throw new InvalidParametersException("BATHROOM_SCALE不支持操作: " + operation); // 修正错误消息
            };
        }
    };

    private final int code;

    DeviceType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static DeviceType getType(int code) {
        for (DeviceType type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的设备类型代码: " + code);
    }

    public abstract String getParameterName(String operation);
}
