package com.utils.ext

import android.content.Context
import android.widget.Toast

/**
 * author : Naruto
 * date   : 2020-06-11
 * desc   :
 * version:
 */

fun Context.showToast(msg: String?) {
    msg ?: return
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}