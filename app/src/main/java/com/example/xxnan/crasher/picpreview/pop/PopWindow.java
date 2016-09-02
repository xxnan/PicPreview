package com.example.xxnan.crasher.picpreview.pop;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xxnan.crasher.picpreview.R;
import com.example.xxnan.crasher.picpreview.bean.FileBean;
import com.example.xxnan.crasher.picpreview.imageUtil.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxnan on 2016/9/2.
 */
public class PopWindow extends PopupWindow{
    private int width,height;
    private LayoutInflater layoutInflater;
    private RelativeLayout contentView;
    private ListView listView;
    private ImageAdapter imageAdapter;
    private List<FileBean> list=new ArrayList<>();
    public PopWindow(Context context, List<FileBean> mlist)
    {
        list=mlist;
        layoutInflater=LayoutInflater.from(context);
        contentView= (RelativeLayout) layoutInflater.inflate(R.layout.pop_mian,null);
        setContentView(contentView);
        calWidthAndHeight(context);
        setWidth(width);
        setHeight(height);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        listView= (ListView) contentView.findViewById(R.id.dir_list);
        imageAdapter=new ImageAdapter(context);
        listView.setAdapter(imageAdapter);

    }

    private void calWidthAndHeight(Context context) {
        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dis=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dis);
        width=dis.widthPixels;
        height= (int) (dis.heightPixels*0.7);
    }
 class ImageAdapter extends BaseAdapter
 {
     Context context;
     public ImageAdapter(Context mContext)
     {
         context=mContext;
     }
     @Override
     public int getCount() {
         return list.size();
     }

     @Override
     public Object getItem(int position) {
         return list.get(position);
     }

     @Override
     public long getItemId(int position) {
         return position;
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
         MyHolder myHolder=null;
         if(convertView==null)
         {
             convertView=LayoutInflater.from(context).inflate(R.layout.pop_item,null);
             myHolder=new MyHolder();
             myHolder.dirCount= (TextView) convertView.findViewById(R.id.dir_count_tv);
             myHolder.dirPath=(TextView)convertView.findViewById(R.id.dir_tv) ;
             myHolder.fristImage= (ImageView) convertView.findViewById(R.id.image_dir);
             convertView.setTag(myHolder);
         }
         else
         {
             myHolder= (MyHolder) convertView.getTag();
         }
         FileBean fileBean=list.get(position);
         myHolder.dirCount.setText(fileBean.getFileCount()+"");
         ImageLoader.getInstance().loadImage(fileBean.getFristPath(),myHolder.fristImage);
         myHolder.dirPath.setText(fileBean.getDirPath());
         return convertView;
     }
 }
     static class MyHolder
     {
         ImageView fristImage;
         TextView dirPath;
         TextView dirCount;
     }
}
