package com.example.qpeij.projectapp;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.qpeij.projectapp.CheckBoxListActivity.KEY_CONTENT;
import static com.example.qpeij.projectapp.CheckBoxListActivity.KEY_ID;
import static com.example.qpeij.projectapp.CheckBoxListActivity.TABLE_NAME;

public class CBDBHelper extends SQLiteOpenHelper {
    public CBDBHelper(Context context) { super(context, "CBData.db",null,1); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TABLE NAME = cbListTable, KEY_ID = id KEY_CONTENT = content 라는 변수 명을 가진 테이블 구성
        String query = String.format("CREATE TABLE %s ( "
                                        +"%s INTEGER,"
                                        +"%s TEXT);", TABLE_NAME, KEY_ID, KEY_CONTENT);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        //새로운 버전으로 업그레이드 될 경우 있던 테이블 삭제 후 다시 onCreate함
        String query = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(query);
        onCreate(db);
    }
}
