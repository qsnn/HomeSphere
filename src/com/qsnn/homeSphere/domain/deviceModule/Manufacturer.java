package com.qsnn.homeSphere.domain.deviceModule;

import java.util.Set;

/**
 * 设备制造商类
 *
 * <p>该类表示设备制造商信息，包含制造商名称和所支持的设备连接方式。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>存储制造商基本信息</li>
 *   <li>管理制造商支持的设备连接方式</li>
 *   <li>提供制造商信息的访问接口</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>所有字段均为final，确保对象不可变性</li>
 *   <li>支持多种连接方式的定义和管理</li>
 *   <li>简单的值对象设计，专注于数据存储</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class Manufacturer {

    /**
     * 制造商名称
     * <p>制造商的唯一标识名称，如："小米", "华为", "苹果"等</p>
     */
    protected final String name;

    /**
     * 所支持的连接方式集合
     * <p>包含该制造商设备支持的所有连接方式，如：WIFI、BLUETOOTH等</p>
     * <p>使用Set确保连接方式的唯一性</p>
     */
    protected final Set<Device.ConnectMode> supportedConnectMode;

    /**
     * 制造商构造函数
     *
     * <p>初始化制造商名称和支持的连接方式集合</p>
     *
     * @param name 制造商名称，不能为null或空
     * @param supportedConnectMode 支持的连接方式集合，不能为null
     * @throws IllegalArgumentException 如果名称为空或连接方式集合为null
     */
    public Manufacturer(String name, Set<Device.ConnectMode> supportedConnectMode) {
        // 参数校验（在实际应用中建议添加）
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("制造商名称不能为空");
        }
        if (supportedConnectMode == null) {
            throw new IllegalArgumentException("支持的连接方式集合不能为null");
        }

        this.name = name;
        this.supportedConnectMode = supportedConnectMode;
    }

    // ==================== Getter 方法 ====================

    /**
     * 获取制造商名称
     *
     * @return 制造商名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取支持的连接方式集合
     *
     * <p>返回制造商设备支持的所有连接方式</p>
     *
     * @return 支持的连接方式集合，不会返回null
     */
    public Set<Device.ConnectMode> getSupportedConnectMode() {
        return supportedConnectMode;
    }

    // ==================== 业务方法 ====================

    /**
     * 检查制造商是否支持指定的连接方式
     *
     * @param connectMode 要检查的连接方式
     * @return 如果支持该连接方式返回true，否则返回false
     */
    public boolean supportsConnectMode(Device.ConnectMode connectMode) {
        return supportedConnectMode.contains(connectMode);
    }

    // ==================== 重写方法 ====================

    /**
     * 返回制造商的字符串表示
     *
     * <p>格式：制造商名称[连接方式1, 连接方式2, ...]</p>
     * <p>示例：小米[WIFI, BLUETOOTH]</p>
     *
     * @return 格式化的制造商信息字符串
     */
    @Override
    public String toString() {
        return name + supportedConnectMode.toString();
    }

    /**
     * 比较两个制造商对象是否相等
     *
     * <p>基于制造商名称进行比较，名称相同的制造商视为相等</p>
     *
     * @param obj 要比较的对象
     * @return 如果制造商名称相同返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Manufacturer that = (Manufacturer) obj;
        return name.equals(that.name);
    }

}