package com.example.xxnan.crasher.picpreview.bean;

/**
 * Created by xxnan on 2016/8/30.
 */
public class ImageInfo {
    private String path;//图片路径
    private String name;//图片名称
    private String dirPath;//图片父目录


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        name=path.substring(path.lastIndexOf("/")+1);
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }
}
