package com.example.qpeij.projectapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckBoxListActivity extends AppCompatActivity {
    CheckboxItemAdapter adapter;
    EditText ed_cbItem;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box_list);
        listView = (ListView) findViewById(R.id.checkboxlistview);
        adapter = new CheckboxItemAdapter();

        listView.setAdapter(adapter);
    }

    public void checkBoxCreateButton(View view) {
        ed_cbItem = (EditText) findViewById(R.id.ed_checkboxItem);
        String content = ed_cbItem.getText().toString();
        adapter.addItem(new CheckBoxItem(content));
        adapter.notifyDataSetChanged();
        ed_cbItem.setText("");
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
