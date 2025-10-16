package homeSphere.domain.deviceModule.attributes;

public class BooleanAttribute extends AbstractDeviceAttribute {
    public BooleanAttribute(String name,  Boolean defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public boolean validate(Object value) {
        return value instanceof Boolean;
    }

    @Override
    public Class<Boolean> getValueType() {
        return Boolean.class;
    }
}
