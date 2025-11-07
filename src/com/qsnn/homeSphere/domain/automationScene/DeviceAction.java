package com.qsnn.homeSphere.domain.automationScene;

import com.qsnn.homeSphere.domain.deviceModule.Device;
import com.qsnn.homeSphere.domain.deviceModule.devices.AirConditioner;
import com.qsnn.homeSphere.domain.deviceModule.devices.BathroomScale;
import com.qsnn.homeSphere.domain.deviceModule.devices.LightBulb;
import com.qsnn.homeSphere.domain.deviceModule.devices.SmartLock;
import com.qsnn.homeSphere.exceptions.InvalidDeviceException;
import com.qsnn.homeSphere.exceptions.InvalidParametersException;

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
            throw new InvalidDeviceException("未设置目标设备");
        }

        if (command == null || command.trim().isEmpty()) {
            throw new InvalidParametersException("未指定操作命令");
        }
//        System.out.print(device.getName() + " ");

        // 根据设备类型执行相应的操作
        if (device instanceof LightBulb) {
            executeLightBulbAction((LightBulb) device);
        } else if (device instanceof AirConditioner) {
            executeAirConditionerAction((AirConditioner) device);
        } else if (device instanceof SmartLock) {
            executeSmartLockAction((SmartLock) device);
        } else if (device instanceof BathroomScale) {
            executeBathroomScaleAction((BathroomScale) device);
        } else {
            throw new InvalidDeviceException("不支持的设备类型: " + device.getClass().getSimpleName());
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
                lightBulb.powerOn();
                //System.out.println("powered on");
                break;

            case "poweroff":
                lightBulb.powerOff();
                //System.out.println("powered off");
                break;

            case "setbrightness":
                int brightness = Integer.parseInt(parameters.trim());
                lightBulb.setBrightness(brightness);
                //System.out.println("target brightness set to " + brightness + "%");
                break;

            case "setcolortemp":
                int colorTemp = Integer.parseInt(parameters.trim());
                lightBulb.setColorTemp(colorTemp);
                //System.out.println("target colorTemp set to " + colorTemp + "K");
                break;

            default:
                throw new InvalidParametersException("不支持的灯泡命令: " + command);
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
                airConditioner.powerOn();
                //System.out.println("powered on");
                break;

            case "poweroff":
                airConditioner.powerOff();
                //System.out.println("powered off");
                break;

            case "settemperature":
                double temperature = Double.parseDouble(parameters.trim());
                airConditioner.setTargetTemp(temperature);
                //System.out.println("target temperature set to " + temperature + "°C");
                break;

            default:
                throw new InvalidParametersException("不支持的空调命令: " + command);
        }
    }

    /**
     * 执行智能门锁操作
     *
     * @param smartLock 目标门锁设备
     */
    private void executeSmartLockAction(SmartLock smartLock) {
        switch (command.toLowerCase()) {
            case "poweron":
                smartLock.setLocked(false);
                //System.out.println("powered on");
                break;

            case "poweroff":
                smartLock.setLocked(true);
                //System.out.println("powered off");
                break;

            case "lock":
                smartLock.setLocked(true);
                //System.out.println("locked");
                break;

            case "unlock":
                smartLock.setLocked(false);
                //System.out.println("unlocked");
                break;

            default:
                throw new InvalidParametersException("不支持的智能门锁命令: " + command);
        }
    }

    /**
     * 执行体重秤操作
     *
     * @param bathroomScale 目标体重秤设备
     */
    private void executeBathroomScaleAction(BathroomScale bathroomScale) {
        switch (command.toLowerCase()) {
            case "poweron":
                bathroomScale.powerOn();
                //System.out.println("powered on");
                break;

            case "poweroff":
                bathroomScale.powerOff();
                //System.out.println("powered off");
                break;

            default:
                throw new InvalidParametersException("不支持的体重秤命令: " + command);
        }
    }

    /**
     * 返回对象的字符串表示形式
     * 格式：Executing command: [命令] with parameters: [参数]
     *
     * @return 格式化的字符串
     */
    @Override
    public String toString() {
        return "Executing command: " + command + " with parameters: " + parameters;
    }
}