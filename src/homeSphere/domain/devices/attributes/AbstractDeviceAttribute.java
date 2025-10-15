package homeSphere.domain.devices.attributes;

import homeSphere.domain.devices.DeviceAttribute;

public abstract class AbstractDeviceAttribute<T> implements DeviceAttribute<T> {
    private final String name;
    protected T value;
    protected final T defaultValue;

    public AbstractDeviceAttribute(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

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

    public T getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String toString(){
        return name + ":" + value;
    }

}