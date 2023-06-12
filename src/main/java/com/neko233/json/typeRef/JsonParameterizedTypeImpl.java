package com.neko233.json.typeRef;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class JsonParameterizedTypeImpl implements ParameterizedType {

    private final String className;
    private final Type[] typeArguments;

    public JsonParameterizedTypeImpl(String className, Type... typeArguments) {
        this.className = className;
        this.typeArguments = typeArguments;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return typeArguments;
    }

    @Override
    public Type getRawType() {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}