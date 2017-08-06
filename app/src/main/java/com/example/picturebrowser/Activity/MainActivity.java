package com.example.picturebrowser.Activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.picturebrowser.Adapter.MainListAdapter;
import com.example.picturebrowser.Application.MyApplication;
import com.example.picturebrowser.MyClass.Photo;
import com.example.picturebrowser.R;
import com.example.picturebrowser.Service.StartIntentService;
import com.example.picturebrowser.View.MyRecyclerView;

import java.util.List;

public class MainActivity extends BaseActivity {

   private List<Photo> resource ;
   private MyRecyclerView recycler_photo;

    class MyBroadReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case "REFRESH" :
                    resource = myApplication.getPaths();
                    recycler_photo.setLayoutManager(new GridLayoutManager(MainActivity.this,4));
                    recycler_photo.setAdapter(new MainListAdapter(MainActivity.this,resource));
                    break;
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) !=   PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        Class ca  = f.class;

        while(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) !=   PackageManager.PERMISSION_GRANTED);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("REFRESH");
        MyBroadReceiver receiver =new MyBroadReceiver();
        registerReceiver(receiver,intentFilter);

        recycler_photo = (MyRecyclerView) findViewById(R.id.recycler_photo);

        Intent intent = new Intent(this,StartIntentService.class);
        intent.setAction("BEGIN");
        startService(intent);







    }

    class  f{}
}
