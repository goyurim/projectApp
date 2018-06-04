package com.example.qpeij.projectapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TODOListActivity extends AppCompatActivity {
    //db
    ListDBHelper lDBHelper;
    SQLiteDatabase db;
    Cursor cursor;
    MyCursorAdapter mCursorAdapter;
//int pos;
    //달성도를 위한 파이어베이스 디비
    FirebaseDatabase database;

    //코드 내용
    final static String KEY_ACHIEV = "achiev";
    final static String KEY_ID = "_id";
    final static String KEY_TITLE = "title";
    final static String TABLE_NAME = "listTable";

    final static String querySelectAll = String.format("SELECT * FROM %s ORDER BY _id DESC",TABLE_NAME);

    long posid;
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
                Intent intent = new Intent(TODOListActivity.this, CheckBoxList2Activity.class);
                Cursor cursor = (Cursor) mCursorAdapter.getItem(position);

                String title = cursor.getString( cursor.getColumnIndex( KEY_TITLE));
                int index = cursor.getInt(cursor.getColumnIndex("_id"));

                intent.putExtra("title",title);
                intent.putExtra("id",index);
                startActivityForResult(intent,0);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                posid = id;
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
                db.execSQL(String.format("DELETE FROM %s WHERE %s = %d", TABLE_NAME, KEY_ID , posid));
                cursor = db.rawQuery( querySelectAll, null );
                mCursorAdapter.changeCursor( cursor );
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cursor = db.rawQuery( querySelectAll, null );
                mCursorAdapter.changeCursor( cursor );
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public void checklistCreateButton(View view) {
        String title = ed_title.getText().toString();
        if(title.equals(""))
            Toast.makeText(getApplicationContext(),"내용을 입력하세요.",Toast.LENGTH_LONG).show();
        else {
            try {
                //고유림: 입력 첫 생성되는 리스트는 달성도를 기본값을 0으로 함
                String query = String.format("INSERT INTO %s VALUES(null,'%s',%d);", TABLE_NAME, title,0);
                db.execSQL(query);
                // 아래 메서드를 실행하면 리스트가 갱신된다. 하지만 구글은 이 메서드를 deprecate한다. 고로 다른 방법으로 해보자.
                // cursor.requery();
                cursor = db.rawQuery(querySelectAll, null);
                mCursorAdapter.changeCursor(cursor);
            } catch (NullPointerException e) {
                Toast.makeText(getApplicationContext(), "리스트 제목을 입력하시오.", Toast.LENGTH_LONG).show();
            }
            ed_title.setText("");
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(ed_title.getWindowToken(), 0);
        }
    }

    private class MyCursorAdapter extends CursorAdapter
    {

        @SuppressWarnings("deprecation")
        public MyCursorAdapter(Context context, Cursor c) { super(context, c); }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView tvTitle = (TextView) view.findViewById( R.id.tv_checklistTitle );
            String title = cursor.getString( cursor.getColumnIndex( KEY_TITLE));
            //고유림 입력
            int achievment = cursor.getInt(cursor.getColumnIndex(KEY_ACHIEV));
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
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==pos){
            int goal=data.getIntExtra("goal",0);
            CheckListItem item=(CheckListItem)mCursorAdapter.getItem(pos);
            item.setGoal(goal);
        }
    }*/
}
