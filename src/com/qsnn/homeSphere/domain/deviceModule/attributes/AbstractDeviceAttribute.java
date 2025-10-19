package com.qsnn.homeSphere.domain.deviceModule.attributes;

/**
 * 设备属性抽象基类
 *
 * <p>该类是所有设备属性的抽象基类，实现了设备属性的通用功能和基本行为。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>管理设备属性的基本信息（名称、当前值、默认值）</li>
 *   <li>提供属性值的设置和获取方法</li>
 *   <li>支持属性值的验证和重置功能</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>使用泛型支持不同类型的属性值</li>
 *   <li>属性名称为final，确保唯一性和不变性</li>
 *   <li>提供默认值机制，支持属性重置</li>
 *   <li>通过抽象方法实现具体属性的验证逻辑</li>
 * </ul>
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public abstract class AbstractDeviceAttribute<T> implements DeviceAttribute<T> {
    /** 属性名称 */
    private final String name;

    /** 当前属性值 */
    protected T value;

    /** 默认属性值 */
    protected final T defaultValue;

    /**
     * 设备属性构造函数
     *
     * <p>初始化属性名称和默认值，并将当前值设置为默认值</p>
     *
     * @param name 属性名称
     * @param defaultValue 默认属性值
     */
    public AbstractDeviceAttribute(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    // ========== 基本Getter/Setter ==========

    /**
     * 获取属性名称
     *
     * @return 属性名称
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * 获取当前属性值
     *
     * @return 当前属性值
     */
    @Override
    public T getValue() {
        return value;
    }

    /**
     * 设置属性值
     *
     * <p>在设置值之前会进行验证，只有通过验证的值才会被设置</p>
     *
     * @param value 要设置的属性值
     * @return 如果设置成功返回true，否则返回false
     */
    @Override
    public boolean setValue(T value) {
        if (validate(value)) {
            this.value = value;
            return true;
        }
        return false;
    }

    /**
     * 重置为默认值
     *
     * <p>将当前属性值重置为构造函数中设置的默认值</p>
     */
    public void resetToDefault() {
        this.value = defaultValue;
    }

    /**
     * 获取默认值
     *
     * @return 默认属性值
     */
    public T getDefaultValue() {
        return defaultValue;
    }

    // ==================== 重写方法 ====================

    /**
     * 返回属性的格式化字符串表示
     *
     * <p>格式：属性名:属性值</p>
     * <p>示例：temperature:25.5</p>
     *
     * @return 格式化的属性信息字符串
     */
    @Override
    public String toString(){
        return name + ":" + value;
    }

    /**
     * 比较两个设备属性对象是否相等
     *
     * <p>基于属性名称进行比较，因为属性名称是唯一标识符</p>
     *
     * @param obj 要比较的对象
     * @return 如果属性名称相同返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        AbstractDeviceAttribute<?> that = (AbstractDeviceAttribute<?>) obj;
        return name.equals(that.name);
    }
}