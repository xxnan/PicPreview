package com.example.xxnan.crasher.picpreview.imageUtil;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/8/30.
 */
public class ToastUtil {
    private static ToastUtil instance;
    private Toast toast;
    private String oldtText;

    private ToastUtil() {

    }

    public static ToastUtil getInstance() {
        if (instance == null)
            synchronized (ToastUtil.class) {
                if (instance == null)
                    instance = new ToastUtil();
            }
        return instance;
    }

    public void showtext(Context context, String text) {
        if (oldtText.equals(text))
            return;
        else
            oldtText = text;
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else
            toast.setText(text);
        toast.show();

    }
}
