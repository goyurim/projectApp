package com.example.qpeij.projectapp;

public class CheckListItem {
    String title;
    int goal;
    public int getGoal() {
        return goal;
    }
    public void setGoal(int percent) {
        this.goal = percent;
    }
    public CheckListItem(String title){
        this.title = title;

    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
