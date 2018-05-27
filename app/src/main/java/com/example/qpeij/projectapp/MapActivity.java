package com.example.qpeij.projectapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        PopupMenu pop = new PopupMenu(this,view);
        pop.getMenuInflater().inflate(R.menu.menu,pop.getMenu());

        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //인텐트
                String title = menuItem.getTitle().toString();
                switch (title){
                    case "사진넣기":
                        break;
                    case "메모하기":
                        break;
                    case "내용보기":
                        break;
                }
                return true;
            }
        });
        pop.show();
    }
}
