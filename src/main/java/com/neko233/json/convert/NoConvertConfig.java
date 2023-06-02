package com.neko233.json.convert;

/**
 * @author SolarisNeko
 * Date on 2023-06-02
 */
public class NoConvertConfig implements ConvertConfig {


    public static final NoConvertConfig instance = new NoConvertConfig();

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
