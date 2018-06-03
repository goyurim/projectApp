package com.example.qpeij.projectapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DiaryActivity extends AppCompatActivity {
    String LocalName;
    String date_selected;
    TextView local,text;
    EditText title, contents;
    FirebaseStorage storage;
    FirebaseDatabase database;
    private DatabaseReference mReference;
    MemoDTO memoDTO;
    String key="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        Intent intent=getIntent();
        LocalName=intent.getStringExtra("local");
        title=(EditText)findViewById(R.id.title);
        contents=(EditText)findViewById(R.id.contents);
        local=(TextView)findViewById(R.id.textView);
        local.setText(LocalName);
        text = (TextView)findViewById(R.id.dateText);
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();

        //가져오기
        database.getReference().child("MemoDB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    memoDTO = snapshot.getValue(MemoDTO.class);
                    if(LocalName.equals(memoDTO.local)){
                        key=snapshot.getKey();
                        title.setText(memoDTO.title);
                        date_selected=memoDTO.date;
                        text.setText(date_selected);
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
        Calendar c = Calendar.getInstance();
        int cYear = c.get(Calendar.YEAR);
        int cMonth = c.get(Calendar.MONTH);
        int cDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date_selected = String.valueOf(year)+" / "+String.valueOf(month+1)+" / "+String.valueOf(dayOfMonth);

                text.setText(date_selected);
            }
        };
        DatePickerDialog alert = new DatePickerDialog(this, mDateSetListener, cYear,cMonth,cDay);
        alert.show();
    }

    public void diaryWriteButton(View view) {
        upload();
        Intent intent = new Intent(getApplicationContext(),Show2Activity.class);
        intent.putExtra("local",LocalName);
        startActivity(intent);
        finish();
        //Toast.makeText(getApplicationContext(),"작성되었습니다.",Toast.LENGTH_LONG).show();
    }
    private void upload() {
        delete_content();
        MemoDTO memoDTO = new MemoDTO();
        memoDTO.local=LocalName;
        memoDTO.title=title.getText().toString();
        memoDTO.date=date_selected;
        memoDTO.contents=contents.getText().toString();
        mReference= database.getReference().child("MemoDB");
        key=mReference.getKey();
        mReference.push().setValue(memoDTO);

    }

    public void clearButton(View view) {
        title.setText("");
        contents.setText("");
        delete_content();
    }
    private void delete_content(){

        if(!key.equals("")){
        database.getReference().child("MemoDB").child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(getApplicationContext(), "삭제가 완료 되었습니다.", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "삭제 실패", Toast.LENGTH_SHORT).show();
            }
        });}
    }

}
