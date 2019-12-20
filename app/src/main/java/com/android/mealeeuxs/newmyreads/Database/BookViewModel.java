package com.android.mealeeuxs.newmyreads.Database;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

public class BookViewModel extends AndroidViewModel
{
    private BookRepository mRepository;

    public BookViewModel(@NonNull Application application)
    {
        super(application);
        mRepository = new BookRepository(application);
    }

    public LiveData<List<Book>> getBooks(int type, String title, String author)
    {
        if (type == Book.TYPE_NONE)
            return mRepository.getBooks(title, author);
        else
            return mRepository.getBooksByState(type, title, author);
    }

    public void insert(Book book)
    {
        mRepository.insert(book);
    }

    public void update(Book book)
    {
        mRepository.update(book);
    }

    public void delete(Book book) {
        mRepository.delete(book);
    }
}
