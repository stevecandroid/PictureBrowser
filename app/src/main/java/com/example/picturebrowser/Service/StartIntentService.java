package com.example.picturebrowser.Service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.picturebrowser.Application.MyApplication;
import com.example.picturebrowser.MyClass.Photo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 铖哥 on 2017/5/23.
 */

public class StartIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    Cursor cursor ;
    MyApplication myApplication ;
    List<Photo> result = new ArrayList<>();
    SQLiteDatabase db;
    SQLiteOpenHelper helper = new SQLiteOpenHelper(this,"Photos",null,1) {

        final String  Photos =
                "create table Photos (" +
                "id integer primary key autoincrement, " +
                "path text, " +
                "size integer, " +
                "lastmodified integer, "+
                "creationtime integer)";

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Photos);
            Log.e("tag","Create");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    };



    public StartIntentService(String name) {
        super(name);
    }

    public StartIntentService(){
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("start","start");

        myApplication = (MyApplication) getApplication();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent.getAction().equals("BEGIN")){
            Log.e("tag","begin");
            db = helper.getWritableDatabase();
            cursor = db.query("Photos",null,null,null,null,null,null);
            Log.e("ta",cursor.moveToFirst()+"");
            if(!cursor.moveToFirst()){
                search(Environment.getExternalStorageDirectory(),result,"jpg","png","bmp");
                ContentValues contentValues = new ContentValues();
                for(Photo p : result) {
                    contentValues.put("path", p.getPath());
                    contentValues.put("size",p.getSize());
                    contentValues.put("lastmodified",p.getLastmodified());
                    db.insert("Photos",null,contentValues);
                    contentValues.clear();
                }
            }else{
                do{
                    result.add(new Photo(cursor.getString(cursor.getColumnIndex("path")),
                            cursor.getInt(cursor.getColumnIndex("size")),
                            cursor.getInt(cursor.getColumnIndex("lastmodified"))));
                }while(cursor.moveToNext());
            }
            cursor.close();
            myApplication.setPaths(result);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendBroadcast(new Intent("REFRESH"));
        Log.e("eee","eeee");
    }

    static int stop = 0 ;
    public static void search(File rootFile, List<Photo> result , String... format ){

        if(rootFile.isDirectory()){
            try{
                File[] allFile = rootFile.listFiles();

                for (File anAllFile : allFile) {
                    if (anAllFile.isDirectory()) {
                        search(anAllFile, result, format);
                    } else {
                        for (String aFormat : format) {
                            if (anAllFile.getPath().contains(aFormat)) {
                                result.add(new Photo(anAllFile.getPath(), anAllFile.length() / 1024 / 1024, anAllFile.lastModified()));

                                Log.d("asdasdasdasdasd",stop+++"");
                                if(stop == 11550){
                                    break;
                                }
                                Log.e("tag",anAllFile.getPath());
                            }
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }
}
