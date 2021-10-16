package com.ui.adapter

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

open class CommonViewHolder(val view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
    override val containerView: View?
        get() = view

    private var viewCache = SparseArray<View>()

    fun <T : View> findViewById(viewId: Int): T? {
        var view = viewCache.get(viewId)
        if (view == null) {
            view = itemView.findViewById<T>(viewId)
            viewCache.put(viewId, view)
        }
        return view as? T
    }

}