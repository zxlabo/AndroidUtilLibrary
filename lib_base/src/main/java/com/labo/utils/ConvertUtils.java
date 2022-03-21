package com.labo.utils;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * detail: 转换工具类 ( Byte、Hex 等 )
 * @author Ttt
 * <pre>
 *     byte 是字节数据类型、有符号型的、占 1 个字节、大小范围为 [ -128 - 127]
 *     当大于 127 时则开始缩进 127 = 127, 128 = -128, 129 = -127
 *     char 是字符数据类型、无符号型的、占 2 个字节 (unicode 码 )、大小范围为 [0 - 65535]
 *     <p></p>
 *     Binary( 二进制 ) toBinaryString
 *     Oct( 八进制 )
 *     Dec( 十进制 )
 *     Hex( 十六进制 ) 以 0x 开始的数据表示十六进制
 *     <p></p>
 *     位移加密: bytesBitwiseAND(byte[] bytes)
 *     @see <a href="http://www.runoob.com/java/java-operators.html"/>
 * </pre>
 */
public final class ConvertUtils {

    private ConvertUtils() {
    }

    // 日志 TAG
    private static final String TAG = ConvertUtils.class.getSimpleName();

    /**
     * byte[] 转为 Object
     * @param bytes byte[]
     * @return {@link Object}
     */
    public static Object bytesToObject(final byte[] bytes) {
        if (bytes == null) return null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        } catch (Exception e) {
        } finally {
            CloseUtils.closeIOQuietly(ois);
        }
        return null;
    }
    /**
     * Object 转 Integer
     * @param value Value
     * @return Integer
     */
    public static Integer toInt(final Object value) {
        return toInt(value, 0);
    }
    /**
     * Object 转 Integer
     * @param value        Value
     * @param defaultValue 默认值
     * @return Integer, 如果转换失败则返回 defaultValue
     */
    public static Integer toInt(final Object value, final Integer defaultValue) {
        if (value == null) return defaultValue;
        try {
            if (value instanceof Integer) {
                return (Integer) value;
            }
            if (value instanceof Number) {
                return ((Number) value).intValue();
            }
            if (value instanceof String) {
                String strVal = (String) value;
                if (strVal.indexOf(',') != 0) {
                    strVal = strVal.replaceAll(",", "");
                }
                return Integer.parseInt(strVal);
            }
            if (value instanceof Boolean) {
                return ((Boolean) value).booleanValue() ? 1 : 0;
            }
            throw new Exception("can not cast to int, value : " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}