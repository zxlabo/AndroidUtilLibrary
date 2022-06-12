package com.labo.lib.tool.utils.ext

import java.lang.Exception
import java.math.BigDecimal

/**
 * author : yaoyakun
 * desc   : 数值工具类
 * getNumber  ： 获取小数，并至少保留一位小数
 */

/**
 * 获取小数，并至少保留一位小数
 */
fun Double.getNumber(): String =
    try {
        var result = BigDecimal.valueOf(this).stripTrailingZeros().toString()
        if (!result.contains(".")) {
            result += ".0"
        }
        result
    } catch (e: Exception) {
        ""
    }