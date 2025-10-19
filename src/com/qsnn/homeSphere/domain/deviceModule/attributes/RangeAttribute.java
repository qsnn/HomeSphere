package com.qsnn.homeSphere.domain.deviceModule.attributes;

public class RangeAttribute extends AbstractDeviceAttribute {
    private final int min;
    private final int max;
    private final String unit;

    public RangeAttribute(String name, Integer min, Integer max, Integer defaultValue, String unit) {
        super(name, defaultValue);
        this.min = min;
        this.max = max;
        this.value = defaultValue;
        this.unit = unit;
    }

    //检查属性值是否合法
    @Override
    public boolean validate(Object value) {
        if (value instanceof Integer) {
            int intValue = (Integer) value;
            return intValue >= min && intValue <= max;
        }
        return false;
    }

}
