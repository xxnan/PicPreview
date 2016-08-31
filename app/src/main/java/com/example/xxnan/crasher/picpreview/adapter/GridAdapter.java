package com.example.xxnan.crasher.picpreview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.xxnan.crasher.picpreview.R;
import com.example.xxnan.crasher.picpreview.bean.ImageInfo;
import com.example.xxnan.crasher.picpreview.imageUtil.ImageLoader;

import java.util.List;

/**
 * Created by xxnan on 2016/8/30.
 */
public class GridAdapter extends BaseAdapter{


    private Context mContext;
    private List<ImageInfo> list;
    public GridAdapter(Context context, List<ImageInfo> mlist)
    {
        mContext=context;
        list=mlist;
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
        ImageHolder holder;
        if(convertView==null)
        {
            holder=new ImageHolder();
            convertView=LayoutInflater.from(mContext).inflate(R.layout.grid_item,null);
            holder.imageView= (ImageView) convertView.findViewById(R.id.image_item);
            convertView.setTag(holder);
        }else
        {
            holder= (ImageHolder) convertView.getTag();
        }
        ImageLoader.getInstance().loadImage(list.get(position).getPath(),holder.imageView);
        return convertView;
    }

    static class ImageHolder
    {
        ImageView imageView;
    }
}
