package com.neko233.json.typeRef;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author SolarisNeko
 * Date on 2023-06-12
 */
public class GenericTypeFactory {

    public static <T> T createInstance(Type genericType) throws NoSuchMethodException {
        Class<?> rawType = extractClassFromType(genericType);
        try {
            return (T) rawType.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + rawType, e);
        }
    }

    public static Class<?> extractClassFromType(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            if (rawType instanceof Class) {
                return (Class<?>) rawType;
            }
        }
        return null;
    }
}
