package com.example.xxnan.crasher.picpreview.imageUtil;

import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.xxnan.crasher.picpreview.bean.ImageSize;

import java.lang.reflect.Field;

/**
 * Created by xxnan on 2016/8/30.
 */
public class Util {
    /**
     * 获取ImageView的大小
     * @param imageView
     * @return
     */
    public static ImageSize getImageSize(ImageView imageView) {
        ImageSize imageSize = new ImageSize();
        ViewGroup.LayoutParams layoutParams=imageView.getLayoutParams();
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        int width = imageView.getWidth();
        if (width <= 0)
            width = layoutParams.width;
        if (width <= 0)
            width = getValueFiledName(imageView,"mMaxWidth");
        if (width <= 0)
            width = displayMetrics.widthPixels;
        int height = imageView.getHeight();
        if (height <= 0)
            height = layoutParams.height;
        if (height <= 0)
            height =getValueFiledName(imageView,"mMaxHeight");
        if (height <= 0)
            height = displayMetrics.heightPixels;
        imageSize.width=width;
        imageSize.height=height;
        return imageSize;
    }
    private static  int getValueFiledName(Object obj ,String filedName)
    {
        int value=0;
        try {
            Field field=obj.getClass().getField(filedName);
            int fieldvalue= field.getInt(filedName);
            if(fieldvalue>0&&fieldvalue<Integer.MAX_VALUE)
               value=fieldvalue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
