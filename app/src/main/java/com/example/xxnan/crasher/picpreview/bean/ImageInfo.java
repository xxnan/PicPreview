package com.example.xxnan.crasher.picpreview.bean;

/**
 * Created by xxnan on 2016/8/30.
 */
public class ImageInfo {
    private int id;//图片Id
    private String path;//图片路径
    private String name;//图片名称
    private String date;//图片日期

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return path.substring(path.lastIndexOf("/"));
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
