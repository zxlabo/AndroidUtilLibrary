package com.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected var layoutView: View? = null

    @get:LayoutRes
    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutView = inflater.inflate(layoutId, container, false)
        return layoutView
    }

    //检测 宿主 是否还存活
    val isAlive: Boolean
        get() = !(isRemoving || isDetached || activity == null)

}