package com.shaw.utils.utils;


import com.shaw.utils.text.StringUtils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * 提供精确的浮点计算
 */
public class MathUtils {
    // 默认除法运算精度
    public static final int DEF_DIV_SCALE = 10;
    public static final int DEFAUTL_SCALE = 2;
    private static final String DEFAUTL_VALUE_STRING = "0";
    private String result;

    // 这个类不能实例化
    private MathUtils() {
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static String add(String... vs) {
        BigDecimal value = new BigDecimal(DEFAUTL_VALUE_STRING);
        for (String v : vs) {
            BigDecimal b = new BigDecimal(StringUtils.defaultIfEmpty(v, DEFAUTL_VALUE_STRING));
            value = value.add(b);
        }
        return value.toString();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static String sub(String v1, String... v2) {
        BigDecimal b1 = new BigDecimal(StringUtils.defaultIfEmpty(v1, DEFAUTL_VALUE_STRING));
        for (String v : v2) {
            BigDecimal b2 = new BigDecimal(StringUtils.defaultIfEmpty(v, DEFAUTL_VALUE_STRING));
            b1 = b1.subtract(b2);
        }
        return b1.toString();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static String mul(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(StringUtils.defaultIfEmpty(v1, DEFAUTL_VALUE_STRING));
        BigDecimal b2 = new BigDecimal(StringUtils.defaultIfEmpty(v2, DEFAUTL_VALUE_STRING));
        return b1.multiply(b2).toString();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static String mul(String v1, String v2, int scale) {
        BigDecimal b1 = new BigDecimal(StringUtils.defaultIfEmpty(v1, DEFAUTL_VALUE_STRING));
        BigDecimal b2 = new BigDecimal(StringUtils.defaultIfEmpty(v2, DEFAUTL_VALUE_STRING));
        return round(b1.multiply(b2).toString(), scale);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static String div(String v1, String v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static String div(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(StringUtils.defaultIfEmpty(v1, DEFAUTL_VALUE_STRING));
        BigDecimal b2 = new BigDecimal(StringUtils.defaultIfEmpty(v2, DEFAUTL_VALUE_STRING));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String round(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(StringUtils.defaultIfEmpty(v, DEFAUTL_VALUE_STRING));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double... vs) {
        BigDecimal value = new BigDecimal(DEFAUTL_VALUE_STRING);
        for (double v : vs) {
            BigDecimal b = new BigDecimal(Double.toString(v));
            value = value.add(b);
        }
        return value.doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double... v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        for (double v : v2) {
            BigDecimal b2 = new BigDecimal(Double.toString(v));
            b1 = b1.subtract(b2);
        }
        return b1.doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return round(b1.multiply(b2).doubleValue(), scale);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param b     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(BigDecimal b, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param b 需要四舍五入的数字
     * @return 四舍五入后的结果
     */
    public static BigDecimal roundDefault(BigDecimal b) {
        return round(b, DEFAUTL_SCALE);
    }

    /**
     * 提供精确的小数位四舍五入处理。默认两位小数
     *
     * @param v 需要四舍五入的数字
     * @return 四舍五入后的结果
     */
    public static double roundDefault(double v) {
        return round(v, DEFAUTL_SCALE);
    }

    /**
     * 小数转化成百分比。默认两位小数
     *
     * @param v 需要转化成百分比的数字
     * @return 转化成百分比的结果
     */
    public static String percent(double v) {
        return round(100 * v, DEFAUTL_SCALE) + "%";
    }

    /**
     * 判断输入的字符串<code>source</code>是否是有效的数字
     *
     * @param source 需要判断的字符串
     * @return true | false
     */
    public static boolean isNumeric(String source) {
        AssertUtils.hasText(source);
        for (int i = source.length(); --i >= 0; ) {
            if (!Character.isDigit(source.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断输入的字符串<code>source</code>是否是有效的数字,包括整数，浮点型数
     *
     * @param source 需要判断的字符串
     * @return True | False
     */
    public static boolean isFloatNumeric(String source) {
        if (StringUtils.isBlank(source))
            return false;
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return isNumeric(source) || pattern.matcher(source).matches();
    }

    public static MathUtils build() {
        return new MathUtils();
    }

    public MathUtils add(String value) {
        result = add(result, value);
        return this;
    }

    public MathUtils add(double value) {
        result = add(result, TypeUtils.toString(value));
        return this;
    }

    public MathUtils sub(String value) {
        result = sub(result, value);
        return this;
    }

    public MathUtils sub(double value) {
        result = sub(result, TypeUtils.toString(value));
        return this;
    }

    public MathUtils mul(String value) {
        result = mul(result, value);
        return this;
    }

    public MathUtils mul(double value) {
        result = mul(result, TypeUtils.toString(value));
        return this;
    }

    public MathUtils div(String value) {
        result = div(result, value);
        return this;
    }

    public MathUtils div(double value) {
        result = div(result, TypeUtils.toString(value));
        return this;
    }

    public String resultToString() {
        return result;
    }

    public String resultToString(int scale) {
        return round(result, scale);
    }

    public double resultToDouble() {
        return TypeUtils.toDouble(result);
    }

    public double resultToDouble(int scale) {
        return TypeUtils.toDouble(round(result, scale));
    }

}
