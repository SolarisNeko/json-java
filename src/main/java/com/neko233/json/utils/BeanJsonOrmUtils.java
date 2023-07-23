package com.neko233.json.utils;

import com.neko233.json.constant.JsonConstant;
import com.neko233.json.convert.DefaultJsonConfig;
import com.neko233.json.convert.JsonConfig;
import com.neko233.json.exception.DeserializeJsonException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class BeanJsonOrmUtils {

    public static final String DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMATTER = "yyyy-MM-dd 00:00:00";

    public static <T> T mapToBeanAny(Map<String, Object> map,
                                     Class<?> clazz) throws Exception {
        return mapToBean(map, clazz, null);
    }

    /**
     * Map 暂时只能 Map<String, Object>
     *
     * @param map   解析后的对象
     * @param clazz 类
     * @return Map
     */
    public static <T> T mapToBean(Map<String, Object> map,
                                  Class<T> clazz) throws Exception {
        if (clazz == Map.class) {
            return (T) map;
        }
        return mapToBean(map, clazz, null);
    }

    public static <T> T mapToBean(Map<String, Object> map,
                                  Class<?> clazz,
                                  JsonConfig config) throws Exception {
        config = config == null ? DefaultJsonConfig.instance : config;

        if (clazz == String.class
                || Number.class.isAssignableFrom(clazz)) {
            Object o = map.get(JsonConstant.DEFAULT_VALUE_KEY);
            return (T) config.castValueToClassType(o, clazz);
        }

        Constructor<T> constructor = (Constructor<T>) clazz.getConstructor();
        constructor.setAccessible(true);
        T instance = constructor.newInstance();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String jsonName = entry.getKey();
            Object jsonValue = entry.getValue();

            try {
                Field field = clazz.getDeclaredField(jsonName);
                field.setAccessible(true);

                Object convertedValue = convertValue(field, jsonValue, config);
                field.set(instance, convertedValue);
            } catch (NoSuchFieldException e) {
                // 如果 Map 中的 key 不存在于目标类的字段中，可以选择忽略或者处理该异常
                config.handleException(e);
            }
        }

        return instance;
    }


    private static Object convertValue(Field field,
                                       Object jsonValue,
                                       JsonConfig config) throws Exception {
        if (jsonValue == null) {
            return null;
        }

        Class<?> fieldType = field.getType();
        Object fieldValue = config.handleByYourDiyType(fieldType, jsonValue);
        if (fieldValue != null) {
            return fieldValue;
        }

        Class<?> jsonType = jsonValue.getClass();

        if (fieldType.isEnum()) {
            if (jsonValue instanceof String) {
                return EnumUtilsForJson.getEnumByName(fieldType, String.valueOf(jsonValue));
            }
            if (jsonValue instanceof Number) {
                int ordinal = Integer.parseInt(StringUtilsForJson.trim(String.valueOf(jsonValue)));
                return EnumUtilsForJson.getEnumByOrdinal(fieldType, ordinal);
            }
            return null;
        }

        if (List.class.isAssignableFrom(fieldType)) {
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments.length == 1 && actualTypeArguments[0] instanceof Class) {
                    Class<?> listElementType = (Class<?>) actualTypeArguments[0];

                    List<Map<String, Object>> jsonValueList = (List<Map<String, Object>>) jsonValue;
                    return jsonValueList.stream()
                            .map(obj -> {
                                try {
                                    return BeanJsonOrmUtils.mapToBeanAny(obj, listElementType);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            })
                            .collect(Collectors.toList());
                }
            }
        } else if (Set.class.isAssignableFrom(fieldType)) {
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments.length == 1 && actualTypeArguments[0] instanceof Class) {
                    Class<?> setElementType = (Class<?>) actualTypeArguments[0];

                    final List<Map<String, Object>> jsonValueList = (List<Map<String, Object>>) jsonValue;
                    return jsonValueList.stream()
                            .map(obj -> {
                                try {
                                    return BeanJsonOrmUtils.mapToBeanAny(obj, setElementType);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            })
                            .collect(Collectors.toSet());
                }
            }
        } else if (Map.class.isAssignableFrom(fieldType)) {
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments.length == 2 && actualTypeArguments[0] instanceof Class && actualTypeArguments[1] instanceof Class) {
                    Class<?> mapKeyType = (Class<?>) actualTypeArguments[0];
                    Class<?> mapValueType = (Class<?>) actualTypeArguments[1];
                    if (Map.class.isAssignableFrom(jsonValue.getClass())) {
                        Map<?, ?> jsonMap = (Map<?, ?>) jsonValue;
                        Map<Object, Object> convertedMap = new HashMap<>();
                        for (Map.Entry<?, ?> entry : jsonMap.entrySet()) {
                            Object key = convertValue(field, entry.getKey(), config);
                            Object value = convertValue(field, entry.getValue(), config);
                            convertedMap.put(key, value);
                        }
                        return convertedMap;
                    }
                }
            }
        } else if (Queue.class.isAssignableFrom(fieldType)) {
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments.length == 1 && actualTypeArguments[0] instanceof Class) {
                    Class<?> queueElementType = (Class<?>) actualTypeArguments[0];
                    if (Queue.class.isAssignableFrom(jsonValue.getClass())) {
                        Queue<?> jsonQueue = (Queue<?>) jsonValue;
                        Queue<Object> convertedQueue = new LinkedList<>();
                        for (Object item : jsonQueue) {
                            convertedQueue.offer(convertValue(field, item, config));
                        }
                        return convertedQueue;
                    }
                }
            }
        }

        if (fieldType.isAssignableFrom(jsonType)) {
            return jsonValue;
        }

        if (config.isConversionSupported(fieldType)) {
            return config.convertValue(fieldType, jsonValue);
        }

        // [Special] json 的数值, 特殊兼容
        if (fieldType == Short.class || fieldType == short.class) {
            if (jsonValue instanceof Long) {
                return Short.parseShort(String.valueOf(jsonValue));
            }
        }
        if (fieldType == Integer.class || fieldType == int.class) {
            if (jsonValue instanceof Long) {
                return Integer.parseInt(String.valueOf(jsonValue));
            }
        }
        if (fieldType == Long.class || fieldType == long.class) {
            if (jsonValue instanceof Long) {
                return jsonValue;
            }
        }
        if (fieldType == Float.class || fieldType == float.class) {
            if (jsonValue instanceof Double) {
                return Float.parseFloat(String.valueOf(jsonValue));
            }
        }
        if (fieldType == Double.class || fieldType == double.class) {
            if (jsonValue instanceof Double) {
                return Double.parseDouble(String.valueOf(jsonValue));
            }
        }
        if (fieldType == Boolean.class || fieldType == boolean.class) {
            if (jsonValue instanceof Boolean) {
                return jsonValue;
            }
            return null;
        }
        // jdk advanced data struct
        if (fieldType == Date.class) {
            if (jsonValue instanceof String) {
                SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMATTER);
                try {
                    return formatter.parse(String.valueOf(jsonValue));
                } catch (ParseException e) {
                    throw new DeserializeJsonException(e, "date 的 string 格式不是 yyyy-MM-dd HH:mm:ss. jsonValue = {}", jsonValue);
                }
            }
            if (jsonValue instanceof Long) {
                return new Date((Long) jsonValue);
            }
            return null;
        }
        if (fieldType == LocalDateTime.class) {
            if (jsonValue instanceof String) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER);
                return LocalDateTime.parse(jsonValue.toString(), formatter);
            }
            if (jsonValue instanceof Long) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) jsonValue), ZoneId.systemDefault());
            }
            return null;
        }
        if (fieldType == LocalDate.class) {
            if (jsonValue instanceof String) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
                return LocalDate.parse(jsonValue.toString(), formatter);
            }
            return null;
        }

        throw new IllegalArgumentException("不支持的转换类型. 需要在 jsonConfig 中定制处理. Unsupported conversion from " + jsonValue.getClass() + " to " + fieldType);
    }
}

