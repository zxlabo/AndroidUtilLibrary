package com.utils.ext

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView

/**
 * author : Naruto
 * desc   : EditText 工具类
 * afterTextChanged：            EditText输入完之后监听
 * onActionSearchListener：      action监听
 * setTextAndSelectEnd：         设置选择的最后一位
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun EditText.onActionSearchListener(listener: (String) -> Unit) {
    this.setOnEditorActionListener { _: TextView?, actionId: Int, _: KeyEvent? ->
        val keyword: String = text.toString().trim()
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (!TextUtils.isEmpty(keyword)) {
                listener.invoke(keyword)
            }
        }
        (actionId == EditorInfo.IME_ACTION_SEARCH
                && !TextUtils.isEmpty(keyword))
    }
}

fun EditText.setTextAndSelectEnd(txt: String?) {
    if (txt.isNullOrEmpty()) return
    setText(txt)
    setSelection(txt.length)
}
