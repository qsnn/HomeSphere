package homeSphere.domain.deviceModule;

public interface DeviceAttribute<T> {
    String getName();
    T getValue();
    boolean setValue(T value);
    Class<T> getValueType();
    boolean validate(Object value);
}
