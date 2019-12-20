package com.android.mealeeuxs.newmyreads.API;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VolumeInfo
{
    @SerializedName("title")
    private String title = "";
    @SerializedName("authors")
    private List<String> authors = new ArrayList<>();
    @SerializedName("publisher")
    private String publisher = "";
    @SerializedName("publishedDate")
    private String publishedDate = "";
    @SerializedName("description")
    private String description = "";
    @SerializedName("imageLinks")
    private ImageLinks imageLinks = new ImageLinks();
    @SerializedName("infoLink")
    private String infoLink = "";

    public String getTitle()
    {
        return title;
    }

    public List<String> getAuthors()
    {
        return authors;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public String getPublishedDate()
    {
        return publishedDate;
    }

    public String getDescription()
    {
        return description;
    }

    public ImageLinks getImageLinks()
    {
        return imageLinks;
    }

    public String getInfoLink()
    {
        return infoLink;
    }
}
