package com.estiam.amanciodossou.tabproject.dummy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.estiam.amanciodossou.tabproject.Current;
import com.estiam.amanciodossou.tabproject.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by Jim.
 */

public class DatabaseDataWorker {
    private SQLiteDatabase mDb;
    private OpenHelper mDbOpenHelper;
    private Context mContext;
    private Current current;

    public DatabaseDataWorker(SQLiteDatabase db, Context context) {
        mDb = db;
        mContext = context;

        current = (Current)mContext.getApplicationContext();
    }

    private void insertDocument(String documentId, String title) {


        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DocumentInfoEntry.COLUMN_DOCUMENT_ID, documentId);
        values.put(DatabaseContract.DocumentInfoEntry.COLUMN_DOCUMENT_TITLE, title);

        long newRowId = mDb.insert(DatabaseContract.DocumentInfoEntry.TABLE_NAME, null, values);
    }

    public void inserImage(Bitmap view){

        Bitmap b = BitmapFactory.decodeResource(mContext.getResources(),
                R.id.drawview);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        view.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] img = bos.toByteArray();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DocumentInfoEntry.getKeyName(), current.getId());
        values.put(DatabaseContract.DocumentInfoEntry.getKeyImage(), img);
        SQLiteDatabase database = null;
        mDbOpenHelper = new OpenHelper(mContext);
        try {
            database = mDbOpenHelper.getWritableDatabase();
            database.insert(DatabaseContract.DocumentInfoEntry.getDbImageTable(), null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    /**
     *
     * @param bmp
     * @return
     */
    private byte[] convertImageTobyte(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


}
