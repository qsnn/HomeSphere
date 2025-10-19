package com.qsnn.homeSphere.domain.automationScene;

import com.qsnn.homeSphere.domain.deviceModule.Device;
import com.qsnn.homeSphere.domain.deviceModule.devices.AirConditioner;
import com.qsnn.homeSphere.domain.deviceModule.devices.BathroomScale;
import com.qsnn.homeSphere.domain.deviceModule.devices.LightBulb;
import com.qsnn.homeSphere.domain.deviceModule.devices.SmartLock;

/**
 * 设备操作类
 *
 * <p>该类表示自动化场景中对设备的单个操作指令，包含命令、参数和目标设备信息。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>封装设备操作命令和参数</li>
 *   <li>关联目标设备对象</li>
 *   <li>执行具体的设备操作</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>支持灵活的命令和参数配置</li>
 *   <li>与具体设备对象关联</li>
 *   <li>提供统一的执行接口</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class DeviceAction {

    /** 操作命令 */
    private String command;

    /** 操作参数 */
    private String parameters;

    /** 目标设备 */
    private Device device;

    /**
     * 默认构造函数
     */
    public DeviceAction() {
    }

    /**
     * 设备操作构造函数
     *
     * @param command 操作命令
     * @param parameters 操作参数
     * @param device 目标设备
     */
    public DeviceAction(String command, String parameters, Device device) {
        this.command = command;
        this.parameters = parameters;
        this.device = device;
    }

    /**
     * 获取操作命令
     *
     * @return 操作命令字符串
     */
    public String getCommand() {
        return command;
    }

    /**
     * 设置操作命令
     *
     * @param command 新的操作命令
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * 获取操作参数
     *
     * @return 操作参数字符串
     */
    public String getParameters() {
        return parameters;
    }

    /**
     * 设置操作参数
     *
     * @param parameters 新的操作参数
     */
    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    /**
     * 获取目标设备
     *
     * @return 目标设备对象
     */
    public Device getDevice() {
        return device;
    }

    /**
     * 设置目标设备
     *
     * @param device 新的目标设备
     */
    public void setDevice(Device device) {
        this.device = device;
    }

    /**
     * 执行设备操作
     *
     * <p>根据命令和参数执行具体的设备操作</p>
     */
    public void execute() {
        if (device == null) {
            System.err.println("错误：未设置目标设备");
            return;
        }

        if (command == null || command.trim().isEmpty()) {
            System.err.println("错误：未指定操作命令");
            return;
        }

        try {
            // 根据设备类型和命令执行相应的操作
            if (device instanceof LightBulb) {
                executeLightBulbAction((LightBulb) device);
            } else if (device instanceof AirConditioner) {
                executeAirConditionerAction((AirConditioner) device);
            } else if (device instanceof SmartLock) {
                executeSmartLockAction((SmartLock) device);
            } else if (device instanceof BathroomScale) {
                executeBathroomScaleAction((BathroomScale) device);
            } else {
                System.err.println("错误：不支持的设备类型 - " + device.getClass().getSimpleName());
            }
        } catch (Exception e) {
            System.err.println("执行设备操作时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * 执行智能灯泡操作
     *
     * @param lightBulb 目标灯泡设备
     */
    private void executeLightBulbAction(LightBulb lightBulb) {
        switch (command.toLowerCase()) {
            case "poweron":
                lightBulb.setBrightness(100); // 默认全亮
                System.out.println("灯泡已开启，亮度设置为100%");
                break;

            case "poweroff":
                lightBulb.setBrightness(0);
                System.out.println("灯泡已关闭");
                break;

            case "setbrightness":
                if (!parameters.isEmpty()) {
                    try {
                        int brightness = Integer.parseInt(parameters);
                        if (brightness >= 0 && brightness <= 100) {
                            lightBulb.setBrightness(brightness);
                            System.out.println("灯泡亮度设置为: " + brightness + "%");
                        } else {
                            System.err.println("错误：亮度值必须在0-100之间");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("错误：无效的亮度参数 - " + parameters);
                    }
                }
                break;

            case "setcolortemp":
                if (!parameters.isEmpty()) {
                    try {
                        int colorTemp = Integer.parseInt(parameters);
                        lightBulb.setColorTemp(colorTemp);
                        System.out.println("灯泡色温设置为: " + colorTemp + "K");
                    } catch (NumberFormatException e) {
                        System.err.println("错误：无效的色温参数 - " + parameters);
                    }
                }
                break;

            default:
                System.err.println("错误：不支持的灯泡命令 - " + command);
        }
    }

    /**
     * 执行空调操作
     *
     * @param airConditioner 目标空调设备
     */
    private void executeAirConditionerAction(AirConditioner airConditioner) {
        switch (command.toLowerCase()) {
            case "poweron":
            case "turnon":
                // 空调开启时设置一个默认目标温度
                airConditioner.setTargetTemp(24.0);
                System.out.println("空调已开启，目标温度设置为24°C");
                break;

            case "poweroff":
            case "turnoff":
                airConditioner.setTargetTemp(airConditioner.getCurrTemp()); // 关闭时保持当前温度
                System.out.println("空调已关闭");
                break;

            case "settemperature":
                if (!parameters.isEmpty()) {
                    try {
                        double temperature = Double.parseDouble(parameters);
                        airConditioner.setTargetTemp(temperature);
                        System.out.println("空调目标温度设置为: " + temperature + "°C");
                    } catch (NumberFormatException e) {
                        System.err.println("错误：无效的温度参数 - " + parameters);
                    }
                }
                break;

            case "setcurrtemp":
                if (!parameters.isEmpty()) {
                    try {
                        double currentTemp = Double.parseDouble(parameters);
                        airConditioner.setCurrTemp(currentTemp);
                        System.out.println("当前温度更新为: " + currentTemp + "°C");
                    } catch (NumberFormatException e) {
                        System.err.println("错误：无效的当前温度参数 - " + parameters);
                    }
                }
                break;

            default:
                System.err.println("错误：不支持的空调命令 - " + command);
        }
    }

    /**
     * 执行智能门锁操作
     *
     * @param smartLock 目标门锁设备
     */
    private void executeSmartLockAction(SmartLock smartLock) {
        switch (command.toLowerCase()) {
            case "lock":
                smartLock.setLocked(true);
                System.out.println("门锁已锁定");
                break;

            case "unlock":
                smartLock.setLocked(false);
                System.out.println("门锁已解锁");
                break;

            case "togglelock":
                boolean currentState = smartLock.isLocked();
                smartLock.setLocked(!currentState);
                System.out.println("门锁状态切换为: " + (currentState ? "解锁" : "锁定"));
                break;

            case "setbatterylevel":
                if (!parameters.isEmpty()) {
                    try {
                        int batteryLevel = Integer.parseInt(parameters);
                        if (batteryLevel >= 0 && batteryLevel <= 100) {
                            smartLock.setBatteryLevel(batteryLevel);
                            System.out.println("门锁电池电量设置为: " + batteryLevel + "%");
                        } else {
                            System.err.println("错误：电池电量必须在0-100之间");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("错误：无效的电池电量参数 - " + parameters);
                    }
                }
                break;

            default:
                System.err.println("错误：不支持的智能门锁命令 - " + command);
        }
    }

    /**
     * 执行体重秤操作
     *
     * @param bathroomScale 目标体重秤设备
     */
    private void executeBathroomScaleAction(BathroomScale bathroomScale) {
        switch (command.toLowerCase()) {
            case "setbodymass":
                if (!parameters.isEmpty()) {
                    try {
                        double bodyMass = Double.parseDouble(parameters);
                        if (bodyMass >= 0) {
                            bathroomScale.setBodyMass(bodyMass);
                            System.out.println("体重数据设置为: " + bodyMass + "kg");
                        } else {
                            System.err.println("错误：体重值不能为负数");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("错误：无效的体重参数 - " + parameters);
                    }
                }
                break;

            case "setbatterylevel":
                if (!parameters.isEmpty()) {
                    try {
                        int batteryLevel = Integer.parseInt(parameters);
                        if (batteryLevel >= 0 && batteryLevel <= 100) {
                            bathroomScale.setBatteryLevel(batteryLevel);
                            System.out.println("体重秤电池电量设置为: " + batteryLevel + "%");
                        } else {
                            System.err.println("错误：电池电量必须在0-100之间");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("错误：无效的电池电量参数 - " + parameters);
                    }
                }
                break;

            default:
                System.err.println("错误：不支持的体重秤命令 - " + command);
        }
    }
}