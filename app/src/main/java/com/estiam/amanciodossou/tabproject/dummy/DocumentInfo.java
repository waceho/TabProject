package com.estiam.amanciodossou.tabproject.dummy;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim.
 */

public final class DocumentInfo implements Parcelable {
    private final String mdocumentId;
    private final String mTitle;
    private final List<ModuleInfo> mModules;

    public DocumentInfo(String documentId, String title, List<ModuleInfo> modules) {
        mdocumentId = documentId;
        mTitle = title;
        mModules = modules;
    }

    private DocumentInfo(Parcel source) {
        mdocumentId = source.readString();
        mTitle = source.readString();
        mModules = new ArrayList<>();
        source.readTypedList(mModules, ModuleInfo.CREATOR);
    }

    public String getdocumentId() {
        return mdocumentId;
    }

    public String getTitle() {
        return mTitle;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocumentInfo that = (DocumentInfo) o;

        return mdocumentId.equals(that.mdocumentId);

    }

    @Override
    public int hashCode() {
        return mdocumentId.hashCode();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mdocumentId);
        dest.writeString(mTitle);
        dest.writeTypedList(mModules);
    }

    public static final Creator<DocumentInfo> CREATOR =
            new Creator<DocumentInfo>() {

                @Override
                public DocumentInfo createFromParcel(Parcel source) {
                    return new DocumentInfo(source);
                }

                @Override
                public DocumentInfo[] newArray(int size) {
                    return new DocumentInfo[size];
                }
            };

}
