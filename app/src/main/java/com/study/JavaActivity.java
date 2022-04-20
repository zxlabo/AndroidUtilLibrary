package com.study;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.library.R;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScheduleTask();
            }
        });

    }

    private long DELAY = 5L;
    private long PERIOD = 5L;

    ScheduledExecutorService executorService;

    private void startScheduleTask() {
        if (executorService == null) {
            executorService = new ScheduledThreadPoolExecutor(1);
            executorService.scheduleAtFixedRate(this::uploadImInfo, DELAY, PERIOD, TimeUnit.SECONDS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            if (!executorService.isShutdown()) {
                executorService.shutdownNow();
            }
            executorService = null;
        }
    }


    private void uploadImInfo() {
        Log.e("====", "aaa");
    }
}