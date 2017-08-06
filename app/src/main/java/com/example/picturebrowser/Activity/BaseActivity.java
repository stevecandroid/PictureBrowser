package com.example.picturebrowser.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.picturebrowser.Application.MyApplication;

/**
 * Created by 铖哥 on 2017/5/19.
 */

public class BaseActivity extends AppCompatActivity {

    protected MyApplication myApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getApplication();
    }
}
