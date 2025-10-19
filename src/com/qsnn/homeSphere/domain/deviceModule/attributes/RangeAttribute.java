package com.qsnn.homeSphere.domain.deviceModule.attributes;

/**
 * 范围类型设备属性类
 *
 * <p>该类用于表示具有数值范围的设备属性，如温度、亮度、音量等需要在最小值和最大值之间取值的属性。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>管理具有数值范围的设备属性值</li>
 *   <li>验证输入值是否在指定的最小值和最大值范围内</li>
 *   <li>支持单位的定义和显示</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>继承自AbstractDeviceAttribute抽象基类</li>
 *   <li>支持最小值和最大值的范围限制</li>
 *   <li>提供单位信息的存储和显示</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public class RangeAttribute extends AbstractDeviceAttribute<Integer> {
    /** 属性最小值 */
    private final int min;

    /** 属性最大值 */
    private final int max;

    /** 属性单位 */
    private final String unit;

    /**
     * 范围属性构造函数
     *
     * <p>初始化范围属性的名称、范围限制、默认值和单位</p>
     *
     * @param name 属性名称
     * @param min 最小值
     * @param max 最大值
     * @param defaultValue 默认值
     * @param unit 单位描述
     */
    public RangeAttribute(String name, Integer min, Integer max, Integer defaultValue, String unit) {
        super(name, defaultValue);
        this.min = min;
        this.max = max;
        this.value = defaultValue;
        this.unit = unit;
    }

    /**
     * 检查属性值是否合法
     *
     * <p>验证输入值是否为Integer类型且在指定的最小值和最大值范围内</p>
     *
     * @param value 要验证的属性值
     * @return 如果值是Integer类型且在范围内返回true，否则返回false
     */
    @Override
    public boolean validate(Object value) {
        if (value instanceof Integer) {
            int intValue = (Integer) value;
            return intValue >= min && intValue <= max;
        }
        return false;
    }

    // ==================== Getter 方法 ====================

    /**
     * 获取属性最小值
     *
     * @return 最小值
     */
    public int getMin() {
        return min;
    }

    /**
     * 获取属性最大值
     *
     * @return 最大值
     */
    public int getMax() {
        return max;
    }

    /**
     * 获取属性单位
     *
     * @return 单位描述
     */
    public String getUnit() {
        return unit;
    }

    // ==================== 重写方法 ====================

    /**
     * 返回范围属性的格式化字符串表示
     *
     * <p>格式：属性名:属性值 单位 [min-max]</p>
     * <p>示例：temperature:25 ℃ [16-30]</p>
     *
     * @return 格式化的属性信息字符串
     */
    @Override
    public String toString() {
        return getName() + ":" + getValue() + " " + unit + " [" + min + "-" + max + "]";
    }

}