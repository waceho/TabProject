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

    //region Initialization code

    private void initializedocuments() {
        mdocuments.add(initializedocument1());
        mdocuments.add(initializedocument2());
        mdocuments.add(initializedocument3());
        mdocuments.add(initializedocument4());
    }

    public void initializeExampleNotes() {
        final DataManager dm = getInstance();

        DocumentInfo document = dm.getdocument("android_intents");
        document.getModule("android_intents_m01").setComplete(true);
        document.getModule("android_intents_m01").setComplete(true);
        document.getModule("android_intents_m02").setComplete(true);
        document.getModule("android_intents_m03").setComplete(true);
        mNotes.add(new NoteInfo(document, "Dynamic intent resolution",
                "Wow, intents allow components to be resolved at runtime"));
        mNotes.add(new NoteInfo(document, "Delegating intents",
                "PendingIntents are powerful; they delegate much more than just a component invocation"));

        document = dm.getdocument("android_async");
        document.getModule("android_async_m01").setComplete(true);
        document.getModule("android_async_m02").setComplete(true);
        mNotes.add(new NoteInfo(document, "Service default threads",
                "Did you know that by default an Android Service will tie up the UI thread?"));
        mNotes.add(new NoteInfo(document, "Long running operations",
                "Foreground Services can be tied to a notification icon"));

        document = dm.getdocument("java_lang");
        document.getModule("java_lang_m01").setComplete(true);
        document.getModule("java_lang_m02").setComplete(true);
        document.getModule("java_lang_m03").setComplete(true);
        document.getModule("java_lang_m04").setComplete(true);
        document.getModule("java_lang_m05").setComplete(true);
        document.getModule("java_lang_m06").setComplete(true);
        document.getModule("java_lang_m07").setComplete(true);
        mNotes.add(new NoteInfo(document, "Parameters",
                "Leverage variable-length parameter lists"));
        mNotes.add(new NoteInfo(document, "Anonymous classes",
                "Anonymous classes simplify implementing one-use types"));

        document = dm.getdocument("java_core");
        document.getModule("java_core_m01").setComplete(true);
        document.getModule("java_core_m02").setComplete(true);
        document.getModule("java_core_m03").setComplete(true);
        mNotes.add(new NoteInfo(document, "Compiler options",
                "The -jar option isn't compatible with with the -cp option"));
        mNotes.add(new NoteInfo(document, "Serialization",
                "Remember to include SerialVersionUID to assure version compatibility"));
    }

    private DocumentInfo initializedocument1() {
        List<ModuleInfo> modules = new ArrayList<>();
        modules.add(new ModuleInfo("android_intents_m01", "Android Late Binding and Intents"));
        modules.add(new ModuleInfo("android_intents_m02", "Component activation with intents"));
        modules.add(new ModuleInfo("android_intents_m03", "Delegation and Callbacks through PendingIntents"));
        modules.add(new ModuleInfo("android_intents_m04", "IntentFilter data tests"));
        modules.add(new ModuleInfo("android_intents_m05", "Working with Platform Features Through Intents"));

        return new DocumentInfo("android_intents", "Android Programming with Intents", modules);
    }

    private DocumentInfo initializedocument2() {
        List<ModuleInfo> modules = new ArrayList<>();
        modules.add(new ModuleInfo("android_async_m01", "Challenges to a responsive user experience"));
        modules.add(new ModuleInfo("android_async_m02", "Implementing long-running operations as a service"));
        modules.add(new ModuleInfo("android_async_m03", "Service lifecycle management"));
        modules.add(new ModuleInfo("android_async_m04", "Interacting with services"));

        return new DocumentInfo("android_async", "Android Async Programming and Services", modules);
    }

    private DocumentInfo initializedocument3() {
        List<ModuleInfo> modules = new ArrayList<>();
        modules.add(new ModuleInfo("java_lang_m01", "Introduction and Setting up Your Environment"));
        modules.add(new ModuleInfo("java_lang_m02", "Creating a Simple App"));
        modules.add(new ModuleInfo("java_lang_m03", "Variables, Data Types, and Math Operators"));
        modules.add(new ModuleInfo("java_lang_m04", "Conditional Logic, Looping, and Arrays"));
        modules.add(new ModuleInfo("java_lang_m05", "Representing Complex Types with Classes"));
        modules.add(new ModuleInfo("java_lang_m06", "Class Initializers and Constructors"));
        modules.add(new ModuleInfo("java_lang_m07", "A Closer Look at Parameters"));
        modules.add(new ModuleInfo("java_lang_m08", "Class Inheritance"));
        modules.add(new ModuleInfo("java_lang_m09", "More About Data Types"));
        modules.add(new ModuleInfo("java_lang_m10", "Exceptions and Error Handling"));
        modules.add(new ModuleInfo("java_lang_m11", "Working with Packages"));
        modules.add(new ModuleInfo("java_lang_m12", "Creating Abstract Relationships with Interfaces"));
        modules.add(new ModuleInfo("java_lang_m13", "Static Members, Nested Types, and Anonymous Classes"));

        return new DocumentInfo("java_lang", "Java Fundamentals: The Java Language", modules);
    }

    private DocumentInfo initializedocument4() {
        List<ModuleInfo> modules = new ArrayList<>();
        modules.add(new ModuleInfo("java_core_m01", "Introduction"));
        modules.add(new ModuleInfo("java_core_m02", "Input and Output with Streams and Files"));
        modules.add(new ModuleInfo("java_core_m03", "String Formatting and Regular Expressions"));
        modules.add(new ModuleInfo("java_core_m04", "Working with Collections"));
        modules.add(new ModuleInfo("java_core_m05", "Controlling App Execution and Environment"));
        modules.add(new ModuleInfo("java_core_m06", "Capturing Application Activity with the Java Log System"));
        modules.add(new ModuleInfo("java_core_m07", "Multithreading and Concurrency"));
        modules.add(new ModuleInfo("java_core_m08", "Runtime Type Information and Reflection"));
        modules.add(new ModuleInfo("java_core_m09", "Adding Type Metadata with Annotations"));
        modules.add(new ModuleInfo("java_core_m10", "Persisting Objects with Serialization"));

        return new DocumentInfo("java_core", "Java Fundamentals: The Core Platform", modules);
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
