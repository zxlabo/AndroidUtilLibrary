package com.labo.utils.ext

import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

/**
 * author : yaoyakun
 * desc   : 时间格式工具类
 * getDateYMD：          日期格式化
 * dateYMD2TimeStamp：   日期格式化字符串转成时间戳
 */

/**
 * 日期格式化
 * @see [DateUtils.getDateFormatResult] [DateFormat.DateFormatYMD]
 */
fun String?.getDateYMD() = DateUtils.getDateFormatResult(DateFormat.DateFormatYMD(this.orEmpty()))
fun Long.getDateYMD() = this.toString().getDateYMD()
fun BigDecimal.getDateYMD() = this.toString().getDateYMD()

/**
 * 日期格式化字符串转成时间戳
 * @see [DateUtils.date2TimeStamp] [DateFormat.DateFormatYMD]
 */
fun String?.dateYMD2TimeStamp() = DateUtils.date2TimeStamp(DateFormat.DateFormatYMD(this.orEmpty()))

/**
 * 日期格式化
 * @see [DateUtils.getDateFormatResult] [DateFormat.DateFormatYMDHM]
 */
fun String?.getDateYMDHM() = DateUtils.getDateFormatResult(DateFormat.DateFormatYMDHM(this.orEmpty()))
fun Long.getDateYMDHM() = this.toString().getDateYMDHM()
fun BigDecimal.getDateYMDHM() = this.toString().getDateYMDHM()

/**
 * 日期格式化字符串转成时间戳
 * @see [DateUtils.date2TimeStamp] [DateFormat.DateFormatYMDHM]
 */
fun String?.dateYMDHM2TimeStamp() = DateUtils.date2TimeStamp(DateFormat.DateFormatYMDHM(this.orEmpty()))


/**
 * 日期格式化
 * @see [DateUtils.getDateFormatResult] [DateFormat.DateFormatYMDHMS]
 */
fun String?.getDateYMDHMS() = DateUtils.getDateFormatResult(DateFormat.DateFormatYMDHMS(this.orEmpty()))
fun Long.getDateYMDHMS() = this.toString().getDateYMDHMS()
fun BigDecimal.getDateYMDHMS() = this.toString().getDateYMDHMS()

/**
 * 日期格式化字符串转成时间戳
 * @see [DateUtils.date2TimeStamp] [DateFormat.DateFormatYMDHMS]
 */
fun String?.dateYMDHMS2TimeStamp() = DateUtils.date2TimeStamp(DateFormat.DateFormatYMDHMS(this.orEmpty()))


sealed class DateFormat(var format: String) {

    abstract val date: String?

    data class DateFormatYMD(override val date: String) : DateFormat("yyyy-MM-dd")
    data class DateFormatYMDHM(override val date: String) : DateFormat("yyyy-MM-dd hh:mm")
    data class DateFormatYMDHMS(override val date: String) : DateFormat("yyyy-MM-dd hh:mm:ss")
}

internal object DateUtils {

    fun getDateFormatResult(dateFormat: DateFormat): String? =
        when (dateFormat) {
            is DateFormat.DateFormatYMD,
            is DateFormat.DateFormatYMDHM,
            is DateFormat.DateFormatYMDHMS ->
                try {
                    val dateFormatter = SimpleDateFormat(dateFormat.format, Locale.CHINA)
                    val dateResult = dateFormat.date?.toLongOrNull()
                    if (dateResult == null) {
                        null
                    } else {
                        dateFormatter.format(dateResult)
                    }
                } catch (e: Exception) {
                    null
                }
        }


    /**
     * 日期格式字符串转换成时间戳
     * @return
     */
    fun date2TimeStamp(dateFormat: DateFormat): Long? =
        when (dateFormat) {
            is DateFormat.DateFormatYMD,
            is DateFormat.DateFormatYMDHM,
            is DateFormat.DateFormatYMDHMS ->
                try {
                    val dateFormatter = SimpleDateFormat(dateFormat.format, Locale.CHINA)
                    dateFormatter.parse(dateFormat.date).time
                } catch (e: Exception) {
                    System.currentTimeMillis()
                }
        }
}

