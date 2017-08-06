package com.example.picturebrowser.Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.picturebrowser.MyClass.Photo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 铖哥 on 2017/5/22.
 */

public class MyApplication extends Application {
    public static Context mContext;
    public static Activity activity;

    public static List<Photo> paths = new ArrayList<>() ;

    public List<Photo> getPaths() {
        return paths;
    }

    public void setPaths(List<Photo> paths) {
        this.paths = paths;
    }

    public static Context getContext(){
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext  = getApplicationContext();
    }
}
