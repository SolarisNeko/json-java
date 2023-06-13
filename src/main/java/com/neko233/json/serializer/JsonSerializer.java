package com.neko233.json.serializer;

import com.neko233.json.convert.JsonConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class JsonSerializer {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
    private JsonConfig jsonConfig;

    public JsonSerializer(JsonConfig jsonConfig) {
        this.jsonConfig = jsonConfig;
    }

    public String serialize(Object obj) {
        StringBuilder jsonBuilder = new StringBuilder();
        this.serialize(obj, jsonBuilder);
        return jsonBuilder.toString();
    }

    private void serialize(Object obj,
                           StringBuilder jsonBuilder) {
        if (obj == null) {
            jsonBuilder.append("null");
            return;
        }

        // value
        if (obj instanceof String) {
            // base
            jsonBuilder.append("\"").append(escapeString((String) obj)).append("\"");
            return;
        }
        if (obj instanceof Number || obj instanceof Boolean) {
            // base
            jsonBuilder.append(obj.toString());
            return;
        }
        if (obj instanceof Date) {
            Date date = (Date) obj;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = simpleDateFormat.format(date);
            jsonBuilder.append("\"").append(escapeString(dateStr)).append("\"");
            return;
        }
        if (obj instanceof LocalDateTime) {
            LocalDateTime dateTime = (LocalDateTime) obj;
            String format = dateTime.format(DATE_TIME_FORMATTER);
            jsonBuilder.append("\"").append(escapeString(format)).append("\"");
            return;
        }
        if (obj instanceof LocalDate) {
            LocalDate dateTime = (LocalDate) obj;
            String format = dateTime.format(DATE_FORMATTER);
            jsonBuilder.append("\"").append(escapeString(format)).append("\"");
            return;
        }

        // collection
        if (obj instanceof Collection<?>) {
            serializeCollection((Collection<?>) obj, jsonBuilder);
            return;
        }
        if (obj instanceof Map<?, ?>) {
            serializeMap((Map<?, ?>) obj, jsonBuilder);
            return;
        }

        serializeObject(obj, jsonBuilder);
    }


    private void serializeEnum(Enum<?> obj, StringBuilder jsonBuilder) {
        jsonBuilder.append("{");

        String escapeKey = escapeString(obj.name());
        jsonBuilder.append("\"")
                .append(escapeKey)
                .append("\":")
                .append(obj.ordinal())
        ;

        jsonBuilder.append("}");
    }

    private void serializeCollection(Collection<?> collection,
                                     StringBuilder jsonBuilder) {
        jsonBuilder.append("[");
        boolean first = true;

        for (Object item : collection) {
            if (first) {
                first = false;
            } else {
                jsonBuilder.append(",");
            }

            // recursive
            serialize(item, jsonBuilder);
        }

        jsonBuilder.append("]");
    }

    private void serializeMap(Map<?, ?> map,
                              StringBuilder jsonBuilder) {
        jsonBuilder.append("{");
        boolean first = true;

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (first) {
                first = false;
            } else {
                jsonBuilder.append(",");
            }
            jsonBuilder.append("\"").append(escapeString(entry.getKey().toString())).append("\":");

            // recursive
            serialize(entry.getValue(), jsonBuilder);
        }

        jsonBuilder.append("}");
    }

    private void serializeObject(Object obj,
                                 StringBuilder jsonBuilder) {
        jsonBuilder.append("{");
        boolean first = true;

        for (Field field : obj.getClass().getDeclaredFields()) {
            // 判断字段是否为静态字段
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            field.setAccessible(true);

            Object value;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                continue;
            }

            // enum 比较特殊 | 优先用 enum index
            if (value instanceof Enum<?>) {
                value = ((Enum<?>) value).ordinal();
//                value = ((Enum<?>) value).name();
            }

            // avoid circle-ref, just address check
            if (value == obj) {
                continue;
            }

            if (first) {
                first = false;
            } else {
                jsonBuilder.append(",");
            }

            String jsonKey = escapeString(field.getName());

            // key
            jsonBuilder.append("\"").append(jsonKey).append("\":");
            // recursive
            serialize(value, jsonBuilder);
        }

        jsonBuilder.append("}");
    }

    /**
     * 转义字符串
     *
     * @param str 原字符串
     * @return 内容
     */
    private String escapeString(String str) {
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
