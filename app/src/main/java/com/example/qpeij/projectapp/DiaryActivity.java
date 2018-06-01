package com.example.qpeij.projectapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class DiaryActivity extends AppCompatActivity {
    String LocalName;
    EditText title, contents;
    FirebaseStorage storage;
    FirebaseDatabase database;
    MemoDTO memoDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        Intent intent=getIntent();
        LocalName=intent.getStringExtra("local");
        title=(EditText)findViewById(R.id.title);
        contents=(EditText)findViewById(R.id.contents);

        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();

        database.getReference().child("MapDB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    memoDTO = snapshot.getValue(MemoDTO.class);
                    if(LocalName.equals(memoDTO.local)){
                        title.setText(memoDTO.title);
                        contents.setText(memoDTO.contents);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void datePickButton(View view) {

    }

    public void diaryWriteButton(View view) {
        upload();
        Toast.makeText(getApplicationContext(),"작성되었습니다.",Toast.LENGTH_LONG).show();
    }
    private void upload() {

        MemoDTO memoDTO = new MemoDTO();
        memoDTO.local=LocalName;
        memoDTO.title=title.getText().toString();
        memoDTO.contents=contents.getText().toString();
        
        database.getReference().child("MapDB").push().setValue(memoDTO);
    }
}
