package com.estiam.amanciodossou.tabproject;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.estiam.amanciodossou.tabproject.dummy.DocumentInfo;
import com.estiam.amanciodossou.tabproject.dummy.DataManager;
import com.estiam.amanciodossou.tabproject.dummy.NoteInfo;
import com.estiam.amanciodossou.tabproject.dummy.DatabaseContract.DocumentInfoEntry;
import com.estiam.amanciodossou.tabproject.dummy.OpenHelper;

public class NoteActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>{
    public static final int LOADER_NOTES = 0;
    public static final int LOADER_documentS = 1;
    private final String TAG = getClass().getSimpleName();
    public static final String NOTE_ID = "com.jwhh.jim..NOTE_ID";
    public static final String ORIGINAL_NOTE_document_ID = "com.jwhh.jim..ORIGINAL_NOTE_document_ID";
    public static final String ORIGINAL_NOTE_TITLE = "com.jwhh.jim..ORIGINAL_NOTE_TITLE";
    public static final String ORIGINAL_NOTE_TEXT = "com.jwhh.jim..ORIGINAL_NOTE_TEXT";
    public static final int ID_NOT_SET = -1;
    private NoteInfo mNote = new NoteInfo(DataManager.getInstance().getdocuments().get(0), "", "");
    private boolean mIsNewNote;
    private Spinner mSpinnerdocuments;
    private EditText mTextNoteTitle;
    private EditText mTextNoteText;
    private int mNoteId;
    private boolean mIsCancelling;
    private String mOriginalNotedocumentId;
    private String mOriginalNoteTitle;
    private String mOriginalNoteText;
    private OpenHelper mDbOpenHelper;
    private Cursor mNoteCursor;
    private int mdocumentIdPos;
    private int mNoteTitlePos;
    private int mNoteTextPos;
    private SimpleCursorAdapter mAdapterdocuments;
    private boolean mdocumentsQueryFinished;
    private boolean mNotesQueryFinished;

    @Override
    protected void onDestroy() {
        mDbOpenHelper.close();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        setContentView(R.layout.activity_note);
        */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDbOpenHelper = new OpenHelper(this);

        /*
       mSpinnerdocuments = (Spinner) findViewById(R.id.spinner_documents);
         */



        mAdapterdocuments = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, null,
                new String[] {DocumentInfoEntry.COLUMN_DOCUMENT_TITLE},
                new int[] {android.R.id.text1}, 0);
        mAdapterdocuments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerdocuments.setAdapter(mAdapterdocuments);

        getLoaderManager().initLoader(LOADER_documentS, null, this);

        readDisplayStateValues();
        if(savedInstanceState == null) {
            saveOriginalNoteValues();
        } else {
            restoreOriginalNoteValues(savedInstanceState);
        }

        /*
        mTextNoteTitle = (EditText) findViewById(R.id.text_note_title);
        mTextNoteText = (EditText) findViewById(R.id.text_note_text);
*/
        if(!mIsNewNote)
            getLoaderManager().initLoader(LOADER_NOTES, null, this);

    }

    private void loaddocumentData() {
        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
        String[] documentColumns = {
                DocumentInfoEntry.COLUMN_DOCUMENT_TITLE,
                DocumentInfoEntry.COLUMN_DOCUMENT_ID,
                DocumentInfoEntry._ID
        };
        Cursor cursor = db.query(DocumentInfoEntry.TABLE_NAME, documentColumns,
                null, null, null, null, DocumentInfoEntry.COLUMN_DOCUMENT_TITLE);
        mAdapterdocuments.changeCursor(cursor);
    }

    private void restoreOriginalNoteValues(Bundle savedInstanceState) {
        mOriginalNotedocumentId = savedInstanceState.getString(ORIGINAL_NOTE_document_ID);
        mOriginalNoteTitle = savedInstanceState.getString(ORIGINAL_NOTE_TITLE);
        mOriginalNoteText = savedInstanceState.getString(ORIGINAL_NOTE_TEXT);
    }

    private void saveOriginalNoteValues() {
        if(mIsNewNote)
            return;
        mOriginalNotedocumentId = mNote.getdocument().getdocumentId();
        mOriginalNoteTitle = mNote.getTitle();
        mOriginalNoteText = mNote.getText();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mIsCancelling) {
            Log.i(TAG, "Cancelling note at position: " + mNoteId);
            if(mIsNewNote) {
                //deleteNoteFromDatabase();
            } else {
                storePreviousNoteValues();
            }
        } else {
          //  saveNote();
        }
        Log.d(TAG, "onPause");
    }

    private void storePreviousNoteValues() {
        DocumentInfo document = DataManager.getInstance().getdocument(mOriginalNotedocumentId);
        mNote.setdocument(document);
        mNote.setTitle(mOriginalNoteTitle);
        mNote.setText(mOriginalNoteText);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ORIGINAL_NOTE_document_ID, mOriginalNotedocumentId);
        outState.putString(ORIGINAL_NOTE_TITLE, mOriginalNoteTitle);
        outState.putString(ORIGINAL_NOTE_TEXT, mOriginalNoteText);
    }

    private String selecteddocumentId() {
        int selectedPosition = mSpinnerdocuments.getSelectedItemPosition();
        Cursor cursor = mAdapterdocuments.getCursor();
        cursor.moveToPosition(selectedPosition);
        int documentIdPos = cursor.getColumnIndex(DocumentInfoEntry.COLUMN_DOCUMENT_ID);
        String documentId = cursor.getString(documentIdPos);
        return documentId;
    }


    private void displayNote() {
        String documentId = mNoteCursor.getString(mdocumentIdPos);
        String noteTitle = mNoteCursor.getString(mNoteTitlePos);
        String noteText = mNoteCursor.getString(mNoteTextPos);

        int documentIndex = getIndexOfdocumentId(documentId);

        mSpinnerdocuments.setSelection(documentIndex);
        mTextNoteTitle.setText(noteTitle);
        mTextNoteText.setText(noteText);
    }

    private int getIndexOfdocumentId(String documentId) {
        Cursor cursor = mAdapterdocuments.getCursor();
        int documentIdPos = cursor.getColumnIndex(DocumentInfoEntry.COLUMN_DOCUMENT_ID);
        int documentRowIndex = 0;

        boolean more = cursor.moveToFirst();
        while(more) {
            String cursordocumentId = cursor.getString(documentIdPos);
            if(documentId.equals(cursordocumentId))
                break;

            documentRowIndex++;
            more = cursor.moveToNext();
        }
        return documentRowIndex;
    }

    private void readDisplayStateValues() {
        Intent intent = getIntent();
        mNoteId = intent.getIntExtra(NOTE_ID, ID_NOT_SET);
        mIsNewNote = mNoteId == ID_NOT_SET;
        if(mIsNewNote) {
           // createNewNote();
        }

        Log.i(TAG, "mNoteId: " + mNoteId);
//        mNote = DataManager.getInstance().getNotes().get(mNoteId);

    }


    private void moveNext() {
       // saveNote();

        ++mNoteId;
        mNote = DataManager.getInstance().getNotes().get(mNoteId);

        saveOriginalNoteValues();
        displayNote();
        invalidateOptionsMenu();
    }

    private void sendEmail() {
        DocumentInfo document = (DocumentInfo) mSpinnerdocuments.getSelectedItem();
        String subject = mTextNoteTitle.getText().toString();
        String text = "Checkout what I learned in the Pluralsight document \"" +
                document.getTitle() +"\"\n" + mTextNoteText.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = null;
        /*
        if(id == LOADER_NOTES)
            loader = createLoaderNotes();
        else if (id == LOADER_documentS)
            loader = createLoaderdocuments();
            */
        return loader;
    }

    private CursorLoader createLoaderdocuments() {
        mdocumentsQueryFinished = false;
        return new CursorLoader(this) {
            @Override
            public Cursor loadInBackground() {
                SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
                String[] documentColumns = {
                        DocumentInfoEntry.COLUMN_DOCUMENT_TITLE,
                        DocumentInfoEntry.COLUMN_DOCUMENT_ID,
                        DocumentInfoEntry._ID
                };
                return db.query(DocumentInfoEntry.TABLE_NAME, documentColumns,
                        null, null, null, null, DocumentInfoEntry.COLUMN_DOCUMENT_TITLE);

            }
        };
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
      //  if(loader.getId() == LOADER_NOTES)
          //  loadFinishedNotes(data);
         if(loader.getId() == LOADER_documentS) {
            mAdapterdocuments.changeCursor(data);
            mdocumentsQueryFinished = true;
            displayNoteWhenQueriesFinished();
        }
    }


    private void displayNoteWhenQueriesFinished() {
        if(mNotesQueryFinished && mdocumentsQueryFinished)
            displayNote();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(loader.getId() == LOADER_NOTES) {
            if(mNoteCursor != null)
                mNoteCursor.close();
        } else if(loader.getId() == LOADER_documentS) {
            mAdapterdocuments.changeCursor(null);
        }
    }
}












