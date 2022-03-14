package com.example.menojang;

import android.graphics.drawable.Drawable;

public class ListViewItem {

    String title;
    String time;
    Drawable icon;
    String memo;

    public String getTitle() {
        return title;
    }
    public String getTime() {
        return time;
    }
    public Drawable getIcon() { return icon; }
    public String getMemo(){ return memo;}

    public void setName(String title) {
        this.title = title;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setIcon(Drawable icon) { this.icon = icon; }
    public void setMemo(String memo) {
        this.memo = memo;
    }

}
