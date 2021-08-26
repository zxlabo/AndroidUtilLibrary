package com.utils.ext

import android.widget.ImageView
import kotlinx.coroutines.launch

/**
 * author : Naruto
 * date   : 2021/6/8
 * desc   :
 */
/**
 * 协程内加载图片
 */
fun ImageView.load(url: String) {
    viewScope.launch {
//        val bitmap = Glide.with(context).asBitmap().load(url).submit().get()
//        withContext(Dispatchers.Main) { setImageBitmap(bitmap) }
    }
}

