package com.qsnn.homeSphere.domain.deviceModule.attributes;

/**
 * 设备属性接口
 *
 * <p>该接口定义了设备属性的基本操作规范，所有设备属性类都应实现此接口。</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>定义设备属性的基本操作方法</li>
 *   <li>提供属性值的类型安全访问</li>
 *   <li>支持属性值的验证机制</li>
 * </ul>
 *
 * <p><b>设计特点：</b></p>
 * <ul>
 *   <li>使用泛型支持不同类型的属性值</li>
 *   <li>提供统一的属性管理接口</li>
 *   <li>支持属性值的验证和设置</li>
 * </ul>
 *
 * @param <T> 属性值的类型
 *
 * @author qsnn
 * @version 1.0
 * @since 2025
 */
public interface DeviceAttribute<T> {

    /**
     * 获取属性名称
     *
     * @return 属性名称
     */
    String getName();

    /**
     * 获取当前属性值
     *
     * @return 当前属性值
     */
    T getValue();

    /**
     * 设置属性值
     *
     * <p>在设置值之前应进行验证，确保值的合法性</p>
     *
     * @param value 要设置的属性值
     * @return 如果设置成功返回true，否则返回false
     */
    boolean setValue(T value);

    /**
     * 验证属性值是否合法
     *
     * <p>检查输入值是否符合该属性的类型和业务规则要求</p>
     *
     * @param value 要验证的属性值
     * @return 如果值合法返回true，否则返回false
     */
    boolean validate(Object value);
}