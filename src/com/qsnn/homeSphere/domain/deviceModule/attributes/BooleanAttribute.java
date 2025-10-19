package com.qsnn.homeSphere.domain.deviceModule.attributes;

import java.util.Collections;
import java.util.Set;

public class BooleanAttribute extends AbstractDeviceAttribute {
    public BooleanAttribute(String name,  Boolean defaultValue) {
        super(name, defaultValue);
    }

    //检查属性值是否合法
    @Override
    public boolean validate(Object value) {
        return value instanceof Boolean;
    }

}
