package com.example.xxnan.crasher.picpreview;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    private TextView dirName,dirCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        intData();
    }

    /**
     * 从ContentResolver获取图片
     */
    private void intData() {
        Uri uri= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver=getContentResolver();
//        contentResolver.query(uri,MediaStore.Images.Media.DATA,MediaStore.Images.Media._ID
//                ,MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,MediaStore.Images.Thumbnails.DATA,MediaStore.Images.Thumbnails.IMAGE_ID
//                ,MediaStore.Images.Media.DATE_MODIFIED + " desc ")
    }

    private void initView() {
        recyclerView= (RecyclerView) findViewById(R.id.pic_recycleview);
        relativeLayout= (RelativeLayout) findViewById(R.id.rlayout);
        dirName= (TextView) findViewById(R.id.dir_name);
        dirCount=(TextView)findViewById(R.id.dir_count);
    }

}
