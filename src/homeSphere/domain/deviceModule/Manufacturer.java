package homeSphere.domain.deviceModule;

import java.util.Set;

public class Manufacturer {
    protected final String name;
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



