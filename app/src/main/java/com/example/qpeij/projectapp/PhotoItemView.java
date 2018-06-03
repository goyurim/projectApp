package com.example.qpeij.projectapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by yoonc on 2018-05-28.
 */

public class PhotoItemView extends LinearLayout {
    ImageView photoView;
    public PhotoItemView(Context context) {
        super(context);
        init(context);
    }

    public PhotoItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public void init(Context context){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.photo_item,this,true);
        photoView=(ImageView)findViewById(R.id.photoView);

    }
    public void setImageView(String resId) {
        Glide.with(getContext()).load(resId).into(photoView);
        //photoView.setImageURI(resId);
    }
}
