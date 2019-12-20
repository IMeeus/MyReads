package com.android.mealeeuxs.newmyreads.Tools;

import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;

public class ConverterTools
{
    public static Drawable UrlToDrawable(String url)
    {
        Drawable drawable = null;

        try {
            InputStream inputStream = (InputStream) new URL(url).getContent();
            drawable = Drawable.createFromStream(inputStream, "book_thumbnail");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return drawable;
    }
}
