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

    public String getCurrentUserName() {
        return "Jim Wilson";
    }

    public String getCurrentUserEmail() {
        return "jimw@jwhh.com";
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

    public void removeNote(int index) {
        mNotes.remove(index);
    }

    public List<DocumentInfo> getdocuments() {
        return mdocuments;
    }

    public DocumentInfo getdocument(String id) {
        for (DocumentInfo document : mdocuments) {
            if (id.equals(document.getdocumentId()))
                return document;
        }
        return null;
    }

    public List<NoteInfo> getNotes(DocumentInfo document) {
        ArrayList<NoteInfo> notes = new ArrayList<>();
        for(NoteInfo note:mNotes) {
            if(document.equals(note.getdocument()))
                notes.add(note);
        }
        return notes;
    }

    public int getNoteCount(DocumentInfo document) {
        int count = 0;
        for(NoteInfo note:mNotes) {
            if(document.equals(note.getdocument()))
                count++;
        }
        return count;
    }

    private DataManager() {
    }

    public int createNewNote(DocumentInfo document, String noteTitle, String noteText) {
        int index = createNewNote();
        NoteInfo note = getNotes().get(index);
        note.setdocument(document);
        note.setTitle(noteTitle);
        note.setText(noteText);

        return index;
    }
    //endregion

}
