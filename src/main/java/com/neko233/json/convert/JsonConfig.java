package com.neko233.json.convert;

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
}
