package com.neko233.json.formatter;

public class JsonFormatter {

    public static String formatJson(String json) {
        int indentLevel = 0;
        StringBuilder formattedJson = new StringBuilder();
        boolean inQuotes = false;
        boolean isEscaped = false;

        for (char character : json.toCharArray()) {
            switch (character) {
                case '{':
                case '[':
                    formattedJson.append(character);
                    if (!inQuotes) {
                        formattedJson.append("\n");
                        appendIndentation(formattedJson, ++indentLevel);
                    }
                    break;
                case '}':
                case ']':
                    if (!inQuotes) {
                        formattedJson.append("\n");
                        appendIndentation(formattedJson, --indentLevel);
                    }
                    formattedJson.append(character);
                    break;
                case ',':
                    formattedJson.append(character);
                    if (!inQuotes) {
                        formattedJson.append("\n");
                        appendIndentation(formattedJson, indentLevel);
                    }
                    break;
                case ':':
                    formattedJson.append(character).append(" ");
                    break;
                case '"':
                    formattedJson.append(character);
                    isEscaped = false;
                    if (inQuotes) {
                        if (!isEscaped) {
                            inQuotes = false;
                        }
                    } else {
                        inQuotes = true;
                    }
                    break;
                case '\\':
                    formattedJson.append(character);
                    isEscaped = !isEscaped;
                    break;
                default:
                    formattedJson.append(character);
                    break;
            }
        }

        return formattedJson.toString();
    }

    private static void appendIndentation(StringBuilder stringBuilder, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            stringBuilder.append("\t");
        }
    }
}
