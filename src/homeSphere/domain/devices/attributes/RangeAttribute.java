package homeSphere.domain.devices.attributes;

import homeSphere.domain.devices.DeviceAttribute;

public class RangeAttribute extends AbstractDeviceAttribute {
    private final int min;
    private final int max;

    public RangeAttribute(String name, Integer min, Integer max, Integer defaultValue) {
        super(name, defaultValue);
        this.min = min;
        this.max = max;
        this.value = defaultValue;
    }

    @Override
    public boolean validate(Object value) {
        if (value instanceof Integer) {
            int intValue = (Integer) value;
            return intValue >= min && intValue <= max;
        }
        return false;
    }

    @Override
    public Class<Integer> getValueType() {
        return Integer.class;
    }
}
