
package com.neko233.json.serializer;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class JsonSerializer {
    public static String serialize(Object obj) {
        StringBuilder json = new StringBuilder();
        serialize(obj, json);
        return json.toString();
    }

    private static void serialize(Object obj,
                                  StringBuilder json) {
        if (obj == null) {
            json.append("null");
        } else if (obj instanceof String) {
            json.append("\"").append(escapeString((String) obj)).append("\"");
        } else if (obj instanceof Number || obj instanceof Boolean) {
            json.append(obj.toString());
        } else if (obj instanceof Collection<?>) {
            serializeCollection((Collection<?>) obj, json);
        } else if (obj instanceof Map<?, ?>) {
            serializeMap((Map<?, ?>) obj, json);
        } else {
            serializeObject(obj, json);
        }
    }

    private static void serializeCollection(Collection<?> collection,
                                            StringBuilder json) {
        json.append("[");
        boolean first = true;

        for (Object item : collection) {
            if (first) {
                first = false;
            } else {
                json.append(",");
            }
            serialize(item, json);
        }

        json.append("]");
    }

    private static void serializeMap(Map<?, ?> map,
                                     StringBuilder json) {
        json.append("{");
        boolean first = true;

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (first) {
                first = false;
            } else {
                json.append(",");
            }
            json.append("\"").append(escapeString(entry.getKey().toString())).append("\":");
            serialize(entry.getValue(), json);
        }

        json.append("}");
    }

    private static void serializeObject(Object obj,
                                        StringBuilder json) {
        json.append("{");
        boolean first = true;

        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;

            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                continue;
            }

            if (first) {
                first = false;
            } else {
                json.append(",");
            }
            json.append("\"").append(escapeString(field.getName())).append("\":");
            serialize(value, json);
        }

        json.append("}");
    }

    private static String escapeString(String str) {
        StringBuilder sb = new StringBuilder();

        for (char c : str.toCharArray()) {
            if (c == '\"' || c == '\\') {
                sb.append('\\');
            }
            sb.append(c);
        }

        return sb.toString();
    }
}
