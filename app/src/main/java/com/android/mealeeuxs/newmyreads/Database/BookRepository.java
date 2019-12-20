package com.android.mealeeuxs.newmyreads.Database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class BookRepository
{
    private BookDao mBookDao;

    public BookRepository(Application application)
    {
        BookDatabase database = BookDatabase.getDatabase(application);
        mBookDao = database.bookDao();
    }

    public LiveData<List<Book>> getBooks(String title, String author)
    {
        LiveData<List<Book>> livedata;

        if (title.isEmpty() && author.isEmpty())
            livedata = mBookDao.getAllBooks();
        else if (!title.isEmpty() && author.isEmpty())
            livedata = mBookDao.getBooksByTitle(title);
        else if (!author.isEmpty() && title.isEmpty())
            livedata = mBookDao.getBooksByAuthor(author);
        else
            livedata = mBookDao.getBooksByTitleAndAuthor(title, author);

        return livedata;
    }

    public LiveData<List<Book>> getBooksByState(int type, String title, String author)
    {
        LiveData<List<Book>> livedata;

        if (title.isEmpty() && author.isEmpty())
            livedata = mBookDao.getBooksByState(type);
        else if (!title.isEmpty() && author.isEmpty())
            livedata = mBookDao.getBooksByStateAndTitle(type, title);
        else if (!author.isEmpty() && title.isEmpty())
            livedata = mBookDao.getBooksByStateAndAuthor(type, author);
        else
            livedata = mBookDao.getBooksByStateAndTitleAndAuthor(type, title, author);

        return livedata;
    }

    public void insert(Book book)
    {
        new insertBookAsyncTask(mBookDao).execute(book);
    }

    private static class insertBookAsyncTask extends AsyncTask<Book, Void, Void>
    {
        private BookDao mDao;

        insertBookAsyncTask(BookDao dao)
        {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Book... books)
        {
            mDao.insert(books[0]);
            return null;
        }
    }

    public void update(Book book) {
        new updateBookAsyncTask(mBookDao).execute(book);
    }

    private static class updateBookAsyncTask extends AsyncTask<Book, Void, Void> {
        private BookDao mDao;

        updateBookAsyncTask(BookDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Book... books)
        {
            mDao.update(books[0]);
            return null;
        }
    }

    public void delete(Book book) {
        new deleteBookAsyncTask(mBookDao).execute(book);
    }

    private static class deleteBookAsyncTask extends AsyncTask<Book, Void, Void> {
        private BookDao mDao;

        deleteBookAsyncTask(BookDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Book... books)
        {
            mDao.delete(books[0]);
            return null;
        }
    }
}
