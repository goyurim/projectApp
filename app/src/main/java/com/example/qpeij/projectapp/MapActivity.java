package com.example.qpeij.projectapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MapActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    public void onClickedLocalBtn1(View view) {
        String areaName = "강원도";
        onClick(view,areaName);
    }
    public void onClickedLocalBtn2(View view) {
        String areaName = "경기도";
        onClick(view,areaName);
    }
    public void onClickedLocalBtn3(View view) {
       String areaName = "충청도";
        onClick(view,areaName);
    }
    public void onClickedLocalBtn4(View view) {
        String areaName = "경상도";
        onClick(view,areaName);
    }
    public void onClickedLocalBtn5(View view) {
        String areaName = "전라도";
        onClick(view,areaName);
    }
    public void onClickedLocalBtn6(View view) {
        String areaName = "제주도";
        onClick(view,areaName);
    }
    public void onClick(View view, String local){
        final String areaName = local;
        PopupMenu pop = new PopupMenu(getApplicationContext(),view);
        pop.getMenuInflater().inflate(R.menu.menu,pop.getMenu());

        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //인텐트
                int id = menuItem.getItemId();
                if(id==R.id.add){
                    Intent intent = new Intent(getApplicationContext(), PhotoAddActivity.class);
                    intent.putExtra("local",areaName);
                    startActivity(intent);
                }
                else if(id == R.id.write){
                    Intent intent = new Intent(getApplicationContext(),DiaryActivity.class);
                    intent.putExtra("local",areaName);
                    startActivity(intent);
                }
                else if(id==R.id.show){
                    Intent intent = new Intent(getApplicationContext(),Show2Activity.class);
                    intent.putExtra("local",areaName);
                    startActivity(intent);
                }
                return MapActivity.super.onOptionsItemSelected(menuItem);
            }
        });
        pop.show();
    }
}
