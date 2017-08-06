package com.example.picturebrowser.MyClass;

/**
 * Created by 铖哥 on 2017/5/22.
 */

public class Photo {

    private long size;
    private String path;
    private long creationtime;
    private long lastmodified;
    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Photo(String path , long size , long lastmodified) {
        this.path = path;
        this.size = size;
        this.lastmodified = lastmodified;
    }

    public long getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(long lastmodified) {
        this.lastmodified = lastmodified;
    }

    public long getCreationtime() {
        return creationtime;
    }

    public void setCreationtime(long creationtime) {
        this.creationtime = creationtime;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
