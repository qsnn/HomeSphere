package com.qsnn.homeSphere.domain.deviceModule.attributes;

import java.util.Collections;
import java.util.Set;

/**
 * 布尔类型设备属性类
 *
 * <p>该类用于表示布尔类型的设备属性，如开关状态、使能标志等。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>管理布尔类型的设备属性值</li>
 *   <li>验证输入值是否为合法的布尔类型</li>
 *   <li>提供布尔属性的基本操作</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>继承自AbstractDeviceAttribute抽象基类</li>
 *   <li>专门处理布尔类型的数据验证</li>
 *   <li>支持默认值的设置和重置</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class BooleanAttribute extends AbstractDeviceAttribute<Boolean> {

    /**
     * 布尔属性构造函数
     *
     * <p>初始化布尔属性的名称和默认值</p>
     *
     * @param name 属性名称
     * @param defaultValue 默认布尔值
     */
    public BooleanAttribute(String name, Boolean defaultValue) {
        super(name, defaultValue);
    }

    /**
     * 检查属性值是否合法
     *
     * <p>验证输入值是否为Boolean类型</p>
     *
     * @param value 要验证的属性值
     * @return 如果值是Boolean类型返回true，否则返回false
     */
    @Override
    public boolean validate(Object value) {
        return value instanceof Boolean;
    }

}