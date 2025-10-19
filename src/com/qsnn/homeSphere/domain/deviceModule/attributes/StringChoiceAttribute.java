package com.qsnn.homeSphere.domain.deviceModule.attributes;

import java.util.*;

/**
 * 字符串选择类型设备属性类
 *
 * <p>该类用于表示具有预定义选项的字符串类型设备属性，如模式选择、状态设置等只能从特定选项中选择的属性。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>管理具有预定义选项的字符串属性值</li>
 *   <li>验证输入值是否为合法的预定义选项</li>
 *   <li>提供选项集合的访问和管理</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>继承自AbstractDeviceAttribute抽象基类</li>
 *   <li>使用Set存储允许的选项值，确保唯一性</li>
 *   <li>在构造时验证默认值的合法性</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class StringChoiceAttribute extends AbstractDeviceAttribute<String> {
    /** 允许的属性值集合 */
    private final Set<String> allowedValues;

    /**
     * 字符串选择属性构造函数
     *
     * <p>初始化字符串选择属性的名称、默认值和允许的选项集合</p>
     *
     * @param name 属性名称
     * @param defaultValue 默认值
     * @param allowedValues 允许的选项值数组
     * @throws IllegalArgumentException 如果默认值不在允许值范围内
     */
    public StringChoiceAttribute(String name, String defaultValue, String... allowedValues) {
        super(name, defaultValue);
        this.allowedValues = new HashSet<>(Arrays.asList(allowedValues));
        // 确保默认值在允许值中
        if (!this.allowedValues.contains(defaultValue)) {
            throw new IllegalArgumentException("默认值不在允许值范围内");
        }
    }

    /**
     * 检查属性值是否合法
     *
     * <p>验证输入值是否为String类型且在允许的选项集合中</p>
     *
     * @param value 要验证的属性值
     * @return 如果值是String类型且在允许值集合中返回true，否则返回false
     */
    @Override
    public boolean validate(Object value) {
        return value instanceof String && allowedValues.contains(value);
    }

    // ==================== Getter 方法 ====================

    /**
     * 获取合法属性值集合
     *
     * <p>返回不可修改的允许值集合视图</p>
     *
     * @return 允许的属性值集合
     */
    public Set<String> getAllowedValues() {
        return Collections.unmodifiableSet(allowedValues);
    }

    // ==================== 业务方法 ====================

    /**
     * 检查是否包含指定的选项值
     *
     * @param value 要检查的选项值
     * @return 如果包含该选项值返回true，否则返回false
     */
    public boolean containsValue(String value) {
        return allowedValues.contains(value);
    }

    /**
     * 获取允许值的数量
     *
     * @return 允许值的数量
     */
    public int getAllowedValuesCount() {
        return allowedValues.size();
    }

    // ==================== 重写方法 ====================

    /**
     * 返回字符串选择属性的格式化字符串表示
     *
     * <p>格式：属性名:当前值 [选项1, 选项2, ...]</p>
     * <p>示例：mode:cooling [cooling, heating, auto, fan]</p>
     *
     * @return 格式化的属性信息字符串
     */
    @Override
    public String toString() {
        return getName() + ":" + getValue() + " " + allowedValues.toString();
    }

}