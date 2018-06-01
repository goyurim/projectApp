package com.example.qpeij.projectapp;

/**
 * Created by qpeij on 2018-05-28.
 */


public class CheckBoxItem {
    String title;
    String content;

    public CheckBoxItem(){}
    public CheckBoxItem(String content){
        this.content = content;
    }
    public String getTitle(){
        return title;
    }
    public String getContent() {
        return content;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
