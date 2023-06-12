package com.neko233.json.convert;

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
}
