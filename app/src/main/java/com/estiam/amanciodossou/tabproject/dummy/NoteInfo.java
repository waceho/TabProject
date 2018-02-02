package com.estiam.amanciodossou.tabproject.dummy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jim.
 */

public final class NoteInfo implements Parcelable {
    private DocumentInfo mdocument;
    private String mTitle;
    private String mText;
    private int mId;

    public NoteInfo(int id, DocumentInfo document, String title, String text) {
        mId = id;
        mdocument = document;
        mTitle = title;
        mText = text;
    }

    public NoteInfo(DocumentInfo document, String title, String text) {
        mdocument = document;
        mTitle = title;
        mText = text;
    }

    private NoteInfo(Parcel source) {
        mdocument = source.readParcelable(DocumentInfo.class.getClassLoader());
        mTitle = source.readString();
        mText = source.readString();
    }

    public int getId() {
        return mId;
    }

    public DocumentInfo getdocument() {
        return mdocument;
    }

    public void setdocument(DocumentInfo document) {
        mdocument = document;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    private String getCompareKey() {
        return mdocument.getdocumentId() + "|" + mTitle + "|" + mText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoteInfo that = (NoteInfo) o;

        return getCompareKey().equals(that.getCompareKey());
    }

    @Override
    public int hashCode() {
        return getCompareKey().hashCode();
    }

    @Override
    public String toString() {
        return getCompareKey();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mdocument, 0);
        dest.writeString(mTitle);
        dest.writeString(mText);
    }

    public final static Creator<NoteInfo> CREATOR =
            new Creator<NoteInfo>() {

                @Override
                public NoteInfo createFromParcel(Parcel source) {
                    return new NoteInfo(source);
                }

                @Override
                public NoteInfo[] newArray(int size) {
                    return new NoteInfo[size];
                }
            };
}
