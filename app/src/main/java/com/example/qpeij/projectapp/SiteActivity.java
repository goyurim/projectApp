package com.example.qpeij.projectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
    WebView mapView; //지도여기에 넣어라 천명희
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);


        //key값 받아오기
        Intent intent=getIntent();
        key = intent.getStringExtra("key");

        mapView=(WebView) findViewById(R.id.mapVIew);
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
      /*  String url;
        switch (key){
            case "서울":
                url = "http://naver.me/5r6ygG2I";
                webViewContect(url);
                break;
            case "춘천":
                url = "http://naver.me/5lYU1cKu";
                webViewContect(url);
                break;
            case "강릉":
                url = "http://naver.me/GcMxatOv";
                webViewContect(url);
                break;
            case "대전":
                url = "http://naver.me/Fvh4lRT4";
                webViewContect(url);
                break;
            case "대구":
                url = "http://naver.me/x4tEgeTL";
                webViewContect(url);
                break;
            case "부산":
                url = "http://naver.me/FiHWyYWJ";
                webViewContect(url);
                break;
            case "전주":
                url = "http://naver.me/5inQGEG7";
                webViewContect(url);
                break;
                default:
                    url = "http://naver.me/xYgdxNlu";
                    webViewContect(url);
                    break;
        }*/

    }
/*
    public void webViewContect(String url){
        String loadUrl = url;
        WebSettings webSettings = mapView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mapView.setWebViewClient(new WebViewClient());
        mapView.loadUrl(loadUrl);

    }
      */
}
