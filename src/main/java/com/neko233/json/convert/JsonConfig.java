package com.neko233.json.convert;

import org.jetbrains.annotations.Nullable;

public interface JsonConfig {

    boolean isPretty();

    /**
     * 是否支持这个类型
     *
     * @param targetType 目标类型
     * @return boolean
     */
    boolean isConversionSupported(Class<?> targetType);

    /**
     * @param targetType 目标类型
     * @param value      值
     * @return value
     */
    Object convertValue(Class<?> targetType,
                        Object value);


    /**
     * 一些异常处理 ｜ 可以添加自定义的逻辑，如记录日志、抛出异常等
     *
     * @param e 异常
     * @throws RuntimeException any exception to 上层
     */
    void handleException(NoSuchFieldException e) throws RuntimeException;

    /**
     * 定制某些 fieldType 的 jsonValue 定制反序列化 | 用于可制化需求
     *
     * @param fieldType 字段类型
     * @param jsonValue json值
     * @return value / null = 不需要 diy, 走默认逻辑
     */
    @Nullable
    Object handleByYourDiyType(Class<?> fieldType, Object jsonValue);

    /**
     * 将 value 转为某个类型
     *
     * @param value   值
     * @param toClass to类型
     * @return 新的值
     */
    Object castValueToClassType(Object value, Class<?> toClass);
}
