package com.example.qpeij.projectapp;

/**
 * Created by qpeij on 2018-05-28.
 */

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CheckBoxItemView extends LinearLayout{
    TextView checkboxItem;
    public CheckBoxItemView(Context context) {
        super(context);
        init(context);
    }

    public CheckBoxItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.checkbox_item, this,true);
        checkboxItem = (TextView)findViewById(R.id.checkboxContent);
        
    }
    public void setContent(String checkboxItem) {
        this.checkboxItem.setText(checkboxItem);
    }
}

