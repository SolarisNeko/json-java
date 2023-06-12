package com.neko233.json;

import com.neko233.json.convert.DefaultJsonConfig;
import com.neko233.json.convert.JsonConfig;
import com.neko233.json.convert.PrettyJsonConfig;
import com.neko233.json.deserializer.JsonParser;
import com.neko233.json.exception.DeserializeJsonException;
import com.neko233.json.formatter.JsonFormatter;
import com.neko233.json.serializer.JsonSerializer;
import com.neko233.json.typeRef.GenericTypeFactory;
import com.neko233.json.typeRef.JsonTypeRef;
import com.neko233.json.utils.BeanJsonOrmUtils;
import com.neko233.json.utils.CollectionUtilsForJson;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author SolarisNeko
 * Date on 2023-06-02
 */
public interface JSON {

    /**
     * 序列化
     *
     * @param object 对象
     * @return json text
     */
    static String serialize(Object object) {
        return serialize(object, null);
    }

    static String serializePretty(Object object) {
        return serialize(object, PrettyJsonConfig.instance);
    }

    static String serialize(Object object, JsonConfig jsonConfig) {
        if (object == null) {
            return "";
        }
        jsonConfig = jsonConfig == null ? DefaultJsonConfig.instance : jsonConfig;
        JsonSerializer jsonSerializer = new JsonSerializer(jsonConfig);

        String serialize = jsonSerializer.serialize(object);
        if (jsonConfig.isPretty()) {
            return JsonFormatter.formatJson(serialize);
        }
        return serialize;
    }

    @Nullable
    static byte[] serializeToBytes(Object object, JsonConfig jsonConfig) {
        return serializeToBytes(object, StandardCharsets.UTF_8, jsonConfig);
    }


    @Nullable
    static byte[] serializeToBytes(Object object,
                                   Charset charset,
                                   JsonConfig jsonConfig) {
        return serialize(object, jsonConfig).getBytes(charset);
    }


    /**
     * 反序列化
     */
    static Map<String, Object> deserialize(byte[] bytes) {
        return deserialize(bytes, StandardCharsets.UTF_8);
    }

    static Map<String, Object> deserialize(byte[] bytes,
                                           Charset charset) {
        return deserialize(new String(bytes, charset));
    }

    static Map<String, Object> deserialize(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }

        JsonParser jsonParser = new JsonParser();
        List<Map<String, Object>> mapList = jsonParser.parseJson(text);
        if (CollectionUtilsForJson.isEmpty(mapList)) {
            return null;
        }
        return mapList.get(0);
    }

    static <T> T deserialize(String text, JsonTypeRef<T> jsonTypeRef) {
        if (text == null || text.isEmpty()) {
            return null;
        }

        JsonParser jsonParser = new JsonParser();
        List<Map<String, Object>> mapList = jsonParser.parseJson(text);
        if (CollectionUtilsForJson.isEmpty(mapList)) {
            return null;
        }
        Map<String, Object> stringObjectMap = mapList.get(0);
        Type genericType = jsonTypeRef.getType();
        if (genericType == null) {
            return null;
        }
        try {
            Object instance = GenericTypeFactory.createInstance(genericType);
            return (T) BeanJsonOrmUtils.mapToBean(stringObjectMap, instance.getClass());
        } catch (Exception e) {
            throw new DeserializeJsonException(e);
        }
    }

    /**
     * parse json Array to data struct
     *
     * @param text 文本
     * @return object tree
     */
    static List<Map<String, Object>> deserializeArray(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }

        JsonParser jsonParser = new JsonParser();
        return jsonParser.parseJson(text);
    }

    @Nullable
    static <T> List<T> deserializeArray(String text, Class<T> clazz) {
        if (text == null || text.isEmpty()) {
            return null;
        }

        JsonParser jsonParser = new JsonParser();
        List<Map<String, Object>> maps = jsonParser.parseJson(text);
        return maps.stream()
                .map(map -> {
                    try {
                        return BeanJsonOrmUtils.mapToBean(map, clazz);
                    } catch (Exception e) {
                        throw new DeserializeJsonException(e, "反序列化失败. json Text = {}", text);
                    }
                })
                .collect(Collectors.toList());
    }

    static <T> T deserialize(String text,
                             Class<T> clazz) throws Exception {
        if (text == null || text.isEmpty()) {
            return null;
        }

        JsonParser jsonParser = new JsonParser();
        List<Map<String, Object>> mapList = jsonParser.parseJson(text);
        if (CollectionUtilsForJson.isEmpty(mapList)) {
            return null;
        }
        Map<String, Object> map = mapList.get(0);
        if (map == null) {
            return null;
        }
        try {
            return BeanJsonOrmUtils.mapToBean(map, clazz);
        } catch (Exception e) {
            throw new DeserializeJsonException(e, "反序列化失败. json Text = {}", text);
        }
    }

}
