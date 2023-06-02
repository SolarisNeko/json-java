package com.neko233.json;

import com.neko233.json.deserializer.JsonParser;
import com.neko233.json.exception.DeserializeJsonException;
import com.neko233.json.serializer.JsonSerializer;
import com.neko233.json.utils.BeanJsonOrmUtils;
import com.neko233.skilltree.commons.core.annotation.Nullable;
import org.apache.commons.collections4.CollectionUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

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
    @Nullable
    static String serialize(Object object) {
        if (object == null) {
            return "";
        }
        return JsonSerializer.serialize(object);
    }

    @Nullable
    static byte[] serializeToBytes(Object object) {
        return serializeToBytes(object, StandardCharsets.UTF_8);
    }


    @Nullable
    static byte[] serializeToBytes(Object object,
                                   Charset charset) {
        return serialize(object).getBytes(charset);
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
        if (CollectionUtils.isEmpty(mapList)) {
            return null;
        }
        return mapList.get(0);
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

    static <T> T deserialize(String text,
                             Class<T> clazz) throws Exception {
        if (text == null || text.isEmpty()) {
            return null;
        }

        JsonParser jsonParser = new JsonParser();
        List<Map<String, Object>> mapList = jsonParser.parseJson(text);
        if (CollectionUtils.isEmpty(mapList)) {
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
