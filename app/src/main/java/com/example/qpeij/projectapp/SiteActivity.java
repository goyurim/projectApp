package com.example.qpeij.projectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class SiteActivity extends AppCompatActivity {

    private String key;
    private double latitude;
    private double longitude;
    private String site;
    FirebaseStorage storage;
    FirebaseDatabase database;
    SiteDTO siteDTO;
    TextView textView;
    LinearLayout mapView; //지도여기에 넣어라 천명희
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);


        //key값 받아오기
        Intent intent=getIntent();
        key = intent.getStringExtra("key");

        mapView=(LinearLayout)findViewById(R.id.mapView);
        textView=(TextView)findViewById(R.id.textView);


        //db
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();

        //database.getReference().child("SiteDB").child(key).addValueEventListener(new ValueEventListener() {
        database.getReference().child("SiteDB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    siteDTO = snapshot.getValue(SiteDTO.class);
                    if(key.equals(siteDTO.local)){
                        textView.setText(siteDTO.site);
                        latitude=siteDTO.latitude;
                        longitude=siteDTO.longtitude;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
