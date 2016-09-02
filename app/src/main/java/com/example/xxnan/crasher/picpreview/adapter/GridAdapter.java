package com.example.xxnan.crasher.picpreview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.xxnan.crasher.picpreview.R;
import com.example.xxnan.crasher.picpreview.bean.ImageInfo;
import com.example.xxnan.crasher.picpreview.imageUtil.ImageLoader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xxnan on 2016/8/30.
 */
public class GridAdapter extends BaseAdapter {

    private Set<String> set = new HashSet<String>();
    private Context mContext;
    private List<ImageInfo> list;

    public GridAdapter(Context context, List<ImageInfo> mlist) {
        mContext = context;
        list = mlist;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ImageHolder holder;
        if (convertView == null) {
            holder = new ImageHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image_item);
            holder.image_select = (ImageButton) convertView.findViewById(R.id.image_select);
            convertView.setTag(holder);
        } else {
            holder = (ImageHolder) convertView.getTag();
        }
        final String path = list.get(position).getPath();
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (set.contains(path)) {
                    set.remove(path);
                    holder.image_select.setBackgroundResource(R.drawable.image_unselect);
                } else {
                    set.add(path);
                    holder.image_select.setBackgroundResource(R.drawable.image_select);
                }
            }
        });
        ImageLoader.getInstance().loadImage(list.get(position).getPath(), holder.imageView);
        return convertView;
    }

    static class ImageHolder {
        ImageView imageView;
        ImageButton image_select;
    }
}
