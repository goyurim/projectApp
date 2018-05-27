package com.example.qpeij.projectapp;

/**
 * Created by qpeij on 2018-05-27.
 */

public class CheckListItem {
    String title;
    String goal;

    public CheckListItem(String title, String goal){
        this.title = title;
        this.goal = goal;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }
    public String getTitle() {
        return title;
    }

    public String getGoal() {
        return goal;
    }
}
