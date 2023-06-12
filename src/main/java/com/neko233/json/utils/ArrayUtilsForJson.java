package com.neko233.json.utils;

import java.lang.reflect.Array;

/**
 * @author SolarisNeko
 * Date on 2023-06-12
 */
public class ArrayUtilsForJson {
    @SuppressWarnings("unchecked")
    public static <T> T get(Object array, int index) {
        if (null == array) {
            return null;
        }

        // reverse
        if (index < 0) {
            index += Array.getLength(array);
        }

        try {
            return (T) Array.get(array, index);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

}
