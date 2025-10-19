package com.qsnn.homeSphere.domain.deviceModule.attributes;

public abstract class AbstractDeviceAttribute<T> implements DeviceAttribute<T> {
    //属性名
    private final String name;

    //当前属性值
    protected T value;

    //默认属性值
    protected final T defaultValue;

    public AbstractDeviceAttribute(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    //========== 基本Getter/Setter ==========
    @Override
    public String getName() {
        return name;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public boolean setValue(T value) {
        if (validate(value)) {
            this.value = value;
            return true;
        }
        return false;
    }

    // 重置为默认值
    public void resetToDefault() {
        this.value = defaultValue;
    }

    //获取默认值
    public T getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String toString(){
        return name + ":" + value;
    }

}