package com.apple.dance.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.apple.dance.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_bottom_sheet.*

class BottomSheetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet)
        val behavior = BottomSheetBehavior.from(bottom_sheet)
        behavior.isFitToContents = false
        behavior.state=BottomSheetBehavior.STATE_HALF_EXPANDED
            //设置half状态下展开的比例
    // behavior.halfExpandedRatio = 0.6f
    }
}