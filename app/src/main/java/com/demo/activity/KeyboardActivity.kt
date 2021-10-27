package com.demo.activity

import android.os.Bundle
import android.os.PersistableBundle
import com.utils.SoftHideKeyBoardUtil
import com.library.R
import com.ui.activity.BaseToolBarActivity

/**
 *  软键盘:https://blog.csdn.net/l540675759/article/details/74528641
 *  当是非滚动布局时：
 *      使用adjustPan：键盘不会遮挡布局：会将主窗口进行平移，来适应软键盘的显示。主窗口意味着：标题栏也会向上移动。
 *      使用adjustResize：键盘会遮盖布局，布局会为了软键盘弹出而重新绘制给软键盘留出空间，而由于控件无法滑动，所以表现的形式与adjustNoting一致。
 *  当是滚动布局时：
 *      使用adjustPan：键盘不会遮挡布局：会将主窗口进行平移，来适应软键盘的显示。主窗口意味着：标题栏也会向上移动。
 *      使用adjustResize：键盘不会遮挡布局：当软键盘弹出时，ScrollView会重新绘制，然后滚动到焦点位置位置，使其显示在软键盘之上。
 *          当我们设置沉浸式状态栏，此时会失效。
 */
class KeyboardActivity : BaseToolBarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keyboard)
        SoftHideKeyBoardUtil.assistActivity(this)

        supportFragmentManager.beginTransaction().commit()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }
}