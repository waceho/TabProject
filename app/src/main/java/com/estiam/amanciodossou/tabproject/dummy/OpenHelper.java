package com.estiam.amanciodossou.tabproject.dummy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.estiam.amanciodossou.tabproject.dummy.DatabaseContract.DocumentInfoEntry;


/**
 * Created by Jim.
 */

public class OpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = ".db";
    public static final int DATABASE_VERSION = 2;
    public OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DocumentInfoEntry.SQL_CREATE_TABLE);
        db.execSQL(DocumentInfoEntry.CREATE_TABLE_IMAGE);

        db.execSQL(DocumentInfoEntry.SQL_CREATE_INDEX1);

       // DatabaseDataWorker worker = new DatabaseDataWorker(db,);
       // worker.insertdocuments();
       // worker.insertSampleNotes();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 2) {
            db.execSQL(DocumentInfoEntry.SQL_CREATE_INDEX1);
        }
    }
}
