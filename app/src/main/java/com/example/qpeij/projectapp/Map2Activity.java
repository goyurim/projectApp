package com.example.qpeij.projectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

public class Map2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
    }

    public void onClickedVersionBtn2(View view) {
        Intent intent=new Intent(getApplicationContext(),MapActivity.class);
        startActivityForResult(intent,11);
        finish();
    }

    public void onClickedBtn1(View view) {
        Intent intent =  new Intent(getApplicationContext(),SiteActivity.class);
        intent.putExtra("key","서울");
        startActivity(intent);
    }
    public void onClickedBtn2(View view) {
        Intent intent =  new Intent(getApplicationContext(),SiteActivity.class);
        intent.putExtra("key","춘천");
        startActivity(intent);
    }
    public void onClickedBtn3(View view) {
        Intent intent =  new Intent(getApplicationContext(),SiteActivity.class);
        intent.putExtra("key","강릉");
        startActivity(intent);
    }
    public void onClickedBtn4(View view) {
        Intent intent =  new Intent(getApplicationContext(),SiteActivity.class);
        intent.putExtra("key","대전");
        startActivity(intent);
    }
    public void onClickedBtn5(View view) {
        Intent intent =  new Intent(getApplicationContext(),SiteActivity.class);
        intent.putExtra("key","대구");
        startActivity(intent);
    }
    public void onClickedBtn6(View view) {
        Intent intent =  new Intent(getApplicationContext(),SiteActivity.class);
        intent.putExtra("key","부산");
        startActivity(intent);
    }

    public void onClickedBtn7(View view) {
        Intent intent =  new Intent(getApplicationContext(),SiteActivity.class);
        intent.putExtra("key","전주");
        startActivity(intent);
    }

    public void onClickedBtn8(View view) {
        Intent intent =  new Intent(getApplicationContext(),SiteActivity.class);
        intent.putExtra("key","여수");
        startActivity(intent);
    }
}
