package com.apple.dance.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.apple.dance.R;
import com.ui.dialog.BaseCenterDialogFragment;

public class SerializableDialog extends BaseCenterDialogFragment {
    public static String dialogTag = "permission_dialog";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_log_demo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //person.student类是没有实现 Serializable 接口的，但这样使用是没有问题的。
//        // 但是，将应用切换到后台。系统会对 Serializable数据进行序列化到磁盘中，此时会抛异常。
//        Person person= (Person) getArguments().getSerializable("key");
//        Log.e("====", person.student.age+"");

    }

    public void  closeDialog(AppCompatActivity activity) {
        DialogFragment dialog = (DialogFragment) activity.getSupportFragmentManager().findFragmentByTag(dialogTag);
        if (dialog != null&&dialog.isAdded()) {
            dismissAllowingStateLoss();
        }
    }

    public static SerializableDialog getInstance(){
        SerializableDialog dialog=new SerializableDialog();
        Bundle bundle = new Bundle();
//        //1、使用putSerializable进行传值
//        bundle.putSerializable("key",person);
//        dialog.setArguments(bundle);
        return dialog;
    }


}
