package com.example.qpeij.projectapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.nhn.android.maps.NMapView;

public class SiteActivity extends AppCompatActivity {

    //지도
    private NMapView mMapView;// 지도 화면 View
    private final String CLIENT_ID = "yMxM1_6BoFVYF8jXenYu";// 애플리케이션 클라이언트 아이디 값

    String key;
    double latitude;
    double longitude;
    String site;
    FirebaseStorage storage;
    FirebaseDatabase database;
    SiteDTO siteDTO;
    TextView textView;
    ImageView siteImage1, siteImage2;
    MapViewFragmentNaver mapViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);

        textView=(TextView)findViewById(R.id.textView);
        siteImage1=(ImageView)findViewById(R.id.siteImage1);
        siteImage2=(ImageView)findViewById(R.id.siteImage2);

        Intent intent=getIntent();
        key=intent.getStringExtra("key");
        site=intent.getStringExtra("site");
        latitude=intent.getDoubleExtra("latitude",0);
        longitude=intent.getDoubleExtra("longitude",0);

        //db
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();

        database.getReference().child("SiteDB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    siteDTO = snapshot.getValue(SiteDTO.class);

                    if(key.equals(siteDTO.local)){
                        textView.setText(siteDTO.site);
                        Glide.with(getApplicationContext()).load(Uri.parse(siteDTO.siteImage1)).into(siteImage1);
                        Glide.with(getApplicationContext()).load(Uri.parse(siteDTO.siteImage2)).into(siteImage2);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mapViewFragment = (MapViewFragmentNaver) getSupportFragmentManager().findFragmentById(R.id.mapView);

        Log.d("yoon",latitude+"");
        Log.d("yoon",longitude+"");

    }

}
