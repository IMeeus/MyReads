package com.android.mealeeuxs.newmyreads;

import android.content.Intent;
import android.os.Bundle;

import com.android.mealeeuxs.newmyreads.Adapters.BookListAdapter;
import com.android.mealeeuxs.newmyreads.Adapters.BookPagerAdapter;
import com.android.mealeeuxs.newmyreads.Database.Book;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        int initialPosition = 0;
        List<Book> bookList = null;
        Intent launchIntent = getIntent();

        if (launchIntent != null) {
            initialPosition = launchIntent.getIntExtra(MainActivity.EXTRA_BOOK_POSITION, 0);
            bookList = launchIntent.getParcelableArrayListExtra(MainActivity.EXTRA_BOOK_LIST);
        }

        ViewPager viewPager = findViewById(R.id.book_pager);
        viewPager.setAdapter(new BookPagerAdapter(getSupportFragmentManager(), bookList));
        viewPager.setCurrentItem(initialPosition);
    }
}