package com.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.library.R
import com.ui.tab.top.TabTopBean
import com.ui.tab.top.TabTopLayout

class TabTopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_top)
        initTabTop()
    }

    var tabsStr = arrayOf(
        "热门",
        "服装",
        "数码",
        "鞋子",
        "零食",
        "家电",
        "汽车",
        "百货",
        "家居",
        "装修",
        "运动"
    )


    private fun initTabTop() {
        val hiTabTopLayout = findViewById<TabTopLayout>(R.id.tab_top_layout)
        val infoList: MutableList<TabTopBean<*>> = ArrayList()
        val defaultColor = resources.getColor(R.color.tabBottomDefaultColor)
        val tintColor = resources.getColor(R.color.tabBottomTintColor)
        for (s in tabsStr) {
            val info: TabTopBean<*> = TabTopBean(s, defaultColor, tintColor)
            infoList.add(info)
        }
        hiTabTopLayout.inflateInfo(infoList)
//        hiTabTopLayout.addTabSelectedChangeListener { index: Int, prevInfo: TabTopBean<*>?, nextInfo: TabTopBean<*> ->
//            Toast.makeText(
//                this@TabTopActivity,
//                nextInfo.name,
//                Toast.LENGTH_SHORT
//            ).show()
//        }
        hiTabTopLayout.defaultSelected(infoList[0])
    }
}