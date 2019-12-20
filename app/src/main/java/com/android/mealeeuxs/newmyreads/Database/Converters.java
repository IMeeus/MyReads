package com.android.mealeeuxs.newmyreads.Database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

class Converters
{
    @TypeConverter
    public static List<String> JsonToList(String json)
    {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(json, listType);
    }

    @TypeConverter
    public static String ListToJson(List<String> list)
    {
        return new Gson().toJson(list);
    }
}
