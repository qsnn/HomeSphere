package com.qsnn.homeSphere.exceptions;

/**
 * 无效房间异常类
 *
 * <p>表示在房间管理操作中遇到的无效房间相关错误。</p>
 *
 * <p><b>触发场景：</b></p>
 * <ul>
 *   <li>创建房间时房间名称为空</li>
 *   <li>根据ID查询房间时房间不存在</li>
 *   <li>在不存在房间中添加设备</li>
 *   <li>房间操作时提供的房间ID无效</li>
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
 * // 在房间操作时抛出异常
 * if (roomName == null || roomName.trim().isEmpty()) {
 *     throw new InvalidRoomException("添加失败：房间名称不能为空！");
 * }
 * </pre>
 *
 * @author qsnn
 * @version 2.0
 * @since 2025
 * @see IllegalArgumentException
 */
public class InvalidRoomException extends IllegalArgumentException {

    /**
     * 默认构造函数
     *
     * <p>使用默认错误消息"房间信息错误！"创建异常实例。</p>
     */
    public InvalidRoomException() {
        super("房间信息错误！");
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
     * throw new InvalidRoomException("房间不存在，ID：" + roomId);
     * </pre>
     */
    public InvalidRoomException(String message) {
        super(message);
    }
}