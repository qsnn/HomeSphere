package qsnn.homeSphere.domain.automationScene;

import qsnn.homeSphere.domain.deviceModule.Device;

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

    }
}