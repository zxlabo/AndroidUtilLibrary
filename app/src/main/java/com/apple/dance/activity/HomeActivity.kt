package com.apple.dance.activity

import android.view.KeyEvent
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.apple.dance.databinding.ActivityHomeBinding
import com.common.base.BaseVmActivity
import com.common.utils.router.RouterConstant
import com.apple.dance.fragment.OneFragment
import com.apple.dance.helper.TabBottomHelper
import com.ui.helper.FragmentListHelper
import com.labo.utils.ext.inflate
import com.labo.utils.ext.showToast
import com.apple.dance.viewmodel.HomeVm

@Route(path = RouterConstant.APP_HOME)
class HomeActivity : BaseVmActivity<HomeVm>() {
    override val mVm: HomeVm by viewModels()
    private val mBinding: ActivityHomeBinding by inflate()
    private var mExitTime: Long = 0

    override fun initView() {
        setContentView(mBinding.root)
        hideToolBar()
        initTabBottom()
    }

    private fun initTabBottom() {
        val fragmentListHelper = FragmentListHelper(supportFragmentManager, getFragmentList(), mBinding.container.id)
        fragmentListHelper.initFragment()
        val tabBottomHelper = TabBottomHelper { fragmentListHelper.chooseTab(it) }
        tabBottomHelper.initTabBottom(mBinding.hitablayout)
    }

    private fun getFragmentList(): MutableList<Fragment> {
        val mFragmentList = mutableListOf<Fragment>()
        mFragmentList.add(OneFragment())
        mFragmentList.add(OneFragment())
        return mFragmentList
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime >= 2000) {
                showToast("再按一次退出应用")
                mExitTime = System.currentTimeMillis()
            } else {
                finish()
            }
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

}