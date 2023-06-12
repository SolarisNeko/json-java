package com.neko233.json.utils;


import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class StringUtilsForJson {


    /**
     * 没找到的 index
     */
    public static final int INDEX_NOT_FOUND = -1;
    /**
     * char 空格
     */
    public static final char C_SPACE = ' ';
    /**
     * 空格
     */
    public static final String SPACE = " ";
    /**
     * 空字符串
     */
    public static final String EMPTY = "";
    /**
     * A String for linefeed LF ("\n").
     * 换行
     */
    public static final String LF = "\n";
    /**
     * carriage return CR ("\r").
     * 回车
     */
    public static final String CR = "\r";
    /**
     * 空数组
     */
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    /**
     * 最大处理字符串
     */
    private static final int MAX_HANDLE_STRING = 8192;
    /**
     * 字符常量：斜杠 {@code '/'}
     */
    private static final char C_SLASH = '/';
    /**
     * 字符常量：反斜杠 {@code '\\'}
     */
    private static final char C_BACKSLASH = '\\';
    /**
     * 字符串常量：空 JSON {@code "{}"}
     */
    private static final String EMPTY_JSON = "{}";


    /**
     * 安全 trim
     *
     * @param input 输入
     * @return 内容
     */
    public static String trim(final String input) {
        if (StringUtilsForJson.isBlank(input)) {
            return "";
        }
        return input.trim();
    }

    public static boolean isEmpty(final char... cs) {
        return isEmpty(new String(cs));
    }

    /**
     * 空字符串判断
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isBlank(final CharSequence cs) {
        final int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
     *
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtilsForJson.isNotBlank(null)      = false
     * StringUtilsForJson.isNotBlank("")        = false
     * StringUtilsForJson.isNotBlank(" ")       = false
     * StringUtilsForJson.isNotBlank("bob")     = true
     * StringUtilsForJson.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @since 0.1.8
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }


    /**
     * 首字母大写
     *
     * @param content 文本
     * @return aaa -> Aaa
     */
    public static String firstWordUpperCase(String content) {
        if (StringUtilsForJson.isBlank(content)) {
            return content;
        }
        if (content.length() == 1) {
            return content.toUpperCase();
        }
        return content.substring(0, 1).toUpperCase() + content.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param content 文本
     * @return AAa -> aAa
     */
    public static String firstWordLowerCase(String content) {
        if (StringUtilsForJson.isBlank(content)) {
            return content;
        }
        if (content.length() == 1) {
            return content.toLowerCase();
        }
        return content.substring(0, 1).toLowerCase() + content.substring(1);
    }


    /**
     * 转换成 Big Camel（大驼峰）的 Upper Case 版本!
     *
     * @return SystemUser -> SYSTEM_USER
     */
    public static String toBigCamelCaseUpper(String name) {
        StringBuilder builder = new StringBuilder();
        char[] chars = name.toCharArray();
        if (chars.length == 0) {
            return name;
        }

        // 首字母不处理
        builder.append(chars[0]);
        // [1, n] 字母, 遇到大写, 进行大驼峰处理
        for (int index = 1; index < chars.length; index++) {
            char aChar = chars[index];
            final boolean upperCase = Character.isUpperCase(aChar);
            if (upperCase) {
                // is Upper Case
                builder.append("_").append(aChar);
            } else {
                // is Lower Case
                final char upperChar = Character.toUpperCase(aChar);
                builder.append(upperChar);
            }
        }
        return builder.toString();
    }

    /**
     * 转换成 Big Camel（大驼峰）的 lower case 版本!
     *
     * @return SystemUser -> system_user
     */
    public static String toBigCamelCaseLower(String name) {
        StringBuilder builder = new StringBuilder();
        // 转化成 char[] 流
        char[] chars = name.toCharArray();
        if (chars.length == 0) {
            return name;
        }
        // 首字母不处理
        builder.append(Character.toLowerCase(chars[0]));
        // [1, n] 字母, 遇到大写, 进行大驼峰处理
        for (int index = 1; index < chars.length; index++) {
            char aChar = chars[index];
            boolean upperCase = Character.isUpperCase(aChar);
            if (upperCase) {
                // Upper Case
                builder.append("_")
                        .append(Character.toLowerCase(aChar));
            } else {
                // Lower Case
                char upperChar = Character.toLowerCase(aChar);
                builder.append(upperChar);
            }
        }
        return builder.toString();
    }

    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static String truncate(final String str,
                                  final int maxWidth) {
        return truncate(str, 0, maxWidth);
    }

    public static String truncate(final String str,
                                  final int offset,
                                  final int maxWidth) {
        if (offset < 0) {
            throw new IllegalArgumentException("offset cannot be negative");
        }
        if (maxWidth < 0) {
            throw new IllegalArgumentException("maxWith cannot be negative");
        }
        if (str == null) {
            return null;
        }
        if (offset > str.length()) {
            return EMPTY;
        }
        if (str.length() > maxWidth) {
            final int ix = Math.min(offset + maxWidth, str.length());
            return str.substring(offset, ix);
        }
        return str.substring(offset);
    }

    /**
     * not null upper case!
     */
    public static String upperCase(final String str) {
        return upperCase(str, null);
    }

    public static String upperCase(final String str,
                                   final Locale locale) {
        if (str == null) {
            return null;
        }
        if (locale == null) {
            return str.toUpperCase();
        }
        return str.toUpperCase(locale);
    }

    public static String lowerCase(final String str) {
        return lowerCase(str, null);
    }

    public static String lowerCase(final String str,
                                   final Locale locale) {
        if (str == null) {
            return null;
        }
        if (locale == null) {
            return str.toLowerCase();
        }
        return str.toLowerCase(locale);
    }


    /**
     * "1, 2, 3" --> "1,2,3"
     *
     * @param s         文本
     * @param separator 分隔符
     * @return trim 后的内容
     */
    public static String splitTrimThenJoin(String s,
                                           String separator) {
        String notNullStr = Optional.of(s).orElse("");
        return Arrays.stream(notNullStr.split(separator)).map(String::trim).filter(StringUtilsForJson::isNotBlank).collect(Collectors.joining(separator));
    }

    /**
     * JDK 默认的 join 不方便
     */
    public static String join(CharSequence delimiter,
                              Object... elements) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(elements);
        // Number of elements not likely worth Arrays.stream overhead.
        StringJoiner joiner = new StringJoiner(delimiter);
        for (Object obj : elements) {
            joiner.add(String.valueOf(obj));
        }
        return joiner.toString();
    }


    public static String[] tokenizeToStringArray(String str,
                                                 String delimiters) {
        return tokenizeToStringArray(str, delimiters, true, true);
    }

    /**
     * split & trim & ignore null token
     *
     * @param str               内容
     * @param delimiters        分隔符
     * @param trimTokens        trim ?
     * @param ignoreEmptyTokens ignore empty?
     * @return String
     */
    public static String[] tokenizeToStringArray(String str,
                                                 String delimiters,
                                                 boolean trimTokens,
                                                 boolean ignoreEmptyTokens) {
        if (str == null) {
            return new String[]{};
        } else {
            StringTokenizer st = new StringTokenizer(str, delimiters);
            List<String> tokens = new ArrayList<>();

            while (true) {
                String token;
                do {
                    if (!st.hasMoreTokens()) {
                        return toStringArray(tokens);
                    }

                    token = st.nextToken();
                    if (trimTokens) {
                        token = token.trim();
                    }
                } while (ignoreEmptyTokens && token.length() <= 0);

                tokens.add(token);
            }
        }
    }

    public static String[] toStringArray(Collection<String> collection) {
        return CollectionUtilsForJson.isNotEmpty(collection) ? collection.toArray(EMPTY_STRING_ARRAY) : EMPTY_STRING_ARRAY;
    }

    public static void appendByPrintStyle(StringBuilder builder,
                                          String key,
                                          Object value) {
        builder.append(key).append(value).append("\n");
    }


    public static String format(String strPattern,
                                Object... argArray) {
        return template(strPattern, "{}", argArray);
    }

    /**
     * 格式化字符串<br>
     * 此方法只是简单将指定占位符 按照顺序替换为参数<br>
     * 如果想输出占位符使用 \\转义即可，如果想输出占位符之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "{}", "a", "b") =》 this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "{}", "a", "b") =》 this is {} for a<br>
     * 转义\： format("this is \\\\{} for {}", "{}", "a", "b") =》 this is \a for b<br>
     *
     * @param pattern     字符串模板
     * @param placeHolder 占位符，例如{}
     * @param argArray    参数列表
     * @return 结果
     */
    public static String template(String pattern,
                                  String placeHolder,
                                  Object... argArray) {
        if (StringUtilsForJson.isBlank(pattern) || StringUtilsForJson.isBlank(placeHolder) || argArray == null) {
            return pattern;
        }
        final int strPatternLength = pattern.length();
        final int placeHolderLength = placeHolder.length();

        // 初始化定义好的长度以获得更好的性能
        final StringBuilder sbuf = new StringBuilder(strPatternLength + 50);

        int handledPosition = 0;// 记录已经处理到的位置
        int delimIndex;// 占位符所在位置
        for (int argIndex = 0; argIndex < argArray.length; argIndex++) {
            delimIndex = pattern.indexOf(placeHolder, handledPosition);
            if (delimIndex == -1) {// 剩余部分无占位符
                if (handledPosition == 0) { // 不带占位符的模板直接返回
                    return pattern;
                }
                // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
                sbuf.append(pattern, handledPosition, strPatternLength);
                return sbuf.toString();
            }

            // 转义符
            if (delimIndex > 0 && pattern.charAt(delimIndex - 1) == C_BACKSLASH) {// 转义符
                if (delimIndex > 1 && pattern.charAt(delimIndex - 2) == C_BACKSLASH) {// 双转义符
                    // 转义符之前还有一个转义符，占位符依旧有效
                    sbuf.append(pattern, handledPosition, delimIndex - 1);
                    sbuf.append(String.valueOf(argArray[argIndex]));
                    handledPosition = delimIndex + placeHolderLength;
                } else {
                    // 占位符被转义
                    argIndex--;
                    sbuf.append(pattern, handledPosition, delimIndex - 1);
                    sbuf.append(placeHolder.charAt(0));
                    handledPosition = delimIndex + 1;
                }
            } else {// 正常占位符
                sbuf.append(pattern, handledPosition, delimIndex);
                sbuf.append(argArray[argIndex]);
                handledPosition = delimIndex + placeHolderLength;
            }
        }

        // 加入最后一个占位符后所有的字符
        sbuf.append(pattern, handledPosition, strPatternLength);

        return sbuf.toString();
    }

}
