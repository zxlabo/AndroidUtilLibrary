package com.labo.lib.tool.utils.ext

import android.graphics.Rect
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

/**
 * author : Naruto
 * desc   : RecyclerView相关的扩展函数
 * blog   :https://juejin.cn/post/6881427923316768776/
 * setOnItemClickListener:  设置item点击事件
 * setOnItemClickListener:  item的子控件的点击事件
 */

/**
 * 使用
    fun demo(recyclerView:RecyclerView){
        recyclerView?.setOnItemClickListener { view, i, x, y ->
            view.onChildViewClick("tvChatroom", "tvCount", x = x, y = y) {
                // 表项子控件tvChatroom和tvCount组成的并集矩形区域被点击
                return@setOnItemClickListener
            }
        }
    }
 */

/**
 * 为 RecyclerView 扩展表项点击监听器，仿照ListView。
 * 判断触点坐标是否落在表项矩形区域内，获得Child
 */
//为 RecyclerView 扩展表项点击监听器
fun RecyclerView.setOnItemClickListener(listener: (View, Int) -> Unit) {
    //'为 RecyclerView 子控件设置触摸监听器'
    addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
        //'构造手势探测器，用于解析单击事件'
        val gestureDetector = GestureDetector(context, object : GestureDetector.OnGestureListener {
            override fun onShowPress(e: MotionEvent?) {
            }

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                //'当单击事件发生时，寻找单击坐标下的子控件，并回调监听器'
                e?.let {
                    findChildViewUnder(it.x, it.y)?.let { child ->
                        listener(child, getChildAdapterPosition(child))
                    }
                }
                return false
            }

            override fun onDown(e: MotionEvent?): Boolean {
                return false
            }

            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                return false
            }

            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                return false
            }

            override fun onLongPress(e: MotionEvent?) {
            }
        })

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

        }

        //'在拦截触摸事件时，解析触摸事件'
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            gestureDetector.onTouchEvent(e)
            return false
        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        }
    })
}
/**
 * item的子控件的点击事件
 */
fun RecyclerView.setOnItemClickListener(listener: (View, Int, Float, Float) -> Unit) {
    addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
        val gestureDetector = GestureDetector(context, object : GestureDetector.OnGestureListener {
            override fun onShowPress(e: MotionEvent?) {
            }

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                e?.let {
                    findChildViewUnder(it.x, it.y)?.let { child ->
                        val realX = if (child.left >= 0 ) it.x - child.left else it.x
                        val realY = if (child.top >= 0) it.y - child.top else it.y
                         listener(child, getChildAdapterPosition(child), realX, realY)
                    }
                }
                return false
            }

            override fun onDown(e: MotionEvent?): Boolean {
                return false
            }

            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                return false
            }

            override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent?,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                return false
            }

            override fun onLongPress(e: MotionEvent?) {
            }
        })

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            gestureDetector.onTouchEvent(e)
            return false
        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        }
    })
}

/**
 * layoutId：View的子控件Id（若输入多个则表示多个控件所组成的并集矩形区域）
 * x：触点横坐标
 * y： 触点纵坐标
 * clickAction：子控件点击响应事件
 */
inline fun View.onChildViewClick(
    vararg layoutId: String, x: Float, y: Float,
    clickAction: ((View?) -> Unit)
) {
    var clickedView: View? = null
    // 遍历所有子控件id
    layoutId
        .map { id ->
            // 根据id查找出子控件实例
            find<View>(id)?.let { view ->
                // 获取子控件相对于父控件的矩形区域
                view.getRelativeRectTo(this).also { rect ->
                    // 如果矩形区域包含触点则表示子控件被点击（记录被点击的子控件）
                    if (rect.contains(x.toInt(), y.toInt())) {
                        clickedView = view
                    }
                }
            } ?: Rect()
        }
        // 将所有子控件矩形区域做并集
        .fold(Rect()) { init, rect -> init.apply { union(rect) } }
        // 如果并集中包含触摸点，则表示并集所对应的大矩形区域被点击
        .takeIf { it.contains(x.toInt(), y.toInt()) }
        ?.let { clickAction.invoke(clickedView) }
}


fun View.getRelativeRectTo(otherView: View): Rect {
    val parentRect = Rect().also { otherView.getGlobalVisibleRect(it) }
    val childRect = Rect().also { getGlobalVisibleRect(it) }
    // 将 2个 Rect 做相对运算后返回一个新的 Rect
    return childRect.relativeTo(parentRect)
}

// Rect 相对运算（可以理解为将坐标原点进行平移）
fun Rect.relativeTo(otherRect: Rect): Rect {
    val relativeLeft = left - otherRect.left
    val relativeTop = top - otherRect.top
    val relativeRight = relativeLeft + right - left
    val relativeBottom = relativeTop + bottom - top
    return Rect(relativeLeft, relativeTop, relativeRight, relativeBottom)
}
/**
 *  listen click action for the child view of [RecyclerView]'s item
 */
inline fun View.onChildViewClick(
    vararg layoutId: Int, // the id of the child view of RecyclerView's item
    x: Float, // the x coordinate of click point
    y: Float,// the y coordinate of click point,
    clickAction: ((View?) -> Unit)
) {
    var clickedView: View? = null
    layoutId
        .map { id ->
            findViewById<View>(id)?.takeIf { it.visibility == View.VISIBLE }?.let { view ->
                view.getRelativeRectTo(this).also { rect ->
                    if (rect.contains(x.toInt(), y.toInt())) {
                        clickedView = view
                    }
                }
            } ?: Rect()
        }
        .fold(Rect()) { init, rect -> init.apply { union(rect) } }
        .takeIf { it.contains(x.toInt(), y.toInt()) }
        ?.let { clickAction.invoke(clickedView) }
}
fun <T : View> View.find(id: String): T? = findViewById(id.toLayoutId())

fun <T : View> AppCompatActivity.find(id: String): T? = findViewById(id.toLayoutId())

val parent_id = "0"

fun String.toLayoutId(): Int {
    var id = hashCode()
    if (this == parent_id) id = 0
    return abs(id)
}
