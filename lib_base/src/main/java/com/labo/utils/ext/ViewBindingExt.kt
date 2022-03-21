package com.labo.utils.ext

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * author : Naruto
 * date   : 2021/5/13
 * desc   : 对Activity、Fragment、Dialog的ViewBinding进行封装
 */

/**
 * 对Activity的ViewBinding的封装
 */
inline fun <reified VB : ViewBinding> Activity.inflate() = lazy {
    inflateBinding<VB>(layoutInflater)
}

inline fun <reified VB : ViewBinding> Activity.inflateView() = lazy {
    inflateBinding<VB>(layoutInflater).apply {
        setContentView(root)
    }
}

/**
 * 对Dialog的ViewBinding的封装
 */
inline fun <reified VB : ViewBinding> Dialog.inflateView() = lazy {
    inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified VB : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
    VB::class.java.getMethod("inflate", LayoutInflater::class.java)
        .invoke(null, layoutInflater) as VB

/**
 * 对Fragment的ViewBinding的封装
 */
inline fun <reified VB : ViewBinding> Fragment.bindView() =
    FragmentBindingDelegate(VB::class.java)

inline fun Fragment.doOnDestroyView(crossinline block: () -> Unit) =
    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            block.invoke()
        }
    })

class FragmentBindingDelegate<VB : ViewBinding>(private val clazz: Class<VB>) :
    ReadOnlyProperty<Fragment, VB> {

    private var binding: VB? = null

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (binding == null) {
            binding = clazz.getMethod("bind", View::class.java)
                .invoke(null, thisRef.requireView()) as VB
            thisRef.doOnDestroyView { binding = null }
        }
        return binding!!
    }
}

/**
 * 对RecyclerView的ViewBinding的封装
 */
class BindingViewHolder<VB : ViewBinding>(val binding: VB) :
    RecyclerView.ViewHolder(binding.root)

inline fun <reified T : ViewBinding> newBindingViewHolder(parent: ViewGroup): BindingViewHolder<T> {
    val method = T::class.java.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    )
    val binding = method.invoke(null, LayoutInflater.from(parent.context), parent, false) as T
    return BindingViewHolder(binding)
}



