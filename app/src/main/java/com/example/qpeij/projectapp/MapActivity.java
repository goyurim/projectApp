package com.example.qpeij.projectapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MapActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    public void onClickedLocalBtn(View view) {
        PopupMenu pop = new PopupMenu(getApplicationContext(),view);
        pop.getMenuInflater().inflate(R.menu.menu,pop.getMenu());

        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //인텐트
                int id = menuItem.getItemId();
                if(id==R.id.add){
                    Intent intent2 = new Intent(getApplicationContext(), PhotoAddActivity.class);
                    startActivity(intent2);
                }
                else if(id == R.id.write){
                    Intent intent3 = new Intent(getApplicationContext(),DiaryActivity.class);
                    startActivity(intent3);
                }

                return MapActivity.super.onOptionsItemSelected(menuItem);
            }
        });
        pop.show();
    }
}
