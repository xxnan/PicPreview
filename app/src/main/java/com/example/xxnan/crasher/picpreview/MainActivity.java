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
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xxnan.crasher.picpreview.adapter.GridAdapter;
import com.example.xxnan.crasher.picpreview.adapter.GridViewAdapter;
import com.example.xxnan.crasher.picpreview.bean.FileBean;
import com.example.xxnan.crasher.picpreview.bean.ImageInfo;
import com.example.xxnan.crasher.picpreview.imageUtil.ToastUtil;
import com.example.xxnan.crasher.picpreview.imageUtil.Util;
import com.example.xxnan.crasher.picpreview.pop.PopWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView pic_gridview;
    private GridAdapter gridAdapter;
    private RelativeLayout relativeLayout;
    private TextView dirName, dirCount;
    private List<ImageInfo> imageList = new ArrayList<ImageInfo>();
    private GridViewAdapter myAdapter;
    private ProgressDialog progressDialog;
    private List<String> mDirPaths = new ArrayList<String>();
    private String dirNameContent = "";
    private int dirCountContent = 0;
    private PopWindow popWindow;
    private boolean isPoPupWindowShow;
    List<FileBean> fileList;
    private Handler myhHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialog.dismiss();
            dirName.setText(dirNameContent);
            dirCount.setText(dirCountContent + "");
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
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("正在加载...");
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //模拟延时
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loadUri();
            }
        }).start();
    }

    /**
     * 从ContentResolver中获取所有图片
     */
    private void loadUri() {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=? ", new String[]{"image/jpg", "image/png", "image/jpeg"},
                MediaStore.Images.Media.DATE_MODIFIED);
        while (cursor.moveToNext()) {
            ImageInfo info = new ImageInfo();
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            File parentFile = new File(path).getParentFile();
            if (parentFile == null)
                continue;
            String dirPath = parentFile.getAbsolutePath();
            if (mDirPaths.contains(dirPath))
                continue;
            else
                mDirPaths.add(dirPath);

            info.setPath(path);
            info.setDirPath(dirPath);
            imageList.add(info);
        }
        if (mDirPaths.size() > 0) {
            dirNameContent = mDirPaths.get(0).toString();
            dirCountContent = imageList.size();
        }
        cursor.close();
        preData();
        myhHandle.sendEmptyMessage(0X11);
    }

    private void preData() {
        fileList = new ArrayList<>();
        for (String path : mDirPaths) {
            FileBean bean = new FileBean();
            int count = 0;
            String fristPath = "";
            for (ImageInfo imageInfo : imageList) {
                if (imageInfo.getDirPath().equals(path)) {
                    count++;
                    bean.setDirPath(imageInfo.getDirPath());
                    if (!fristPath.equals(""))
                        fristPath = imageInfo.getPath();
                }
            }
            bean.setFileCount(count);
            bean.setFristPath(fristPath);
            fileList.add(bean);
        }
    }

    private void initView() {
        relativeLayout = (RelativeLayout) findViewById(R.id.rlayout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popWindow == null)
                    popWindow = new PopWindow(MainActivity.this, fileList);
                if (!isPoPupWindowShow) {
                    //显示popwindow
                    popWindow.showAsDropDown(relativeLayout);

                    isPoPupWindowShow = true;
                } else {
                    //隐藏popwindow
                    popWindow.dismiss();
                    isPoPupWindowShow = false;
                }
            }
        });
        dirName = (TextView) findViewById(R.id.dir_name);
        dirCount = (TextView) findViewById(R.id.dir_count);
        gridAdapter = new GridAdapter(getApplicationContext(), imageList);
        pic_gridview = (GridView) findViewById(R.id.pic_gridview);
        pic_gridview.setAdapter(gridAdapter);
    }

}
