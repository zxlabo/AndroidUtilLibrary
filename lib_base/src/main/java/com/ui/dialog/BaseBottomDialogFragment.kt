package com.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.ViewGroup
import com.labo.utils.R

/**
 * author : Naruto
 */
open class BaseBottomDialogFragment : BaseDialogFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dialog = dialog
        dialog?:return
        if (dialog.window != null) {
            dialog.window?.decorView?.setPadding(0,0,0,0)
            dialog.window?.setGravity(Gravity.BOTTOM)
            val lp = dialog.window?.attributes
            val window = getDialog()?.window
            if (window != null) {
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val dm = DisplayMetrics()
                activity?.windowManager?.defaultDisplay?.getMetrics(dm)
                lp?.gravity = Gravity.BOTTOM
                lp?.width = ViewGroup.LayoutParams.MATCH_PARENT
                lp?.height = ViewGroup.LayoutParams.WRAP_CONTENT
                window.attributes = lp
                window.setWindowAnimations(R.style.DialogBottomInOut)
            }
        }
    }

}