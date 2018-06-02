package com.example.qpeij.projectapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.List;

public class CheckBoxList2Activity extends AppCompatActivity {
    EditText ed_cbItem; //체크박스 내용 입력받는 것
    ListView listView;// 리스트 뷰
    TextView checklistTitle;//체크박스 리스트 이름
    //int id;//아이디 값
    int position;
    private List<String> uidLists = new ArrayList<>();
    String title;
    CheckBoxListAdapter adapter;
    CheckBoxItem checkBoxItem;
    FirebaseStorage storage;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box_list2);

        //권한
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }

        //TODOList 화면에서 받음
        Intent intent = getIntent();
        title = intent.getStringExtra("title"); //리스트 제목
        //id = intent.getIntExtra("id",0);//리스트 아이디값
        listView = (ListView) findViewById(R.id.checkboxlistview);
        ed_cbItem=(EditText)findViewById(R.id.ed_checkboxItem) ;
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();

        //타이틀 이름 지정
        checklistTitle = (TextView)findViewById(R.id.checklistTitle);
        checklistTitle.setText(title);

        //리스트 뷰 지정
        adapter=new CheckBoxListAdapter();
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        database.getReference().child("MapDB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                adapter.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    checkBoxItem = snapshot.getValue(CheckBoxItem.class);
                    if(title.equals(checkBoxItem.getTitle())){
                        adapter.addItem(checkBoxItem);
                        listView.setAdapter(adapter);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //길게누르면 -안됨 왠지 모름**
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Log","dsf");
                position = i;
                showMessage();
                return true;
            }
        });
    }
    private void showMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("삭제하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        Log.d("Log","longClick");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //예 누르면
                delete_content(position);
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //아니오 누르면
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
    private void delete_content(final int position){
        //storage.getReference().child("MapDB").child(uidLists.get(position)).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
        // @Override
        // public void onSuccess(Void aVoid) {

        database.getReference().child("MapDB").child(uidLists.get(position)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(getApplicationContext(), "삭제가 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "삭제 실패", Toast.LENGTH_SHORT).show();
            }
        });
        // }
        // }).addOnFailureListener(new OnFailureListener() {
        //    @Override
        //   public void onFailure(@NonNull Exception e) {
        //

        //   }
        // });

    }

    //추가하기버튼
    public void checkBoxCreateButton(View view) {

        adapter.addItem(new CheckBoxItem(ed_cbItem.getText().toString()));
        upload();
        adapter.notifyDataSetChanged();
        ed_cbItem.setText("");
    }


    class CheckBoxListAdapter extends BaseAdapter {
        ArrayList<CheckBoxItem> items = new ArrayList<CheckBoxItem>();
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
        public void addItem(CheckBoxItem item){
            items.add(item);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            CheckBoxItemView  view = new CheckBoxItemView(getApplicationContext());

            CheckBoxItem item = items.get(position);
            view.setContent(item.getContent());
            return view;
        }
    }
    private void upload() {

        CheckListDTO checkListDTO = new CheckListDTO();
        checkListDTO.title=title;
        checkListDTO.content=ed_cbItem.getText().toString();

        database.getReference().child("MapDB").push().setValue(checkListDTO);
    }
}
