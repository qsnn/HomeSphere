package com.qsnn.homeSphere.domain.deviceModule.attributes;

public interface DeviceAttribute<T> {
    String getName();
    T getValue();
    boolean setValue(T value);
    boolean validate(Object value);
}
