package com.neko233.json.typeRef;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Java Runtime 范型擦除, 保留范型
 */
public class JsonTypeRef<T> {

    private final Type type;

    public JsonTypeRef() {
        Type superClass = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) superClass;
        this.type = parameterizedType.getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }

}
