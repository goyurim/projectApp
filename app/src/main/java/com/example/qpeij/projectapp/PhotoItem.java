package com.example.qpeij.projectapp;


import android.graphics.Bitmap;

public class PhotoItem  {
    String image;
    String local;
    String mainImage;
    public PhotoItem(){}
    public PhotoItem(String resId){
        this.image=resId;
    }
    public void setResId(String resId) {
        this.image = resId;
    }
    public String getResId() {
        return image;
    }
    public void setLocal(String local){this.local=local;}
    public String getLocal(){return local;}
    public void setMainImage(String local){this.mainImage=mainImage;}
    public String getMainImage(){return mainImage;}
}
