package com.ui.helper

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.labo.utils.ListUtils

/**
 * author : Naruto
 * date   : 2021/9/28
 * desc   :
 */
class FragmentListHelper(private val supportFragmentManager: FragmentManager,
    private val fragmentList: MutableList<Fragment>,
    @IdRes private val fragmentContent: Int) {

    private var lastFragment: Fragment? = null


    fun initFragment() {
        checkFragmentSafe()
        removeAllFragment()
    }

    fun chooseTab(index: Int) {
        if (index >= fragmentList.size) return
        val currentFragment = fragmentList[index]
        if (currentFragment == lastFragment) return
        switchSelectedFragment(currentFragment)
    }

    private fun removeAllFragment() {
        checkFragmentSafe()
        val transaction = supportFragmentManager.beginTransaction()
        fragmentList.forEach {
            supportFragmentManager.findFragmentByTag(it::class.java.name)?.apply {
                transaction.remove(this)
            }
        }
        transaction.commitAllowingStateLoss()
    }

    private fun switchSelectedFragment(currentFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        if (currentFragment.isAdded) {
            lastFragment?.let { transaction.hide(it) }
            transaction.show(currentFragment)
        } else {
            lastFragment?.let { transaction.hide(it) }
            transaction.add(fragmentContent, currentFragment, currentFragment::class.java.name)
        }
        transaction.commitAllowingStateLoss()
        lastFragment = currentFragment
    }

    private fun checkFragmentSafe() {
        if (ListUtils.isEmpty(fragmentList)) {
            throw NullPointerException("fragmentList is null")
        }
    }

}