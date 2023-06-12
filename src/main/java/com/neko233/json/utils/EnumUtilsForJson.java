package com.neko233.json.utils;

/**
 * @author SolarisNeko on 2023-05-02
 **/
public class EnumUtilsForJson {

    /**
     * 根据排序获取枚举值
     *
     * @param enumClass 枚举类型
     * @param ordinal   枚举顺序. 从 0 开始
     * @return 枚举值
     */
    public static <T extends Enum<T>> T getEnumByOrdinal(Class<?> enumClass, int ordinal) {
        if (enumClass == null) {
            return null;
        }
        if (!enumClass.isEnum()) {
            return null;
        }
        T[] enumConstants = (T[]) enumClass.getEnumConstants();
        if (ordinal >= 0 && ordinal < enumConstants.length) {
            return enumConstants[ordinal];
        }
        return null;
    }

    /**
     * 基于名字获取枚举值
     *
     * @param enumClass 枚举类型
     * @param name      名字
     * @return 枚举值
     */
    public static <T extends Enum<T>> T getEnumByName(Class<?> enumClass, String name) {
        if (enumClass == null) {
            return null;
        }
        if (!enumClass.isEnum()) {
            return null;
        }
        try {
            return Enum.valueOf((Class<T>) enumClass, name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
