package com.qsnn.homeSphere.exceptions;

/**
 * 无效参数异常类
 *
 * <p>表示在设备操作和场景配置中遇到的无效参数相关错误。</p>
 *
 * <p><b>触发场景：</b></p>
 * <ul>
 *   <li>设备操作参数格式错误（非数字、超出范围等）</li>
 *   <li>场景操作参数验证失败</li>
 *   <li>空调温度设置超出16.0-32.0度范围</li>
 *   <li>灯泡亮度设置超出0-100范围</li>
 *   <li>灯泡色温设置超出2300K-7000K范围</li>
 *   <li>不需要参数的操作提供了参数</li>
 *   <li>需要参数的操作缺少参数</li>
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
 * // 在参数验证时抛出异常
 * if (temperature < 16.0 || temperature > 32.0) {
 *     throw new InvalidParametersException("空调温度设置应在16.0-32.0度之间");
 * }
 * </pre>
 *
 * @author qsnn
 * @version 2.0
 * @since 2025
 * @see IllegalArgumentException
 */
public class InvalidParametersException extends IllegalArgumentException {

    /**
     * 默认构造函数
     *
     * <p>使用默认错误消息"属性信息错误！"创建异常实例。</p>
     */
    public InvalidParametersException() {
        super("属性信息错误！");
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
     * throw new InvalidParametersException("setbrightness操作需要亮度参数");
     * </pre>
     */
    public InvalidParametersException(String message) {
        super(message);
    }
}