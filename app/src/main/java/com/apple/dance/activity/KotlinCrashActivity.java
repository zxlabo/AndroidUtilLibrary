package com.apple.dance.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.apple.dance.CrashUtils;
import com.apple.dance.R;

import java.util.List;

/**
 * 梳理常见的kotlin-crash
 */
public class KotlinCrashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kotlin_crash);
        /**
         * java.lang.NullPointerException:
         * Parameter specified as non-null is null: method kotlin.jvm.internal.Intrinsics.checkNotNullParameter
         */
        findViewById(R.id.btn_1).setOnClickListener(v -> CrashUtils.INSTANCE.printMsg(null));
        /**
         * java.lang.UnsupportedOperationException:
         * Operation is not supported for read-only collection
         */
        findViewById(R.id.btn_2).setOnClickListener(v -> CrashUtils.INSTANCE.getList().add("hello crash"));
    }

}