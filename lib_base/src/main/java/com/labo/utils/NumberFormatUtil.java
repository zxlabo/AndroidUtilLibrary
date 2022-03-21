package com.labo.utils;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * parseDouble      四舍五入转换成整型
 * parseBigDecimal  四舍五入转换成整型
 * changeOneDecimal 保留一位小数
 * parseDouble      四舍五入转换成整型
 */
public class NumberFormatUtil {


    private NumberFormatUtil() {
    }

    /**
     * Double型 四舍五入转换成整型
     */
    public static String parseDouble(Double number) {
        return String.format(Locale.ROOT, "%.0f", number);
    }

    /**
     * Double型 四舍五入转换成整型
     */
    public static String parseBigDecimal(BigDecimal number) {
        return String.format(Locale.ROOT, "%.0f", number);
    }

    /**
     * 保留一位小数
     * setScale(1)表示保留一位小数，默认用四舍五入方式
     * setScale(1,BigDecimal.ROUND_DOWN)直接删除多余的小数位，如2.35会变成2.3
     * setScale(1,BigDecimal.ROUND_UP)进位处理，2.35变成2.4
     * setScale(1,BigDecimal.ROUND_HALF_UP)四舍五入，2.35变成2.4
     * setScaler(1,BigDecimal.ROUND_HALF_DOWN)四舍五入，2.35变成2.3，如果是5则向下舍
     * 原文链接：https://blog.csdn.net/flyjiang2014/article/details/78908373
     */
    public static double changeOneDecimal(double value) {
        BigDecimal b1 = new BigDecimal(value);
        return b1.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * Double型 四舍五入转换成整型
     *
     * @param factor 精度
     */
    public static double parseDouble(double number, int factor) {
        BigDecimal b1 = new BigDecimal(number);
        return b1.setScale(factor, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
