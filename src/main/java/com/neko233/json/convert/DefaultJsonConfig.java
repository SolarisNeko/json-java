package com.neko233.json.convert;

import org.jetbrains.annotations.Nullable;

/**
 * @author SolarisNeko
 * Date on 2023-06-02
 */
public class DefaultJsonConfig implements JsonConfig {


    public static final DefaultJsonConfig instance = new DefaultJsonConfig();

    @Override
    public boolean isPretty() {
        return false;
    }

    @Override
    public boolean isConversionSupported(Class<?> targetType) {
        return false;
    }

    @Override
    public Object convertValue(Class<?> targetType,
                               Object value) {
        return null;
    }

    @Override
    public void handleException(NoSuchFieldException e) throws RuntimeException {

    }

    @Override
    public @Nullable Object handleByYourDiyType(Class<?> fieldType, Object jsonValue) {
        return null;
    }

    @Override
    public Object castValueToClassType(Object value, Class<?> clazz) {
        if (value == null) {
            return value;
        }
        if (clazz == String.class) {
            return String.valueOf(value);
        }
        if (clazz == Byte.class) {
            value = ((Number) value).byteValue();
        } else if (clazz == Short.class) {
            value = ((Number) value).shortValue();
        } else if (clazz == Integer.class) {
            value = ((Number) value).intValue();
        } else if (clazz == Long.class) {
            value = ((Number) value).longValue();
        } else if (clazz == Float.class) {
            value = ((Number) value).floatValue();
        } else if (clazz == Double.class) {
            value = ((Number) value).doubleValue();
        }
        return value;
    }

}
