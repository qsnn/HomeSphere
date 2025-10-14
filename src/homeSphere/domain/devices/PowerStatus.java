package homeSphere.domain.devices;

public interface PowerStatus {
    void open();
    void close();

    enum PowerStatusType {
        POWERED,
        UNPOWERED
    }
}
