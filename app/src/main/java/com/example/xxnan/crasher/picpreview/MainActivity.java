package com.example.xxnan.crasher.picpreview;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xxnan.crasher.picpreview.adapter.GridViewAdapter;
import com.example.xxnan.crasher.picpreview.bean.ImageInfo;
import com.example.xxnan.crasher.picpreview.imageUtil.ToastUtil;
import com.example.xxnan.crasher.picpreview.imageUtil.Util;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    private TextView dirName, dirCount;
    private List<ImageInfo> imageList = new ArrayList<ImageInfo>();
    private GridViewAdapter myAdapter;

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
        if(!Util.isSdCardExist()) {
            ToastUtil.getInstance().showtext(MainActivity.this, "SD卡不存在！");
            return;
        }
        loadUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID
                , MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, MediaStore.Images.Thumbnails.DATA, MediaStore.Images.Thumbnails.IMAGE_ID
                , MediaStore.Images.Media.DATE_MODIFIED + " desc ");
    }

    private void loadUri(Uri externalContentUri, String data, String id, Uri externalContentUri1, String data1, String imageId, String s) {
        ContentResolver contentResolver = getContentResolver();
        //获得外部存储卡上的图片缩略图
        Cursor cursor = contentResolver.query(externalContentUri1, null, null, null, null);
        while (cursor.moveToNext()) {
            ImageInfo info = new ImageInfo();
            info.setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID)));
            info.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
            info.setDate(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)));
            imageList.add(info);
        }
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.pic_recycleview);
        relativeLayout = (RelativeLayout) findViewById(R.id.rlayout);
        dirName = (TextView) findViewById(R.id.dir_name);
        dirCount = (TextView) findViewById(R.id.dir_count);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
        myAdapter = new GridViewAdapter(getApplicationContext(), imageList);
        recyclerView.setAdapter(myAdapter);
    }

}
