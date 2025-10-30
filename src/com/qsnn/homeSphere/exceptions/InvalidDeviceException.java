package com.qsnn.homeSphere.exceptions;

/**
 * 无效设备异常类
 *
 * <p>表示在设备管理操作中遇到的无效设备相关错误。</p>
 *
 * <p><b>触发场景：</b></b></p>
 * <ul>
 *   <li>创建设备时设备名称为空</li>
 *   <li>根据ID查询设备时设备不存在</li>
 *   <li>删除设备时设备不存在</li>
 *   <li>设备操作时提供的设备ID无效</li>
 *   <li>场景配置中引用了不存在的设备</li>
 *   <li>设备状态操作失败</li>
 * </ul>
 *
 * <p><b>继承关系：</b></p>
 * <ul>
 *   <li>继承自 {@link IllegalArgumentException}</li>
 *   <li>属于运行时异常，不需要显式捕获</li>
 * </ul>
 *
 * <p><b>使用示例：</b></p>
 * <pre>
 * // 在设备操作时抛出异常
 * if (deviceName == null || deviceName.trim().isEmpty()) {
 *     throw new InvalidDeviceException("添加失败：设备名称不能为空！");
 * }
 * </pre>
 *
 * @author qsnn
 * @version 2.0
 * @since 2025
 * @see IllegalArgumentException
 */
public class InvalidDeviceException extends IllegalArgumentException {

    /**
     * 默认构造函数
     *
     * <p>使用默认错误消息"设备信息错误！"创建异常实例。</p>
     */
    public InvalidDeviceException() {
        super("设备信息错误！");
    }

    /**
     * 带自定义消息的构造函数
     *
     * <p>使用指定的错误消息创建异常实例。</p>
     *
     * @param message 自定义的错误消息，用于更详细地描述异常原因
     *
     * <pre>
     * // 抛出带自定义消息的异常
     * throw new InvalidDeviceException("设备不存在，ID：" + deviceId);
     * </pre>
     */
    public InvalidDeviceException(String message) {
        super(message);
    }
}