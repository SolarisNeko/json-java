package com.neko233.json.utils;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionUtilsForJson {


    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(final Collection<?> collection) {
        return !isEmpty(collection);
    }


    public static <T> Collection<T> addAll(List<T> es,
                                           Enumeration<T> networkInterfaces) {
        if (networkInterfaces == null) {
            return es;
        }
        if (es == null) {
            es = new ArrayList<>();
        }
        while (networkInterfaces.hasMoreElements()) {
            es.add(networkInterfaces.nextElement());
        }
        return es;
    }

    /**
     * 检查非负数
     *
     * @param value
     * @param name
     * @return
     */
    static int checkSizeNonnegative(int value,
                                    String name) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " cannot be negative but was: " + value);
        }
        return value;
    }

    /**
     * 模拟 JDK 11+ 的 List.of
     *
     * @param objs 对象
     * @return List
     */
    @SafeVarargs
    public static <V> List<V> ofList(V... objs) {
        if (objs == null) {
            return new ArrayList<>(0);
        }
        return Arrays.stream(objs).collect(Collectors.toList());
    }

    /**
     * 模拟 JDK 11+ 的 Set.of
     *
     * @param objs 对象
     * @return Set
     */
    @SafeVarargs
    public static <V> Set<V> ofSet(V... objs) {
        if (objs == null) {
            return new HashSet<>(0);
        }
        return Arrays.stream(objs).collect(Collectors.toSet());
    }

}
