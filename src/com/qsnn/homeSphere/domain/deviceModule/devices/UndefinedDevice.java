package com.qsnn.homeSphere.domain.deviceModule.devices;

import com.qsnn.homeSphere.domain.deviceModule.Device;
import com.qsnn.homeSphere.domain.deviceModule.DeviceAttribute;
import com.qsnn.homeSphere.domain.deviceModule.Manufacturer;

import java.util.Set;

public class UndefinedDevice extends Device {
    public UndefinedDevice(Integer deviceID, String name, String OS, Manufacturer manufacturer, String brand, ConnectMode connectMode, PowerMode powerMode, double power, Set<DeviceAttribute<?>> attributes ) {
        super(deviceID, name, OS, manufacturer, brand, connectMode, powerMode, power);
        for (DeviceAttribute<?> attribute : attributes) {
            addAttribute(attribute.getName(), attribute);
        }
    }

    @Override
    protected void initializeAttributes() {

    }
}
