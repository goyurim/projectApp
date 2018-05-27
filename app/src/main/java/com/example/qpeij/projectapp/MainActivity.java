package com.example.qpeij.projectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    final int REQUEST_CODE_MAP=101;
    final int REQUEST_CODE_TODOList=102;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //map
    public void onClickedMapBtn(View view) {
        Intent intent = new Intent(getApplicationContext(),MapActivity.class);
        startActivityForResult(intent,REQUEST_CODE_MAP);
    }

    //todo list
    public void onClickedListBtn(View view) {
        Intent intent = new Intent(getApplicationContext(),TODOListActivity.class);
        startActivityForResult(intent,REQUEST_CODE_TODOList);
    }
}
