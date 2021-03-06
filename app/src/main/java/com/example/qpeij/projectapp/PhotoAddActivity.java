package com.example.qpeij.projectapp;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.graphics.*;
import android.widget.*;
import android.provider.*;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PhotoAddActivity extends AppCompatActivity {
    ImageButton photoAdd;
    TextView textView;
    GridView photoListView;
    String LocalName;
    PhotoItem photoItem;
    PhotoAdapter photoAdapter;
    private final int PICK_IMAGE = 1;
    FirebaseStorage storage;
    FirebaseDatabase database;
    private ProgressDialog detectionProgressDialog;
    private List<String> uidLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_add);
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
        textView=(TextView)findViewById(R.id.textView);

        //지역명 받아올것
        Intent intent=getIntent();
        LocalName=getIntent().getStringExtra("local");
        textView.setText(LocalName);

        //권한
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }

        photoAdd = (ImageButton)findViewById(R.id.photoAdd);

        photoListView=(GridView)findViewById(R.id.photoListView);
        photoAdapter = new PhotoAdapter();
        photoListView.setAdapter(photoAdapter);

        photoAdd = (ImageButton)findViewById(R.id.photoAdd);
        photoListView=(GridView)findViewById(R.id.photoListView);
        photoAdapter = new PhotoAdapter();

        database.getReference().child("MapDB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                uidLists.clear();
                photoAdapter.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    photoItem = snapshot.getValue(PhotoItem.class);
                    String uidKey = snapshot.getKey();
                    if(LocalName.equals(photoItem.getLocal())){

                        photoAdapter.addItem(photoItem);
                        photoListView.setAdapter(photoAdapter);
                        uidLists.add(uidKey);

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //사진추가버튼클릭
        photoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(photoAdapter.getCount()<=10) {
                     Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

                    startActivityForResult(intent,PICK_IMAGE);
                }
                else{
                    Toast.makeText(getApplicationContext(),"사진은 10장만 추가가 가능합니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        detectionProgressDialog = new ProgressDialog(this);

        //리스트에 항목 선택하면 이벤트 처리
        photoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
            //팝업창띄우기
            PopupMenu pop = new PopupMenu(getApplicationContext(),view);
            pop.getMenuInflater().inflate(R.menu.photo_menu,pop.getMenu());

            pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    int id = menuItem.getItemId();

                    if(id==R.id.deletePhoto) {
                        //삭제
                        delete_content(position);
                        //갱신
                        photoAdapter.notifyDataSetChanged();
                    }
                    return PhotoAddActivity.super.onOptionsItemSelected(menuItem);
                }
            });
            pop.show();
        }
    });
}
    private void delete_content(final int position){
                database.getReference().child("MapDB").child(uidLists.get(position)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(getApplicationContext(), "삭제가 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                        photoAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "삭제 실패", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    class PhotoAdapter extends BaseAdapter{
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            photoAdapter.addItem(new PhotoItem(getPath(uri)));
            photoAdapter.notifyDataSetChanged();
            upload(getPath(uri));
        }
    }
    public String getPath(Uri uri){
        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }
    private void upload(String uri){
        StorageReference storageRef = storage.getReference();

        Uri file = Uri.fromFile(new File(uri));
        StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                ImageDTO imageDTO = new ImageDTO();
                imageDTO.local=LocalName;
                imageDTO.image =(downloadUrl.toString());

                database.getReference().child("MapDB").push().setValue(imageDTO);
            }
        });
    }
}
