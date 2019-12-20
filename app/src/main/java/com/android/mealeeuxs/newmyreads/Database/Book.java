package com.android.mealeeuxs.newmyreads.Database;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "book_table")
public class Book implements Parcelable {
    public static final int TYPE_NONE = 0;
    public static final int TYPE_NOT_READ = 1;
    public static final int TYPE_READING = 2;
    public static final int TYPE_HAVE_READ = 3;

    public static final String KEY_BOOK = "maeleeuxs.newmyreads.EXTRA.BOOK";

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String mId;
    @ColumnInfo(name = "title")
    private String mTitle;
    @ColumnInfo(name = "authors")
    private List<String> mAuthors;
    @ColumnInfo(name = "publisher")
    private String mPublisher;
    @ColumnInfo(name = "publish_date")
    private String mPublishedDate;
    @ColumnInfo(name = "description")
    private String mDescription;
    @ColumnInfo(name = "small_thumbnail")
    private String mSmallThumbnail;
    @ColumnInfo(name = "thumbnail")
    private String mThumbnail;
    @ColumnInfo(name = "info_link")
    private String mInfoLink;
    @ColumnInfo(name = "type")
    private int mType;

    private Book(Parcel input) {
        mAuthors = new ArrayList<>();

        mId = Objects.requireNonNull(input.readString());
        mTitle = input.readString();
        input.readStringList(mAuthors);
        mPublisher = input.readString();
        mPublishedDate = input.readString();
        mDescription = input.readString();
        mSmallThumbnail = input.readString();
        mThumbnail = input.readString();
        mInfoLink = input.readString();
        mType = input.readInt();
    }

    public Book(@NonNull String id, int type, String title, List<String> authors, String publisher,
                String publishedDate, String description, String smallThumbnail, String thumbnail,
                String infoLink) {
        mId = id;
        mType = type;
        mTitle = title;
        mAuthors = authors;
        mPublisher = publisher;
        mPublishedDate = publishedDate;
        mDescription = description;
        mSmallThumbnail = smallThumbnail;
        mThumbnail = thumbnail;
        mInfoLink = infoLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel target, int flags) {
        target.writeString(mId);
        target.writeString(mTitle);
        target.writeStringList(mAuthors);
        target.writeString(mPublisher);
        target.writeString(mPublishedDate);
        target.writeString(mDescription);
        target.writeString(mSmallThumbnail);
        target.writeString(mThumbnail);
        target.writeString(mInfoLink);
        target.writeInt(mType);
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {

        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public List<String> getAuthors() {
        return mAuthors;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getSmallThumbnail() {
        return mSmallThumbnail;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public String getInfoLink() {
        return mInfoLink;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }
}
