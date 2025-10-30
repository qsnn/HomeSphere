package com.qsnn.homeSphere.exceptions;

/**
 * 无效用户异常类
 *
 * <p>表示在用户管理操作中遇到的无效用户相关错误。</p>
 *
 * <p><b>触发场景：</b></p>
 * <ul>
 *   <li>用户登录时用户名或密码错误</li>
 *   <li>用户注册时用户名已存在</li>
 *   <li>用户注册时信息不完整（用户名、密码、邮箱为空）</li>
 *   <li>删除用户时用户不存在</li>
 *   <li>删除管理员用户或当前登录用户</li>
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
 * // 在用户登录验证时抛出异常
 * if (!user.getPassword().equals(inputPassword)) {
 *     throw new InvalidUserException("登录失败：密码错误！");
 * }
 * </pre>
 *
 * @author qsnn
 * @version 2.0
 * @since 2025
 * @see IllegalArgumentException
 */
public class InvalidUserException extends IllegalArgumentException{

    /**
     * 默认构造函数
     *
     * <p>使用默认错误消息"用户信息错误！"创建异常实例。</p>
     */
    public InvalidUserException() {
        super("用户信息错误！");
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
     * throw new InvalidUserException("注册失败：用户名已存在！");
     * </pre>
     */
    public InvalidUserException(String message) {
        super(message);
    }
}