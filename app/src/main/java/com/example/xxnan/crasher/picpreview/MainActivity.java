package com.example.xxnan.crasher.picpreview;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xxnan.crasher.picpreview.adapter.GridAdapter;
import com.example.xxnan.crasher.picpreview.adapter.GridViewAdapter;
import com.example.xxnan.crasher.picpreview.bean.ImageInfo;
import com.example.xxnan.crasher.picpreview.imageUtil.ToastUtil;
import com.example.xxnan.crasher.picpreview.imageUtil.Util;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    private RecyclerView recyclerView;
    private GridView pic_gridview;
    private GridAdapter gridAdapter;
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
       /* Cursor cursor = contentResolver.query(externalContentUri, null, null, null, null);
        while (cursor.moveToNext()) {
            ImageInfo info = new ImageInfo();
            info.setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
            info.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
//            info.setDate(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)));
            imageList.add(info);
        }
        cursor.close();*/
        String[] projection = { MediaStore.Images.Thumbnails._ID  ,MediaStore.Images.Thumbnails.DATA};
        Cursor cursor = contentResolver.query(
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,//指定缩略图数据库的Uri
                null,//指定所要查询的字段
                null,//查询条件
                null, //查询条件中问号对应的值
                null);
        while (cursor.moveToNext()) {
            ImageInfo info = new ImageInfo();
            info.setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Thumbnails._ID)));
            info.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA)));
            imageList.add(info);

        }
        cursor.close();
        gridAdapter.notifyDataSetChanged();
    }

    private void initView() {
//        recyclerView = (RecyclerView) findViewById(R.id.pic_recycleview);
//        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
//        recyclerView.setAdapter(myAdapter);
        relativeLayout = (RelativeLayout) findViewById(R.id.rlayout);
        dirName = (TextView) findViewById(R.id.dir_name);
        dirCount = (TextView) findViewById(R.id.dir_count);
//        myAdapter = new GridViewAdapter(getApplicationContext(), imageList);
        gridAdapter=new GridAdapter(getApplicationContext(), imageList);
        pic_gridview= (GridView) findViewById(R.id.pic_gridview);
        pic_gridview.setAdapter(gridAdapter);
    }

}
