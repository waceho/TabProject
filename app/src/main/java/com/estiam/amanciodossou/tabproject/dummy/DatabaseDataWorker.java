package com.estiam.amanciodossou.tabproject.dummy;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Jim.
 */

public class DatabaseDataWorker {
    private SQLiteDatabase mDb;

    public DatabaseDataWorker(SQLiteDatabase db) {
        mDb = db;
    }

    private void insertDocument(String documentId, String title) {


        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DocumentInfoEntry.COLUMN_DOCUMENT_ID, documentId);
        values.put(DatabaseContract.DocumentInfoEntry.COLUMN_DOCUMENT_TITLE, title);

        long newRowId = mDb.insert(DatabaseContract.DocumentInfoEntry.TABLE_NAME, null, values);
    }

}
