package homeSphere.domain.devices;

public interface DeviceAttribute<T> {
    T getValue();
    boolean setValue(T value);
    Class<T> getValueType();
    boolean validate(Object value);
}
