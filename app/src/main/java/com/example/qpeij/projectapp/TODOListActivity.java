package com.example.qpeij.projectapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TODOListActivity extends AppCompatActivity {
    //db
    ListDBHelper lDBHelper;
    SQLiteDatabase db;
    Cursor cursor;
    MyCursorAdapter mCursorAdapter;

    //코드 내용
    final static String KEY_ID = "_id";
    final static String KEY_TITLE = "title";
    final static String TABLE_NAME = "listTable";

    final static String querySelectAll = String.format("SELECT * FROM %s",TABLE_NAME);
    ListView listView;

    EditText ed_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

        listView = (ListView)findViewById(R.id.listview);
        lDBHelper = new ListDBHelper(this);
        db = lDBHelper.getWritableDatabase();

        cursor=db.rawQuery(querySelectAll,null);
        mCursorAdapter = new MyCursorAdapter(this,cursor);

        listView.setAdapter(mCursorAdapter);

        ed_title = (EditText)findViewById(R.id.ed_listTitle);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),CheckBoxListActivity.class);
                Cursor cursor = (Cursor) mCursorAdapter.getItem(position);
                String index = cursor.getString(cursor.getColumnIndex("_id"));
                int idd = Integer.parseInt(index);

                intent.putExtra("id",idd);
                startActivityForResult(intent,0);
            }
        });
    }

    public void checklistCreateButton(View view) {
        String title = ed_title.getText().toString();
        try{
            String query = String.format("INSERT INTO %s VALUES(null,'%s');",TABLE_NAME, title);
            db.execSQL(query);
            // 아래 메서드를 실행하면 리스트가 갱신된다. 하지만 구글은 이 메서드를 deprecate한다. 고로 다른 방법으로 해보자.
            // cursor.requery();
            cursor = db.rawQuery( querySelectAll, null );
            mCursorAdapter.changeCursor( cursor );
        }catch (NullPointerException e){
            Toast.makeText(getApplicationContext(),"리스트 제목을 입력하시오.",Toast.LENGTH_LONG).show();
        }
        ed_title.setText("");
        InputMethodManager imm =
                (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE );
        imm.hideSoftInputFromWindow( ed_title.getWindowToken(), 0 );
    }

    class MyCursorAdapter extends CursorAdapter
    {

        @SuppressWarnings("deprecation")
        public MyCursorAdapter(Context context, Cursor c) {
            super(context, c);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView tvTitle = (TextView) view.findViewById( R.id.tv_checklistTitle );

            String title = cursor.getString( cursor.getColumnIndex( KEY_TITLE));


            Log.d("스트링 확인",  "" + title);

            tvTitle.setText( title );

        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from( context );
            View v = inflater.inflate( R.layout.checklist_item, parent, false );
            return v;
        }

    }
}
