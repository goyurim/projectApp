package com.example.qpeij.projectapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PhotoAddActivity extends AppCompatActivity {
    Button photoAdd;
    GridView photoListView;
    PhotoAdapter photoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_add);

        photoAdd = (Button)findViewById(R.id.photoAdd);

        photoListView=(GridView)findViewById(R.id.photoListView);

        photoAdapter = new PhotoAdapter();

        photoListView.setAdapter(photoAdapter);

        photoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoAdapter.addItem(new PhotoItem(R.drawable.map));
                photoAdapter.notifyDataSetChanged();
            }
        });
        //리스트에 항목 선택하면 이벤트 처리
        photoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //팝업창띄우기
                //삭제/대표사진설정
            }
        });
    }

    class PhotoAdapter extends BaseAdapter{
        ArrayList<PhotoItem> items = new ArrayList<PhotoItem>();
        @Override
        public int getCount()  {
            return items.size();
        }

        @Override
        public Object getItem(int position)  {
            return items.get(position);
        }

        @Override
        public long getItemId(int position)  {
            return position;
        }
        public void addItem(PhotoItem item){
            items.add(item);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup)
        {
            PhotoItemView  view = new PhotoItemView(getApplicationContext());

            PhotoItem item = items.get(position);
            view.setImageView(item.getResId());
            return view;
        }
    }
}
