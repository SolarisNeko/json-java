package com.neko233.json.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 删除 char 不在特殊内容中
 *
 * @author SolarisNeko on 2023-06-02
 **/
public class TextTokenUtils {

    public static String removeCharInTargetRange(String input) {
        return removeCharInTargetRange(input,
                ListUtilsForJson.of('\n', '\t', ' '),
                ListUtilsForJson.of('\'', '"')
        );
    }

    /**
     * @param input            输入内容
     * @param toRemoveCharList 要删除的 char
     * @param specialCharArray 特殊符号, 成对匹配进入【接收所有字符的状态】
     * @return 处理后的字符
     */
    public static String removeCharInTargetRange(String input,
                                                 final List<Character> toRemoveCharList,
                                                 final List<Character> specialCharArray) {
        StringBuilder result = new StringBuilder();

        Map<Character, Boolean> stateMap = new HashMap<>();
        for (Character character : specialCharArray) {
            stateMap.put(character, false);
        }


        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            Boolean isInState = stateMap.get(c);
            if (isInState == null) {
                //  要删除的部分
                if (toRemoveCharList.contains(c)) {
                    continue;
                }
                result.append(c);
                continue;
            }

            // 反转状态
            stateMap.put(c, !isInState);

            boolean isInAnyState = isInAnyState(stateMap);
            if (specialCharArray.contains(c)) {
                result.append(c);
                continue;
            }

            // 特殊状态, 所有内容都接收
            if (isInAnyState) {
                result.append(c);
                continue;
            }

            continue;
        }

        return result.toString();
    }

    private static boolean isInAnyState(Map<Character, Boolean> stateMap) {
        return stateMap.values().stream().anyMatch(b -> b);
    }


}
