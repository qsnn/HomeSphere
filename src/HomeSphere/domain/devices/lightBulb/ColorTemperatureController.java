package OopHW.Exp1.domain.devices.lightBulb;

public interface ColorTemperatureController {
    ColorTemperatureType getColorTemperature();
    void setColorTemperature(ColorTemperatureType colorTemperature) ;

    enum ColorTemperatureType {
        COLD,
        WARM,
        AUTO
    }
}
