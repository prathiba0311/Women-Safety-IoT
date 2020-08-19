package com.example.practice;
import android.content.ContentValues;
import java.text.SimpleDateFormat;
import java.util.*;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
public class DBManager {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(int id) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.ID, id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        contentValue.put(DatabaseHelper.TIME, currentDateandTime);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public int fetch() {
        String[] columns = new String[] { DatabaseHelper.ID, DatabaseHelper.TIME };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor.getCount();
    }

    public void clear() {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.ID + "=" + DatabaseHelper.ID, null);
    }
}
