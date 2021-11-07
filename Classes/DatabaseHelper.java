package com.example.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context c;
    private static final String DATABASE_NAME = "MyNurseryDB";
    private static final String KID_TABLE_NAME = "Kid";
    private static final String STAFF_TABLE_NAME = "Staff";
    private static final String EXPENSE_TABLE_NAME = "Expense";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context c){
        super(c,DATABASE_NAME,null,DATABASE_VERSION);
        this.c = c;
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + KID_TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT,pName TEXT,contact TEXT)");
        db.execSQL("CREATE TABLE " + STAFF_TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT,password TEXT,isAdmin INTEGER)");
        db.execSQL("CREATE TABLE " + EXPENSE_TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "detail TEXT,amount TEXT,date TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int i, int j){
        db.delete(KID_TABLE_NAME,null,null);
        db.delete(STAFF_TABLE_NAME,null,null);
        db.delete(EXPENSE_TABLE_NAME,null,null);
        onCreate(db);
    }


}
