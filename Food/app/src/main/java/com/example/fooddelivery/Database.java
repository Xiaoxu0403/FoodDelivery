package com.example.fooddelivery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database {

    /**
     * create a database helper
     */
    private class DatabaseHelper extends SQLiteOpenHelper {

        private static final String name = "account";

        private static final int version = 1;

        public DatabaseHelper (Context context) {

            super(context, name, null, version);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //create tables
            db.execSQL("CREATE TABLE IF NOT EXISTS user (account varchar(20) primary key, password varchar(20))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    /**
     * initialize a database object
     * @param context
     */
    public Database(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * get writable database
     */
    private void open() {
        db = dbHelper.getWritableDatabase();
    }

    private void close() {
        db.close();
    }

    /**
     * insert new user into database
     * @param account account
     * @param password password
     * @return state
     */
    private int insertUser(String account, String password) {
        open();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE account=?", new String[]{account});
        if (cursor.moveToFirst()) {
            close();
            return 0;
        } else {
            db.execSQL("INSERT INTO user VALUES('" + account + "', '" + password + "')");
            close();
            return 1;
        }
    }

    /**
     * check login state
     * @param account
     * @param password
     * @return login result
     */
    public int CheckLogin(String account, String password) {
        open();
        Cursor cursor = db.rawQuery("SELECT password FROM user WHERE account=?", new String[]{account});
        if (cursor.moveToFirst()) {
            if (cursor.getString(0).equals(password)) {
                close();
                return 1;
            } else {
                close();
                return -1;
            }
        } else {
            insertUser(account, password);
            return 0;
        }
    }

}