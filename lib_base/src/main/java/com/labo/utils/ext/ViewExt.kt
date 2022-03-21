package com.labo.utils.ext

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Px
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * author : Naruto
 * desc   : View扩展函数
 * visible：
 * invisible：
 * gone：
 * reverseVisibility：
 * changeVisible：
 * isVisible：
 * isInvisible：
 * isGone：
 * setPadding：
 * postDelayed：
 * toBitmap
 * createBitmapSafely
 * onGlobalLayout
 * afterMeasured
 */

/**
 * Set view visible
 */
fun View.visible() {
    visibility = View.VISIBLE
}

/**
 * Set view invisible
 */
fun View.invisible() {
    visibility = View.INVISIBLE
}

/**
 * Set view gone
 */
fun View.gone() {
    visibility = View.GONE
}

/**
 * Reverse the view's visibility
 */
fun View.reverseVisibility(needGone: Boolean = true) {
    if (isVisible) {
        if (needGone) gone() else invisible()
    } else visible()
}

fun View.changeVisible(visible: Boolean, needGone: Boolean = true) {
    when {
        visible -> visible()
        needGone -> gone()
        else -> invisible()
    }
}

var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) = if (value) visible() else gone()

var View.isInvisible: Boolean
    get() = visibility == View.INVISIBLE
    set(value) = if (value) invisible() else visible()

var View.isGone: Boolean
    get() = visibility == View.GONE
    set(value) = if (value) gone() else visible()

/**
 * Set padding
 * @param size top, bottom, left, right padding are same
 */
fun View.setPadding(@Px size: Int) {
    setPadding(size, size, size, size)
}

/**
 * Causes the Runnable which contains action() to be added to the message queue, to be run
 * after the specified amount of time elapses.
 * The runnable will be run on the user interface thread
 *
 * @param action Will be invoked in the Runnable
 * @param delayInMillis The delay (in milliseconds) until the action() will be invoked
 */
inline fun View.postDelayed(delayInMillis: Long, crossinline action: () -> Unit): Runnable {
    val runnable = Runnable { action() }
    postDelayed(runnable, delayInMillis)
    return runnable
}

@Deprecated("use View.drawToBitmap()")
fun View.toBitmap(scale: Float = 1f, config: Bitmap.Config = Bitmap.Config.ARGB_8888): Bitmap? {
    if (this is ImageView) {
        if (drawable is BitmapDrawable) return (drawable as BitmapDrawable).bitmap
    }
    this.clearFocus()
    val bitmap = createBitmapSafely((width * scale).toInt(), (height * scale).toInt(), config, 1)
    if (bitmap != null) {
        Canvas().run {
            setBitmap(bitmap)
            save()
            drawColor(Color.WHITE)
            scale(scale, scale)
            this@toBitmap.draw(this)
            restore()
            setBitmap(null)
        }
    }
    return bitmap
}

fun createBitmapSafely(width: Int, height: Int, config: Bitmap.Config, retryCount: Int): Bitmap? {
    try {
        return Bitmap.createBitmap(width, height, config)
    } catch (e: OutOfMemoryError) {
        e.printStackTrace()
        if (retryCount > 0) {
            System.gc()
            return createBitmapSafely(width, height, config, retryCount - 1)
        }
        return null
    }

}

/**
 * Register a callback to be invoked when the global layout state or the visibility of views
 * within the view tree changes
 *
 * @param callback The callback() to be invoked
 */
inline fun View.onGlobalLayout(crossinline callback: () -> Unit) = with(viewTreeObserver) {
    addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        override fun onGlobalLayout() {
            removeOnGlobalLayoutListener(this)
            callback()
        }
    })
}

/**
 * Register a callback to be invoked after the view is measured
 *
 * @param callback The callback() to be invoked
 */
inline fun View.afterMeasured(crossinline callback: View.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                callback()
            }
        }
    })
}
val View.viewScope: CoroutineScope
    get() {
        // 获取现有 viewScope 对象
        val key = "ViewScope".hashCode()
        var scope = getTag(key) as? CoroutineScope
        // 若不存在则新建 viewScope 对象
        if (scope == null) {
            scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
            // 将 viewScope 对象缓存为 View 的 tag
            setTag(key,scope)
            val listener = object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View?) {
                }

                override fun onViewDetachedFromWindow(v: View?) {
                    // 当 view detach 时 取消协程的任务
                    scope.cancel()
                }

            }
            addOnAttachStateChangeListener(listener)
        }
        return scope
    }

fun TextView.setBoldText(defaultText: String?, boldMsg:String?, color: Int?, size: Int) {
    defaultText ?: return
    val textSpanned1 = SpannableString(defaultText)
    boldMsg?.let { doc ->
        val index = defaultText.indexOf(doc)
        if (index > -1) {
            color?.let {
                textSpanned1.setSpan(
                    ForegroundColorSpan(color),
                    index, index + doc.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE
                )
            }
            textSpanned1.setSpan(
                StyleSpan(Typeface.BOLD),
                index, index + doc.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE
            )
            textSpanned1.setSpan(
                AbsoluteSizeSpan(size, true),
                index, index + doc.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE
            )
        }
    }
    this.text = textSpanned1
}

fun TextView.setBoldText(defaultText: String?, boldList: List<String>?, color: Int?, size: Int?=null,isBold:Boolean=true) {
    defaultText ?: return
    val textSpanned1 = SpannableString(defaultText)
    boldList?.forEach { doc ->
        val indexMsg=defaultText
        var index = indexMsg.indexOf(doc)
        while (index > -1) {
            color?.let {
                textSpanned1.setSpan(
                    ForegroundColorSpan(color),
                    index, index + doc.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            if (isBold){
                textSpanned1.setSpan(
                    StyleSpan(Typeface.BOLD),
                    index, index + doc.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            size?.let {
                textSpanned1.setSpan(
                    AbsoluteSizeSpan(size, true),
                    index, index + doc.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            index=indexMsg.indexOf(doc,index+1)
        }
    }
    this.text = textSpanned1
}
