package com.example.qpeij.projectapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CheckBoxList2Activity extends AppCompatActivity {
    EditText ed_cbItem; //체크박스 내용 입력받는 것
    ListView listView;// 리스트 뷰
    TextView checklistTitle;//체크박스 리스트 이름
    int id;//아이디 값
    String title;
    CheckBoxListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box_list2);

        //TODOList 화면에서 받음
        Intent intent = getIntent();
        title = intent.getStringExtra("title"); //리스트 제목
        id = intent.getIntExtra("id",0);//리스트 아이디값
        listView = (ListView) findViewById(R.id.checkboxlistview);
        ed_cbItem=(EditText)findViewById(R.id.ed_checkboxItem) ;
        //타이틀 이름 지정
        checklistTitle = (TextView)findViewById(R.id.checklistTitle);
        checklistTitle.setText(title);

        //리스트 뷰 지정
        adapter=new CheckBoxListAdapter();
        listView.setAdapter(adapter);
    }
//추가하기버튼
    public void checkBoxCreateButton(View view) {

        adapter.addItem(new CheckBoxItem(ed_cbItem.getText().toString()));
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
        public View getView(int position, View convertView, ViewGroup viewGroup)
        {
            CheckBoxItemView  view = new CheckBoxItemView(getApplicationContext());

            CheckBoxItem item = items.get(position);
            view.setContent(item.getContent());
            return view;
        }
    }
}
