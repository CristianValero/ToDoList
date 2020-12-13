package com.titianvalero.todolist.Utils.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {

    private FeedReaderDatabaseHelper databaseHelper;
    private final Context context;

    public DatabaseManager(FeedReaderDatabaseHelper databaseHelper, Context context) {
        this.databaseHelper = databaseHelper;
        this.context = context;
    }

    private void close() {
        if ( databaseHelper != null ) {
            databaseHelper.close();
        }
    }

    public void insertData(final String query) {
        databaseHelper = new FeedReaderDatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        database.execSQL(query);
        close();
    }

    public void deleteData(final String entryName) {
        databaseHelper = new FeedReaderDatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        database.delete(FeedReaderDatabaseHelper.DATABASE_NAME, "entry='" + entryName + "'", null);
        close();
    }

    public Cursor getQuery(final String query) {
        databaseHelper = new FeedReaderDatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        Cursor cursor = database.query(FeedReaderDatabaseHelper.DATABASE_NAME, new String[]{"id", "entry", "priority", "date_expire"}, null, null, null, null, null);
        database.close();
        return cursor;
    }

}
