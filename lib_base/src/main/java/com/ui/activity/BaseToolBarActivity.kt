package com.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.labo.utils.BarUtils
import com.labo.utils.databinding.ActivityBaseBinding

/**
 * author : Naruto
 * date   : 2019-10-22
 * desc   :
1、需要引入 viewBinding
buildFeatures{
viewBinding = true
}
2、因为我们自己添加了toolbar，所以要设置主题是NoActionBar
<item name="windowActionBar">false</item>
<item name="windowNoTitle">true</item>
3、如果需要自定义，可以隐藏标题栏，在xml里面添加标题栏。
4、AppBarLayout作用设置状态栏和padding
 */
open class BaseToolBarActivity : BaseLifecycleActivity() {

    private lateinit var mBinding: ActivityBaseBinding
    protected val mActivity by lazy { this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityBaseBinding.inflate(layoutInflater)
        ToolBarHelper.setTranslucentStatus(window)
    }

    override fun setContentView(layoutId: Int) {
        setContentView(View.inflate(this, layoutId, null))
    }

    override fun setContentView(view: View?) {
        initToolBar(view)
    }

    /**
     * 设置状态栏是否为浅色模式
     */
    fun setStatusBarLightMode(lightMode: Boolean) {
        BarUtils.setStatusBarLightMode(this, lightMode)
    }

    /**
     * 设置状态栏颜色
     */
    @SuppressLint("ObsoleteSdkInt")
    fun setStatusBarColor(color: Int) {
        ToolBarHelper.setStatusBarColor(window, color)
    }

    fun hideAppBar() {
        mBinding.toolsBar.appbar.visibility = View.GONE
    }

    fun hideToolBar() {
        mBinding.toolsBar.toolbar.visibility = View.GONE
    }

    /**
     * 对ToolBar的封装
     */
    private fun initToolBar(view: View?) {
        super.setContentView(mBinding.root)
        view ?: return
        //添加view
        mBinding.rootLayout.addView(view, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        //设置标题栏
        ToolBarHelper.setToolBar(mActivity,
            mBinding.toolsBar.appbar,
            mBinding.toolsBar.toolbar,
            getAppBarColor(),
            getToolBarColor(),
            isShowBackButton())
    }

    open fun isShowBackButton(): Boolean = true

    /**
     * 设置状态栏颜色
     */
    open fun getAppBarColor(): Int {
        return Color.parseColor("#03a9f4")
    }

    /**
     * 设置标题栏颜色
     */
    open fun getToolBarColor(): Int {
        return Color.parseColor("#03a9f4")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // 点击返回图标事件
            android.R.id.home -> {
                backClick()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    open fun backClick() {
        finish()
    }

}