package com.estiam.amanciodossou.tabproject.dummy;

import android.provider.BaseColumns;

/**
 * Created by Jim.
 */

public final class DatabaseContract {
    private DatabaseContract() {} // make non-creatable

    public static final class DocumentInfoEntry implements BaseColumns {
        public static final String TABLE_NAME = "document_info";
        public static final String COLUMN_DOCUMENT_ID = "document_id";
        public static final String COLUMN_DOCUMENT_TITLE = "document_title";

        // CREATE INDEX course_info_index1 ON course_info (document_title)
        public static final String INDEX1 = TABLE_NAME + "_index1";
        public static final String SQL_CREATE_INDEX1 =
                "CREATE INDEX " + INDEX1 + " ON " + TABLE_NAME +
                        "(" + COLUMN_DOCUMENT_TITLE + ")";

        public static final String getQName(String columnName) {
            return TABLE_NAME + "." + columnName;
        }

        // CREATE TABLE document_info (course_id, course_title)
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_DOCUMENT_ID + " TEXT UNIQUE NOT NULL, " +
                        COLUMN_DOCUMENT_TITLE + " TEXT NOT NULL)";

        public static String getDbImageTable() {
            return DB_IMAGE_TABLE;
        }

        // bitmap Table Names
        private static final String DB_IMAGE_TABLE = "table_image";

        public static String getKeyName() {
            return KEY_NAME;
        }

        public static String getKeyImage() {
            return KEY_IMAGE;
        }

        // bitmap column names
        private static final String KEY_NAME = "image_name";
        private static final String KEY_IMAGE = "image_data";

        // bitmap table create statement
        public static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + DB_IMAGE_TABLE + "("+
                KEY_NAME + " TEXT," +
                KEY_IMAGE + " BLOB);";
    }

}












