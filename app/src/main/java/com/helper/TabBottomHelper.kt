package com.helper

import android.graphics.BitmapFactory
import com.labo.library.R
import com.ui.tab.bottom.TabBottomBean
import com.ui.tab.bottom.TabBottomLayout
import com.labo.utils.AppGlobals
import com.labo.utils.SizeUtils

/**
 * author : Naruto
 * date   : 2021/8/27
 * desc   :
 */
class TabBottomHelper(val chooseTab: (index: Int) -> Unit) {

    fun initTabBottom(tabBottomLayout: TabBottomLayout) {
        tabBottomLayout.setTabAlpha(0.85f)
        val bottomInfoList: MutableList<TabBottomBean<*>> = getTabBottomList()
        tabBottomLayout.inflateInfo(bottomInfoList)
        tabBottomLayout.addTabSelectedChangeListener { index, _, _ ->
            chooseTab(index)
        }
        tabBottomLayout.defaultSelected(bottomInfoList[0])
        //改变某个tab的高度
        val tabBottom = tabBottomLayout.findTab(bottomInfoList[2])
        tabBottom?.apply { resetHeight(SizeUtils.dp2px(66f)) }
    }

    private fun getTabBottomList(): MutableList<TabBottomBean<*>> {
        val context = AppGlobals.getContext()!!
        val bottomInfoList: MutableList<TabBottomBean<*>> = ArrayList()
        val homeInfo = TabBottomBean(
            "首页", "fonts/iconfont.ttf", context.getString(R.string.if_home), null, "#ff656667", "#ffd44949")
        val infoRecommend = TabBottomBean("收藏", "fonts/iconfont.ttf", context.getString(R.string.if_favorite), null, "#ff656667", "#ffd44949")
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.fire, null)
        val infoCategory = TabBottomBean<String>("分类", bitmap, bitmap)
        val infoChat = TabBottomBean("推荐", "fonts/iconfont.ttf", context.getString(R.string.if_recommend), null, "#ff656667", "#ffd44949")
        val infoProfile = TabBottomBean("我的", "fonts/iconfont.ttf", context.getString(R.string.if_profile), null, "#ff656667", "#ffd44949")
        bottomInfoList.add(homeInfo)
        bottomInfoList.add(infoRecommend)
        bottomInfoList.add(infoCategory)
        bottomInfoList.add(infoChat)
        bottomInfoList.add(infoProfile)
        return bottomInfoList
    }
}