package com.estiam.amanciodossou.tabproject.dummy;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim.
 */

public final class DrawedInfo implements Parcelable {
    private final String mimageId;
    private final byte[] imageInfo;
    private final List<ModuleInfo> mModules;

    public DrawedInfo(String documentId, byte[] image, List<ModuleInfo> modules) {
        mimageId = documentId;
        imageInfo = image;
        mModules = modules;
    }

    private DrawedInfo(Parcel source) {
        mimageId = source.readString();
        imageInfo = source.createByteArray();
        mModules = new ArrayList<>();
        source.readTypedList(mModules, ModuleInfo.CREATOR);
    }

    public String getimageId() {
        return mimageId;
    }

    public byte[] getTitle() {
        return imageInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DrawedInfo that = (DrawedInfo) o;

        return mimageId.equals(that.mimageId);

    }

    @Override
    public int hashCode() {
        return mimageId.hashCode();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mimageId);
        dest.writeByteArray(imageInfo);
        dest.writeTypedList(mModules);
    }

    public static final Creator<DrawedInfo> CREATOR =
            new Creator<DrawedInfo>() {

                @Override
                public DrawedInfo createFromParcel(Parcel source) {
                    return new DrawedInfo(source);
                }

                @Override
                public DrawedInfo[] newArray(int size) {
                    return new DrawedInfo[size];
                }
            };

}
