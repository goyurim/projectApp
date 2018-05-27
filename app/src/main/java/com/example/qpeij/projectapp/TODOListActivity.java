package com.example.qpeij.projectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TODOListActivity extends AppCompatActivity {
    public static final int REQUEST_CHECKBOX = 100;
    ListView listView;
    ListItemAdapter adapter;
    EditText ed_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

        listView = (ListView)findViewById(R.id.listview);
        adapter = new ListItemAdapter();

        adapter.addItem(new CheckListItem("title","goal"));
        adapter.addItem(new CheckListItem("title2", "goal2"));
        adapter.addItem(new CheckListItem("title3", "goal3"));

        listView.setAdapter(adapter);
        ed_title = (EditText)findViewById(R.id.ed_listTitle);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),CheckBoxListActivity.class);
                startActivityForResult(intent,REQUEST_CHECKBOX);
            }
        });
    }

    public void checklistCreateButton(View view) {
        String title = ed_title.getText().toString();
        adapter.addItem(new CheckListItem(title,"goal"));
        adapter.notifyDataSetChanged();
    }


    class ListItemAdapter extends BaseAdapter {
        ArrayList<CheckListItem> items = new ArrayList<CheckListItem>();
        @Override
        public int getCount() {
            return items.size();
        }
        public void addItem(CheckListItem item){
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
            CheckListItemView view = new CheckListItemView(getApplicationContext());
            CheckListItem item = items.get(position);

            view.setTitle(item.getTitle());
            view.setGoal(item.getGoal());

            return view;
        }
    }
}
