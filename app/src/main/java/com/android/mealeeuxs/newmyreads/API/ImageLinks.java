package com.android.mealeeuxs.newmyreads.API;

import com.google.gson.annotations.SerializedName;

public class ImageLinks
{
    @SerializedName("smallThumbnail")
    private String smallThumbnail = "";

    @SerializedName("thumbnail")
    private String thumbnail = "";

    public String getSmallThumbnail()
    {
        return smallThumbnail;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }
}
