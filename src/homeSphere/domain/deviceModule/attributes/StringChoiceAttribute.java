package homeSphere.domain.deviceModule.attributes;

import java.util.*;

public class StringChoiceAttribute extends AbstractDeviceAttribute {
    private final Set<String> allowedValues;

    public StringChoiceAttribute(String name, String defaultValue, String... allowedValues) {
        super(name, defaultValue);
        this.allowedValues = new HashSet<>(Arrays.asList(allowedValues));
        // 确保默认值在允许值中
        if (!this.allowedValues.contains(defaultValue)) {
            throw new IllegalArgumentException("默认值不在允许值范围内");
        }
    }

    @Override
    public boolean validate(Object value) {
        return value instanceof String && allowedValues.contains(value);
    }

    @Override
    public Class getValueType() {
        return String.class;
    }

    public Set<String> getAllowedValues() {
        return Collections.unmodifiableSet(allowedValues);
    }

}
