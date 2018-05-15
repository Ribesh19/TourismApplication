package com.sarangcode.projecttama.Database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserListDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="User_info.db";
    private static final  int DATABASE_VERSION=1;



    public UserListDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_USERINFO_TABLE="CREATE TABLE"+ UserListContract.UserListEntry.TABLE_NAME+"("
                + UserListContract.UserListEntry.COLUMN_USER_NAME+
                UserListContract.UserListEntry.COLUMN_PASSWORD+
                UserListContract.UserListEntry.COLUMN_DESTINATION+");";
        db.execSQL(SQL_CREATE_USERINFO_TABLE);
        Log.e("msg","database created successfully");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+ UserListContract.UserListEntry.TABLE_NAME);
        onCreate(db);

    }
}
