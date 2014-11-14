package com.xplink.android.carchecklist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBCarCheckList extends SQLiteOpenHelper {
    private static final String DB_NAME = "carchecklist";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "history";

    public static final String COL_USERNAME = "username";
    public static final String COL_CHECKLIST = "checklist";
    
    public DBCarCheckList(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " 
                + COL_USERNAME + " VARCHAR(25), " + COL_CHECKLIST + " TEXT);");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_USERNAME + ", " + COL_CHECKLIST +
        		") VALUES ('bank'" + "'engine-index1-f');");
    }
    
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}