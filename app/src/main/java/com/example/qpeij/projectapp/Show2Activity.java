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
    TextView local, title, contents;
    String LocalName;
    ImageDTO imageDTO;
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

        Intent intent=getIntent();
        LocalName = intent.getStringExtra("local");
        local.setText(LocalName);

        //내용보기
        database.getReference().child("MapDB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    imageDTO = snapshot.getValue(ImageDTO.class);
                    if(LocalName.equals(imageDTO.local)){
                        title.setText(imageDTO.title);
                        contents.setText(imageDTO.contents);
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
