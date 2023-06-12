package com.neko233.json.deserializer;

import com.neko233.json.constant.JsonConstant;
import com.neko233.json.utils.StringUtilsForJson;
import com.neko233.json.utils.TextTokenUtils;

import java.util.*;

public class JsonParser {

    private int currentIndex;
    private String jsonString;

    public List<Map<String, Object>> parseJson(String json) {
        if (StringUtilsForJson.isBlank(json)) {
            return null;
        }

        currentIndex = 0;
        jsonString = TextTokenUtils.removeCharInTargetRange(json);

        List<Map<String, Object>> resultList = new ArrayList<>();

        if (jsonString.startsWith("{")) {
            // 解析单个 JSON 对象
            Map<String, Object> jsonObject = parseObject();
            resultList.add(jsonObject);
        } else if (jsonString.startsWith("[")) {
            // 解析 JSON 数组
            List<Object> jsonArray = parseArray();
            for (Object item : jsonArray) {
                if (item instanceof Map) {
                    // 已经是 Map 类型，直接添加到结果列表
                    resultList.add((Map<String, Object>) item);
                    continue;
                }
                if (item instanceof Number
                        || item instanceof String
                        || item instanceof Boolean) {
                    resultList.add(Collections.singletonMap(JsonConstant.DEFAULT_VALUE_KEY, item));
                }
            }
        }

        return resultList;
    }


    private Map<String, Object> parseObject() {
        Map<String, Object> map = new LinkedHashMap<>();

        // Skip the opening curly brace
        currentIndex++;

        while (currentIndex < jsonString.length()) {
            char currentChar = jsonString.charAt(currentIndex);

            if (currentChar == '}') {
                break;
            } else if (currentChar == ',') {
                currentIndex++;
                continue;
            }

            String key = parseKey();
            // Skip the colon
            currentIndex++;

            Object value = parseValue();
            map.put(key, value);
        }

        currentIndex++; // Skip the closing curly brace

        return map;
    }


    private List<Object> parseArray() {
        List<Object> list = new ArrayList<>();

        // Skip the opening square bracket
        currentIndex++;

        while (currentIndex < jsonString.length()) {
            char currentChar = jsonString.charAt(currentIndex);

            if (currentChar == ']') {
                break;
            } else if (currentChar == ',') {
                currentIndex++;
                continue;
            }

            Object value = parseValue();
            list.add(value);
        }

        // Skip the closing square bracket
        currentIndex++;

        return list;
    }


    private Object parseValue() {
        char currentChar = jsonString.charAt(currentIndex);

        if (currentChar == '\"') {
            return parseKey();
        } else if (currentChar == '{') {
            return parseObject();
        } else if (currentChar == '[') {
            return parseArray();
        } else {
            return parsePrimitive();
        }
    }

    private String parseKey() {
        currentIndex++; // 跳过开头的 '\"'
        StringBuilder builder = new StringBuilder();

        while (currentIndex < jsonString.length()) {
            char currentChar = jsonString.charAt(currentIndex);

            if (currentChar == '\"') {
                currentIndex++; // 跳过结尾的 '\"'
                break;
            }

            builder.append(currentChar);
            currentIndex++;
        }

        return builder.toString();
    }

    private Object parsePrimitive() {
        StringBuilder valueBuilder = new StringBuilder();

        while (currentIndex < jsonString.length()) {
            char currentChar = jsonString.charAt(currentIndex);

            if (currentChar == ',' || currentChar == ']' || currentChar == '}') {
                break;
            }

            valueBuilder.append(currentChar);
            currentIndex++;
        }

        String value = valueBuilder.toString().trim();

        if (value.isEmpty()) {
            return null;
        } else if (value.equals("true")) {
            return true;
        } else if (value.equals("false")) {
            return false;
        } else if (value.contains(".") || value.contains("e") || value.contains("E")) {
            // 小数, 优先
            return Double.parseDouble(value);
        } else {
            // 整数
            return Long.parseLong(value);
        }
    }

}
