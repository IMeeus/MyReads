package com.android.mealeeuxs.newmyreads.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Volume
{
    @SerializedName("id")
    private String id;

    @SerializedName("volumeInfo")
    private VolumeInfo volumeInfo = new VolumeInfo();

    public String getId()
    {
        return id;
    }

    public String getTitle()
    {
        return volumeInfo.getTitle();
    }

    public List<String> getAuthors()
    {
        return volumeInfo.getAuthors();
    }

    public String getPublisher()
    {
        return volumeInfo.getPublisher();
    }

    public String getPublishedDate()
    {
        return volumeInfo.getPublishedDate();
    }

    public String getDescription()
    {
        return volumeInfo.getDescription();
    }

    public String getSmallThumbnail()
    {
        return volumeInfo.getImageLinks().getSmallThumbnail();
    }

    public String getThumbnail()
    {
        return volumeInfo.getImageLinks().getThumbnail();
    }

    public String getInfoLink()
    {
        return volumeInfo.getInfoLink();
    }
}
