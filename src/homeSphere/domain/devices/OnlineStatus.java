package homeSphere.domain.devices;

public interface OnlineStatus {
    void connect();
    void disconnect();

    enum OnlineStatusType {
        ONLINE,
        OUTLINE
    }
}
