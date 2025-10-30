package com.qsnn.homeSphere.domain.deviceModule.devices;

import com.qsnn.homeSphere.exceptions.InvalidParametersException;

/**
 * 设备类型枚举
 *
 * <p>定义智能家居系统中支持的设备类型及其操作特性。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>定义系统支持的设备类型</li>
 *   <li>管理每种设备类型支持的操作命令</li>
 *   <li>验证设备操作参数的合法性</li>
 *   <li>提供设备操作的用户友好提示</li>
 * </ul>
 *
 * <p><b>设备类型：</b></p>
 * <ul>
 *   <li>空调 - 支持温度调节和开关控制</li>
 *   <li>智能灯泡 - 支持亮度、色温调节和开关控制</li>
 *   <li>智能门锁 - 支持锁定、解锁和开关控制</li>
 *   <li>智能体重秤 - 支持基本开关控制</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public enum DeviceType {
    /**
     * 空调设备
     * <p>支持温度调节功能，温度范围：16.0-32.0°C</p>
     */
    AIR_CONDITIONER(1){
        /**
         * 获取空调设备操作的参数名称
         *
         * @param operation 操作命令
         * @return 参数名称，如果操作不需要参数则返回null
         * @throws InvalidParametersException 当操作不被支持时抛出异常
         */
        @Override
        public String getParameterName(String operation) {
            return switch (operation.toLowerCase()) {
                case "poweron" -> null;
                case "poweroff" -> null;
                case "settemperature" -> "目标温度(16.0-32.0°C)";
                default -> throw new InvalidParametersException("AIR_CONDITIONER不支持操作: " + operation);
            };
        }

        /**
         * 获取空调设备支持的操作列表
         *
         * @return 支持的操作命令字符串
         */
        @Override
        public String getSupportedOperations() {
            return "powerOn, powerOff, setTemperature";
        }
    },

    /**
     * 智能灯泡设备
     * <p>支持亮度和色温调节功能</p>
     */
    LIGHT_BULB(2){
        /**
         * 获取智能灯泡设备操作的参数名称
         *
         * @param operation 操作命令
         * @return 参数名称，如果操作不需要参数则返回null
         * @throws InvalidParametersException 当操作不被支持时抛出异常
         */
        @Override
        public String getParameterName(String operation) {
            return switch (operation.toLowerCase()) {
                case "poweron" -> null;
                case "poweroff" -> null;
                case "setbrightness" -> "亮度值(0-100%)";
                case "setcolortemp" -> "色温值(2300-2700K)";
                default -> throw new InvalidParametersException("LIGHT_BULB不支持操作: " + operation);
            };
        }

        /**
         * 获取智能灯泡设备支持的操作列表
         *
         * @return 支持的操作命令字符串
         */
        @Override
        public String getSupportedOperations() {
            return "powerOn, powerOff, setBrightness, setColorTemp";
        }
    },

    /**
     * 智能门锁设备
     * <p>支持锁定和解锁功能</p>
     */
    SMART_LOCK(3){
        /**
         * 获取智能门锁设备操作的参数名称
         *
         * @param operation 操作命令
         * @return 参数名称，如果操作不需要参数则返回null
         * @throws InvalidParametersException 当操作不被支持时抛出异常
         */
        @Override
        public String getParameterName(String operation) {
            return switch (operation.toLowerCase()) {
                case "poweron" -> null;
                case "poweroff" -> null;
                case "lock" -> null;
                case "unlock" -> null;
                default -> throw new InvalidParametersException("SMART_LOCK不支持操作: " + operation);
            };
        }

        /**
         * 获取智能门锁设备支持的操作列表
         *
         * @return 支持的操作命令字符串
         */
        @Override
        public String getSupportedOperations() {
            return "powerOn, powerOff, lock, unlock";
        }
    },

    /**
     * 智能体重秤设备
     * <p>支持基本开关控制功能</p>
     */
    BATHROOM_SCALE(4){
        /**
         * 获取智能体重秤设备操作的参数名称
         *
         * @param operation 操作命令
         * @return 参数名称，如果操作不需要参数则返回null
         * @throws InvalidParametersException 当操作不被支持时抛出异常
         */
        @Override
        public String getParameterName(String operation) {
            return switch (operation.toLowerCase()) {
                case "poweron" -> null;
                case "poweroff" -> null;
                default -> throw new InvalidParametersException("BATHROOM_SCALE不支持操作: " + operation);
            };
        }

        /**
         * 获取智能体重秤设备支持的操作列表
         *
         * @return 支持的操作命令字符串
         */
        @Override
        public String getSupportedOperations() {
            return "powerOn, powerOff";
        }
    };

    /** 设备类型代码 */
    private final int code;

    /**
     * 设备类型构造函数
     *
     * @param code 设备类型代码
     */
    DeviceType(int code) {
        this.code = code;
    }

    /**
     * 获取设备类型代码
     *
     * @return 设备类型代码
     */
    public int getCode() {
        return code;
    }

    /**
     * 根据代码获取设备类型
     *
     * @param code 设备类型代码
     * @return 对应的设备类型枚举值
     * @throws IllegalArgumentException 当代码无效时抛出异常
     */
    public static DeviceType getType(int code) {
        for (DeviceType type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的设备类型代码: " + code);
    }

    /**
     * 获取设备操作的参数名称
     *
     * @param operation 操作命令
     * @return 参数名称，如果操作不需要参数则返回null
     * @throws InvalidParametersException 当操作不被支持时抛出异常
     */
    public abstract String getParameterName(String operation);

    /**
     * 获取设备支持的操作列表
     *
     * @return 支持的操作命令字符串
     */
    public abstract String getSupportedOperations();
}