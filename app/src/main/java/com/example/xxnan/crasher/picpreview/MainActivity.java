package com.example.xxnan.crasher.picpreview;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import java.io.File;
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
    private ProgressDialog progressDialog;
    private Handler myhHandle =new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialog.dismiss();
            gridAdapter.notifyDataSetChanged();
        }
    };
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
        if (!Util.isSdCardExist()) {
            ToastUtil.getInstance().showtext(MainActivity.this, "SD卡不存在！");
            return;
        }
        progressDialog=ProgressDialog.show(MainActivity.this,null,"正在加载...");
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadUri();
            }
        }).start();
    }

    private void loadUri() {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=? ", new String[]{"image/png", "image/jpeg"}, MediaStore.Images.Media.DATE_MODIFIED);
        while (cursor.moveToNext()) {
            ImageInfo info = new ImageInfo();
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
            File parentFile = new File(path).getParentFile();
            if (parentFile == null)
                continue;
            String dirPath = parentFile.getAbsolutePath();
            info.setPath(path);
            info.setDirPath(dirPath);
            imageList.add(info);

        }
        cursor.close();
        myhHandle.sendEmptyMessage(0X11);
    }

    private void initView() {
//        recyclerView = (RecyclerView) findViewById(R.id.pic_recycleview);
//        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
//        recyclerView.setAdapter(myAdapter);
        relativeLayout = (RelativeLayout) findViewById(R.id.rlayout);
        dirName = (TextView) findViewById(R.id.dir_name);
        dirCount = (TextView) findViewById(R.id.dir_count);
//        myAdapter = new GridViewAdapter(getApplicationContext(), imageList);
        gridAdapter = new GridAdapter(getApplicationContext(), imageList);
        pic_gridview = (GridView) findViewById(R.id.pic_gridview);
        pic_gridview.setAdapter(gridAdapter);
    }

}
