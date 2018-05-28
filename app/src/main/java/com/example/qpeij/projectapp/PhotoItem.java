package com.example.qpeij.projectapp;


import android.graphics.Bitmap;

public class PhotoItem  {
    Bitmap resId;
    public PhotoItem(Bitmap resId){
        this.resId=resId;
    }
    public void setResId(Bitmap resId) {
        this.resId = resId;
    }
    public Bitmap getResId() {
        return resId;
    }
}
