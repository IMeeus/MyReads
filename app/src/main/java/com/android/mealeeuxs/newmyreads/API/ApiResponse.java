package com.android.mealeeuxs.newmyreads.API;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ApiResponse
{
    @SerializedName("items")
    private List<Volume> items = new ArrayList<>();

    public List<Volume> getItems()
    {
        return items;
    }
}
