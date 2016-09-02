package com.example.xxnan.crasher.picpreview.bean;

/**
 * Created by xxnan on 2016/9/2.
 */
public class FileBean {
    private String dirPath;//文件目录路径
    private int fileCount;//目录下文件数量
    private String fristPath;//第一张图片路径

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public String getFristPath() {
        return fristPath;
    }

    public void setFristPath(String fristPath) {
        this.fristPath = fristPath;
    }
}
