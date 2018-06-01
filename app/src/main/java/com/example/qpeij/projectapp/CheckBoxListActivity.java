package com.example.qpeij.projectapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CheckBoxListActivity extends AppCompatActivity {
    EditText ed_cbItem; //체크박스 내용 입력받는 것
    ListView listView;// 리스트 뷰
    TextView checklistTitle;//체크박스 리스트 이름
    int id;//아이디 값

    final static String KEY_CONTENT = "content";
    final static String KEY_ID = "_id";
    final static String TABLE_NAME = "cbListTable";
    String querySelectAll;
    //db
    CBDBHelper cbDBHelper;
    SQLiteDatabase cbdb;
    Cursor cbcursor;
    CBCursorAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box_list);
        //TODOList 화면에서 받음
        Intent intent = getIntent();
        String title = intent.getStringExtra("title"); //리스트 제목
        id = intent.getIntExtra("id",0);//리스트 아이디값
        listView = (ListView) findViewById(R.id.checkboxlistview);
        querySelectAll = String.format("SELECT * FROM %s WHERE _id = %d;",TABLE_NAME,id);

        //db
        cbDBHelper = new CBDBHelper(this);
        cbdb = cbDBHelper.getWritableDatabase();
        cbcursor = cbdb.rawQuery(querySelectAll,null);
        adapter = new CBCursorAdapter(this, cbcursor);
        Log.d("Log", "ok4");


        //타이틀 이름 지정
        checklistTitle = (TextView)findViewById(R.id.checklistTitle);
        checklistTitle.setText(title);

        //리스트 뷰 지정

        listView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void checkBoxCreateButton(View view) {
        ed_cbItem = (EditText) findViewById(R.id.ed_checkboxItem);
        String content = ed_cbItem.getText().toString();
        try{
            String query = String.format("INSERT INTO %s VALUES(%d,'%s');",TABLE_NAME,id, content);
            cbdb.execSQL(query);

            cbcursor = cbdb.rawQuery(querySelectAll,null);
            adapter.changeCursor(cbcursor);

        }catch (NullPointerException e){
            Toast.makeText(getApplicationContext(),"추가할 내용을 작성하시오", Toast.LENGTH_LONG).show();
        }
        ed_cbItem.setText("");
        InputMethodManager imm =
                (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE );
        imm.hideSoftInputFromWindow( ed_cbItem.getWindowToken(), 0 );
    }

    private class CBCursorAdapter extends CursorAdapter
    {
        @SuppressWarnings("deprecation")
        private CBCursorAdapter(Context context, Cursor c) {super(context, c);  }

        public void bindView(View view, Context context, Cursor cursor) {
            CheckBox tv_checkboxContent = (CheckBox) view.findViewById(R.id.checkboxContent);
            String content = cursor.getString( cursor.getColumnIndex( KEY_CONTENT));
            Log.d("스트링 확인",  "" + content);
            tv_checkboxContent.setText( content );
        }

        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from( context );
            View v = inflater.inflate( R.layout.checkbox_item, parent, false );
            return v;
        }
    }
}
