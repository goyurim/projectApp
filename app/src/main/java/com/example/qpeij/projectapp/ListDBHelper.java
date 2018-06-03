package com.example.qpeij.projectapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.qpeij.projectapp.TODOListActivity.KEY_ACHIEV;
import static com.example.qpeij.projectapp.TODOListActivity.KEY_TITLE;
import static com.example.qpeij.projectapp.TODOListActivity.TABLE_NAME;

public class ListDBHelper extends SQLiteOpenHelper {
    public ListDBHelper (Context context){
        super(context, "Database.db",null,1);
    }
    @Override

    //고유림 수정
    public void onCreate(SQLiteDatabase db) {
        // AUTOINCREMENT 속성 사용 시 PRIMARY KEY로 지정한다.
        String query = String.format( "CREATE TABLE %s ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "%s TEXT,"
                + "%s INTEGER); " , TABLE_NAME, KEY_TITLE, KEY_ACHIEV );
        db.execSQL( query );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format( "DROP TABLE IF EXISTS %s", TABLE_NAME );
        db.execSQL( query );
        onCreate( db );
    }

    public void modifyUpgrade(int achiev, int id){
        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("UPDATE %s SET %s = %d WHERE _id = %d",TABLE_NAME, KEY_ACHIEV, achiev, id);
        db.execSQL(query);
        db.close();
    }
}
