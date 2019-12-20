package com.android.mealeeuxs.newmyreads.Database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;

@Database(entities = {Book.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class BookDatabase extends RoomDatabase
{
    public abstract BookDao bookDao();

    private static BookDatabase INSTANCE;

    public static BookDatabase getDatabase(final Context context)
    {
        if (INSTANCE == null) {
            synchronized (BookDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        BookDatabase.class, "book_database")
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return INSTANCE;
    }
}
