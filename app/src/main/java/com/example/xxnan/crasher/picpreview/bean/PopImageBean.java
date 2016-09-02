package com.example.xxnan.crasher.picpreview.bean;

/**
 * Created by xxnan on 2016/9/2.
 */
public class PopImageBean {
    private String fristIamgePath;//第一张图片的路径
    private String dirPath;//父目录路径
    private int imageCount;//目录下图片的数量

    public void setFristIamgePath(String fristIamgePath) {
        this.fristIamgePath = fristIamgePath;
    }

    public String getFristIamgePath() {
        return fristIamgePath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

}
