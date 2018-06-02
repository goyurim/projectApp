package com.example.qpeij.projectapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
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
    int count = 0;
    boolean checked = false;
    private List<String> uidLists = new ArrayList<>();
    private List<Boolean> isChecked = new ArrayList<>();
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

        //data꺼내기
        database.getReference().child("MapDB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                uidLists.clear();
                isChecked.clear();
                adapter.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    checkBoxItem = snapshot.getValue(CheckBoxItem.class);
                    String uidKey = snapshot.getKey();
                    if(title.equals(checkBoxItem.getTitle())){
                        adapter.addItem(checkBoxItem);
                        listView.setAdapter(adapter);
                        uidLists.add(uidKey);
                       // checked=checkBoxItem.isChecked;
                        isChecked.add(checkBoxItem.isChecked);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        //클릭시 취소 선 생성
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView tv = (TextView)view.findViewById(R.id.checkboxContent);
                checked=isChecked.get(position);
                //tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                Log.d("보냄",position+"");
                if(checked == false){
                    //체크가 안되있다면.
                    Log.d("checked",checked+"");

                    isChecked.set(position,true);
                    update(position,true);
                    tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    count++;
                }else if(checked==true){

                    isChecked.set(position,false);
                    update(position,false);
                    tv.setPaintFlags(0);
                    count--;
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
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
        public View getView(int position, View convertView, ViewGroup parent) {
//            int all = getCount();
            CheckBoxItemView view = new CheckBoxItemView(getApplicationContext());
            //TextView tv = (TextView) findViewById(R.id.tv_goal);
            TextView textView = (TextView)view.checkboxItem;
//            int goal = (count/all)*100;
//            tv.setText(""+goal);

            CheckBoxItem item = items.get(position);
            Boolean checked=isChecked.get(position);
            /*if(각 items의 checked부분의 불린 값이 true이면 밑줄 false 밑줄 아닌 조건문 생성){
                .setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else{
                .setPaintFlags(0);
            }
            */
            if(checked == false){
                //체크가 안되있다면.
                Log.d("checked",checked+"");
                textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }else if(checked==true){

                textView.setPaintFlags(0);
            }

            view.setContent(item.getContent());
            return view;
        }
    }
    private void upload() {

        CheckListDTO checkListDTO = new CheckListDTO();
        checkListDTO.title=title;
        checkListDTO.content=ed_cbItem.getText().toString();
        checkListDTO.isChecked=false;
        database.getReference().child("MapDB").push().setValue(checkListDTO);
        adapter.notifyDataSetChanged();
    }
    private void update(int position,Boolean checked){
        Log.d("받음",position+"");
       // Log.d("log","업데이트");
        database.getReference().child("MapDB").child(uidLists.get(position)).child("isChecked").setValue(checked);
        adapter.notifyDataSetChanged();
    }

}
