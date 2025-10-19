package com.qsnn.homeSphere.domain.deviceModule;

import java.util.Set;

public class Manufacturer {
    //制造商名称
    protected final String name;

    //所支持的连接方式 - WIFI、BLUETOOTH......
    protected final Set<Device.ConnectMode> supportedConnectMode;

    public Manufacturer(String name, Set<Device.ConnectMode> supportedConnectMode) {
        this.name = name;
        this.supportedConnectMode = supportedConnectMode;
    }

    public String getName() {
        return name;
    }

    public Set<Device.ConnectMode> getSupportedConnectMode() {
        return supportedConnectMode;
    }
}



