package com.example.xxnan.crasher.picpreview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.xxnan.crasher.picpreview.R;
import com.example.xxnan.crasher.picpreview.bean.ImageInfo;
import com.example.xxnan.crasher.picpreview.imageUtil.ImageLoader;

import java.util.List;

/**
 * Created by xxnan on 2016/8/30.
 */
public class GridViewAdapter  extends RecyclerView.Adapter<MyHoder>{


    private Context mContext;
    private List<ImageInfo> list;
    public GridViewAdapter(Context context, List<ImageInfo> mlist)
    {
        mContext=context;
        list=mlist;
    }
    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(mContext).inflate(R.layout.grid_item,null);
        return new MyHoder(view);
    }

    @Override
    public void onBindViewHolder(MyHoder holder, int position) {
        ImageLoader.getInstance().loadImage(list.get(position).getPath(),holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
    class MyHoder extends  RecyclerView.ViewHolder
    {

        public ImageView imageView;
        public MyHoder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.image_item);
        }
    }
