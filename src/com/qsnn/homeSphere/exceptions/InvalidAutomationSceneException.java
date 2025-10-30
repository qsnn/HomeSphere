package com.qsnn.homeSphere.exceptions;

/**
 * 无效自动化场景异常类
 *
 * <p>表示在自动化场景管理操作中遇到的无效场景相关错误。</p>
 *
 * <p><b>触发场景：</b></p>
 * <ul>
 *   <li>创建场景时场景名称为空</li>
 *   <li>根据ID查询场景时场景不存在</li>
 *   <li>触发场景时场景不存在</li>
 *   <li>场景操作时提供的场景ID无效</li>
 *   <li>场景配置信息不完整或格式错误</li>
 *   <li>场景操作执行过程中出现错误</li>
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
 * // 在场景操作时抛出异常
 * if (sceneName == null || sceneName.trim().isEmpty()) {
 *     throw new InvalidAutomationSceneException("创建失败：场景名称不能为空！");
 * }
 * </pre>
 *
 * @author qsnn
 * @version 2.0
 * @since 2025
 * @see IllegalArgumentException
 */
public class InvalidAutomationSceneException extends IllegalArgumentException {

    /**
     * 默认构造函数
     *
     * <p>使用默认错误消息"智能场景信息错误！"创建异常实例。</p>
     */
    public InvalidAutomationSceneException() {
        super("智能场景信息错误！");
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
     * throw new InvalidAutomationSceneException("场景不存在，ID：" + sceneId);
     * </pre>
     */
    public InvalidAutomationSceneException(String message) {
        super(message);
    }
}