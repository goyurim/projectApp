package com.example.qpeij.projectapp;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class Show2Activity extends AppCompatActivity {
    TextView local, title, contents,dateText;
    String LocalName;
    MemoDTO memoDTO;
    FirebaseDatabase database;
    FirebaseStorage storage;
    PhotoItem photoItem;
    PhotoAdapter showAdapter;
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show2);
        //권한
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }

        local = (TextView)findViewById(R.id.local);
        title=(TextView)findViewById(R.id.title);
        contents=(TextView)findViewById(R.id.contents);
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        gridView=(GridView)findViewById(R.id.gridView);
        dateText=(TextView)findViewById(R.id.dateText);

        //지역명 가져옴
        Intent intent=getIntent();
        LocalName = intent.getStringExtra("local");
        local.setText(LocalName);

        showAdapter = new PhotoAdapter();
        gridView.setAdapter(showAdapter);

        //내용보기
        database.getReference().child("MapDB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                showAdapter.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    memoDTO = snapshot.getValue(MemoDTO.class);
                    photoItem = snapshot.getValue(PhotoItem.class);
                    if(LocalName.equals(memoDTO.local)){
                        title.setText(memoDTO.title);
                        contents.setText(memoDTO.contents);
                        dateText.setText(memoDTO.date);
                    }
                    if(LocalName.equals(photoItem.getLocal())){
                        showAdapter.addItem(photoItem);
                        gridView.setAdapter(showAdapter);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private class PhotoAdapter extends BaseAdapter{
        ArrayList<PhotoItem> items = new ArrayList<PhotoItem>();
        public void clear() {
            items.clear();
        }
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
