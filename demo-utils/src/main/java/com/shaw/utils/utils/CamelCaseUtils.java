package com.shaw.utils.utils;

/**
 * CamelCaseUtils.toCamelCase("hello_world") == "helloWorld" CamelCaseUtils.
 * toCapitalizeCamelCase("hello_world") == "HelloWorld"
 * CamelCaseUtils.toUnderScoreCase("helloWorld") = "hello_world"
 *
 * @author xiaohong.zhou
 * @version 1.0
 * @date 2013-10-15
 * @see
 * @since
 */
public class CamelCaseUtils {

    private static final char SEPARATOR = '_';

    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        boolean hasLowerCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            }
        }
        if (!hasLowerCase)
            return s;

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static void main(String[] args) {
        System.out.println(CamelCaseUtils.toCamelCase("NAME_ID"));
    }
}
