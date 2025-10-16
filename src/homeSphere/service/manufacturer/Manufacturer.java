package homeSphere.service.manufacturer;

import homeSphere.service.connectService.ConnectMode;

import java.util.Set;

public class Manufacturer {
    protected final String name;
    protected final Set<ConnectMode> supportedConnectMode;

    public Manufacturer(String name, Set<ConnectMode> supportedConnectMode) {
        this.name = name;
        this.supportedConnectMode = supportedConnectMode;
    }

    public String getName() {
        return name;
    }

    public Set<ConnectMode> getSupportedConnectMode() {
        return supportedConnectMode;
    }
}



