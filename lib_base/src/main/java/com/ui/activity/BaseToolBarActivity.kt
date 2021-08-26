package com.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StyleRes
import com.ui.helper.ToolBarHelper
import com.utils.BarUtils
import com.utils.databinding.ActivityBaseBinding

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
abstract class BaseToolBarActivity : BaseLifecycleActivity() {

    private lateinit var mBinding: ActivityBaseBinding
    protected val mActivity by lazy { this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityBaseBinding.inflate(layoutInflater)
        super.setContentView(mBinding.root)
        ToolBarHelper.setTranslucentStatus(window)
    }

    override fun setContentView(view: View?) {
        initToolBar(view)
    }

    override fun setContentView(layoutId: Int) {
        setContentView(View.inflate(this, layoutId, null))
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
    /**
     * 设置状态栏颜色
     */
    abstract fun getAppBarColor(): Int

    /**
     * 设置标题栏颜色
     */
    abstract fun getToolBarColor(): Int

    fun getAppBar()=mBinding.toolsBar.appbar

    fun hideAppBar() {
        mBinding.toolsBar.appbar.visibility = View.GONE
    }

    fun getToolBar()= mBinding.toolsBar.toolbar

    fun hideToolBar() {
        mBinding.toolsBar.toolbar.visibility = View.GONE
    }
    fun setToolBarTitleTextAppearance(@StyleRes resId: Int) {
        mBinding.toolsBar.toolbar.setTitleTextAppearance(mActivity, resId)
    }

    /**
     * 对ToolBar的封装
     */
    private fun initToolBar(view: View?) {
        view ?: return
        //添加view
        mBinding.rootLayout.addView(view, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        //设置标题栏
        ToolBarHelper.setToolBar(mActivity, mBinding.toolsBar.appbar, mBinding.toolsBar.toolbar, getAppBarColor(), getToolBarColor())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // 点击返回图标事件
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}