package com.example.qpeij.projectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CheckBoxListActivity extends AppCompatActivity {
    CheckboxItemAdapter adapter;
    EditText ed_cbItem;
    ListView listView;
    TextView checklistTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box_list);

        //TODOList 화면에서 받음
        Intent intent = getIntent();
        String title = intent.getStringExtra("title"); //리스트 제목
        int id = intent.getIntExtra("id",0);//리스트 아이디값

        listView = (ListView) findViewById(R.id.checkboxlistview);
        adapter = new CheckboxItemAdapter();
        checklistTitle = (TextView)findViewById(R.id.checklistTitle);
        checklistTitle.setText(title);
        listView.setAdapter(adapter);
    }

    public void checkBoxCreateButton(View view) {
        ed_cbItem = (EditText) findViewById(R.id.ed_checkboxItem);
        String content = ed_cbItem.getText().toString();
        if(content.equals("") ){
            Toast.makeText(getApplicationContext(),"추가할 내용을 작성하시오", Toast.LENGTH_LONG).show();
        }else{
            adapter.addItem(new CheckBoxItem(content));
            adapter.notifyDataSetChanged();
            ed_cbItem.setText("");
        }
    }

    class CheckboxItemAdapter extends BaseAdapter {
        ArrayList<CheckBoxItem> items = new ArrayList<CheckBoxItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(CheckBoxItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            CheckBoxItemView view = new CheckBoxItemView(getApplicationContext());
            CheckBoxItem item = items.get(position);

            view.setContent(item.getContent());
            return view;
        }
    }
}
