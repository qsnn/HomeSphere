package homeSphere.domain.devices.airconditioner;

public interface AirConditionerModelController {
    AirConditionerModelType getModel();
    void setModel(AirConditionerModelType model) ;

    enum AirConditionerModelType {
        COOL,
        WARM,
        FAN,
        DRY,
        AUTO
    }
}
