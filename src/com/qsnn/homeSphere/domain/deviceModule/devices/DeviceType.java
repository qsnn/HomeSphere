package com.qsnn.homeSphere.domain.deviceModule.devices;

public enum DeviceType {
    AIR_CONDITIONER("空调"),
    LIGHT_BULB("智能灯泡"),
    SMART_LOCK("智能门锁"),
    BATHROOM_SCALE("体重秤"),
    UNDEFINED("未定义设备");

    private final String displayName;

    DeviceType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
