package com.estiam.amanciodossou.tabproject.dummy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.estiam.amanciodossou.tabproject.dummy.DatabaseContract.DocumentInfoEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim.
 */

public class DataManager {
    private static DataManager ourInstance = null;

    private List<DocumentInfo> mdocuments = new ArrayList<>();
    private List<DrawedInfo> mimages = new ArrayList<>();
    private List<NoteInfo> mNotes = new ArrayList<>();

    public static DataManager getInstance() {
        if(ourInstance == null) {
            ourInstance = new DataManager();
//            ourInstance.initializedocuments();
//            ourInstance.initializeExampleNotes();
        }
        return ourInstance;
    }

    public static void loadFromDatabase(OpenHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final String[] documentColumns = {
                DocumentInfoEntry.COLUMN_DOCUMENT_ID,
                DocumentInfoEntry.COLUMN_DOCUMENT_TITLE};
        final Cursor documentCursor = db.query(DocumentInfoEntry.TABLE_NAME, documentColumns,
                null, null, null, null, DocumentInfoEntry.COLUMN_DOCUMENT_TITLE + " DESC");
        loaddocumentsFromDatabase(documentCursor);

    }

    public static void loadImageFromDatabase(OpenHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final String[] documentColumns = {
                DocumentInfoEntry.getKeyName(),
                DocumentInfoEntry.getKeyImage()};
        final Cursor documentCursor = db.query(DocumentInfoEntry.getDbImageTable(), documentColumns,
                null, null, null, null, DocumentInfoEntry.getKeyImage() + " DESC");
        loadimagesFromDatabase(documentCursor);

    }

    private static void loaddocumentsFromDatabase(Cursor cursor) {
        int documentIdPos = cursor.getColumnIndex(DocumentInfoEntry.COLUMN_DOCUMENT_ID);
        int documentTitlePos = cursor.getColumnIndex(DocumentInfoEntry.COLUMN_DOCUMENT_TITLE);

        DataManager dm = getInstance();
        dm.mdocuments.clear();
        while(cursor.moveToNext()) {
            String documentId = cursor.getString(documentIdPos);
            String documentTitle = cursor.getString(documentTitlePos);
            DocumentInfo document = new DocumentInfo(documentId, documentTitle, null);

            dm.mdocuments.add(document);
        }
        cursor.close();
    }

    private static void loadimagesFromDatabase(Cursor cursor) {
        int documentIdPos = cursor.getColumnIndex(DocumentInfoEntry.getKeyName());
        int documentTitlePos = cursor.getColumnIndex(DocumentInfoEntry.getKeyImage());

        DataManager dm = getInstance();
        dm.mdocuments.clear();
        while(cursor.moveToNext()) {
            String documentId = cursor.getString(documentIdPos);
            byte[] image = cursor.getBlob(documentTitlePos);
            DrawedInfo imageInfo = new DrawedInfo(documentId, image, null);

            dm.mimages.add(imageInfo);
        }
        cursor.close();
    }

    public List<NoteInfo> getNotes() {
        return mNotes;
    }

    public int createNewNote() {
        NoteInfo note = new NoteInfo(null, null, null);
        mNotes.add(note);
        return mNotes.size() - 1;
    }

    public int findNote(NoteInfo note) {
        for(int index = 0; index < mNotes.size(); index++) {
            if(note.equals(mNotes.get(index)))
                return index;
        }

        return -1;
    }

    public List<DocumentInfo> getdocuments() {
        return mdocuments;
    }

    public DrawedInfo getimage(String id) {
        for (DrawedInfo document : mimages) {
            if (document != null)
            if (id.equals(document.getimageId()))
                return document;
        }
        return null;
    }

    private DataManager() {
    }

}
