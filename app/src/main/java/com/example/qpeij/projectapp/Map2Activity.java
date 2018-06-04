package com.example.qpeij.projectapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Map2Activity extends AppCompatActivity {
    SiteDTO siteDTO;
    String key;
    FirebaseDatabase database;
    double latitude;
    double longitude;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);

        database=FirebaseDatabase.getInstance();
    }

    public void onClickedBtn1(View view) {
        intent=  new Intent(getApplicationContext(),SiteActivity.class);
        key="서울";
        intent.putExtra("key",key);

        database.getReference().child("SiteDB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    siteDTO = snapshot.getValue(SiteDTO.class);

                    if(key.equals(siteDTO.local)){

                        latitude=siteDTO.latitude;
                        longitude=siteDTO.longtitude;
                        intent.putExtra("latitude",latitude);
                        intent.putExtra("longitude",longitude);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void onClickedBtn2(View view) {
        intent =  new Intent(getApplicationContext(),SiteActivity.class);
        key="춘천";
        intent.putExtra("key",key);

        database.getReference().child("SiteDB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    siteDTO = snapshot.getValue(SiteDTO.class);

                    if(key.equals(siteDTO.local)){
                        latitude=siteDTO.latitude;
                        longitude=siteDTO.longtitude;
                        Log.d("yoon if",latitude+"");
                        intent.putExtra("latitude",latitude);
                        intent.putExtra("longitude",longitude);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void onClickedBtn3(View view) {
        intent =  new Intent(getApplicationContext(),SiteActivity.class);
        key="강릉";
        intent.putExtra("key",key);

        database.getReference().child("SiteDB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    siteDTO = snapshot.getValue(SiteDTO.class);

                    if(key.equals(siteDTO.local)){

                        latitude=siteDTO.latitude;
                        longitude=siteDTO.longtitude;
                        intent.putExtra("latitude",latitude);
                        intent.putExtra("longitude",longitude);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void onClickedBtn4(View view) {
        intent =  new Intent(getApplicationContext(),SiteActivity.class);
        key="대전";
        intent.putExtra("key",key);

        database.getReference().child("SiteDB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    siteDTO = snapshot.getValue(SiteDTO.class);

                    if(key.equals(siteDTO.local)){

                        latitude=siteDTO.latitude;
                        longitude=siteDTO.longtitude;
                        intent.putExtra("latitude",latitude);
                        intent.putExtra("longitude",longitude);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void onClickedBtn5(View view) {
        intent =  new Intent(getApplicationContext(),SiteActivity.class);
        key="대구";
        intent.putExtra("key",key);

        database.getReference().child("SiteDB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    siteDTO = snapshot.getValue(SiteDTO.class);

                    if(key.equals(siteDTO.local)){

                        latitude=siteDTO.latitude;
                        longitude=siteDTO.longtitude;
                        intent.putExtra("latitude",latitude);
                        intent.putExtra("longitude",longitude);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void onClickedBtn6(View view) {
        intent =  new Intent(getApplicationContext(),SiteActivity.class);
        key="부산";
        intent.putExtra("key",key);

        database.getReference().child("SiteDB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    siteDTO = snapshot.getValue(SiteDTO.class);

                    if(key.equals(siteDTO.local)){

                        latitude=siteDTO.latitude;
                        longitude=siteDTO.longtitude;
                        intent.putExtra("latitude",latitude);
                        intent.putExtra("longitude",longitude);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void onClickedBtn7(View view) {
        intent =  new Intent(getApplicationContext(),SiteActivity.class);
        key="전주";
        intent.putExtra("key",key);

        database.getReference().child("SiteDB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    siteDTO = snapshot.getValue(SiteDTO.class);

                    if(key.equals(siteDTO.local)){

                        latitude=siteDTO.latitude;
                        longitude=siteDTO.longtitude;
                        intent.putExtra("latitude",latitude);
                        intent.putExtra("longitude",longitude);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void onClickedBtn8(View view) {
        intent =  new Intent(getApplicationContext(),SiteActivity.class);
        key="여수";
        intent.putExtra("key",key);

        database.getReference().child("SiteDB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    siteDTO = snapshot.getValue(SiteDTO.class);

                    if(key.equals(siteDTO.local)){

                        latitude=siteDTO.latitude;
                        longitude=siteDTO.longtitude;
                        intent.putExtra("latitude",latitude);
                        intent.putExtra("longitude",longitude);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //지역 지도로 돌아가기
    public void onClickedVersionBtn2(View view) {
        intent = new Intent(getApplicationContext(), MapActivity.class);
        startActivity(intent);
        finish();
    }

    public void TiketingOnClickButton(View view) {
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.letskorail.com/ebizprd/prdMain.do"));
        startActivity(intent);
        finish();
    }


}
