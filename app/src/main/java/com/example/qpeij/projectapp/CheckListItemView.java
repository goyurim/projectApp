package com.example.qpeij.projectapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CheckListItemView extends LinearLayout{
    TextView checklistTitle;

    public CheckListItemView(Context context) {
        super(context);
        init(context);
    }
    public CheckListItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.checklist_item, this,true);
        checklistTitle = (TextView)findViewById(R.id.tv_checklistTitle);
    }

    public void setTitle(String checklistTitle) {
        this.checklistTitle.setText(checklistTitle);
    }


}
