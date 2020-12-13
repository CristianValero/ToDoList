package com.titianvalero.todolist.Utils.Database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class FeedReaderDatabaseHelper extends SQLiteOpenHelper {

    private final static String TASKS_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, entry VARCHAR(100), priority INTEGER, date_expire DATE);";
    private final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME = "tasks";

    public FeedReaderDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        db.execSQL(TASKS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

}
