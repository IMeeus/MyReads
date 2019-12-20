package com.android.mealeeuxs.newmyreads.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.android.mealeeuxs.newmyreads.BookFragment;
import com.android.mealeeuxs.newmyreads.Database.Book;

import java.util.List;

public class BookPagerAdapter extends FragmentStatePagerAdapter
{
    private List<Book> mBookList;

    public BookPagerAdapter(FragmentManager fm, List<Book> bookList)
    {
        super(fm);
        mBookList = bookList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        Book currentBook = mBookList.get(position);
        return BookFragment.getInstance(currentBook);
    }

    @Override
    public int getCount()
    {
        return mBookList.size();
    }
}