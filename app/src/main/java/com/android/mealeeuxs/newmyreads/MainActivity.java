package com.android.mealeeuxs.newmyreads;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.mealeeuxs.newmyreads.Adapters.BookListAdapter;
import com.android.mealeeuxs.newmyreads.Behaviour.APIBehaviour;
import com.android.mealeeuxs.newmyreads.Behaviour.ActivityBehaviour;
import com.android.mealeeuxs.newmyreads.Behaviour.SQLBehaviour;
import com.android.mealeeuxs.newmyreads.Database.Book;
import com.android.mealeeuxs.newmyreads.Tools.ActivityTools;
import com.android.mealeeuxs.newmyreads.Tools.RecyclerViewHost;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewHost, View.OnClickListener {

    public static final String EXTRA_BOOK_LIST = "maeleeuxs.newmyreads.EXTRA.BOOK_LIST";
    public static final String EXTRA_BOOK_POSITION = "maeleeuxs.newmyreads.EXTRA.BOOK_POSITION";

    private static final String STATE_TOOLBAR_TITLE = "maeleeuxs.newmyreads.STATE.TOOLBAR_TITLE";
    private static final String STATE_CURRENT_FRAGMENT = "maeleeuxs.newmyreads.STATE_CURRENT_FRAGMENT";
    private static final String STATE_BOOK_LIST = "maeleeuxs.newmyreads.STATE_BOOK_LIST";

    private int mCurrentFragment = -1;
    private static final int FRAGMENT_MY_COLLECTION = 0;
    private static final int FRAGMENT_NOT_READ = 1;
    private static final int FRAGMENT_READING = 2;
    private static final int FRAGMENT_HAVE_READ = 3;
    private static final int FRAGMENT_FIND_BOOKS = 4;

    private int mPositionOfLastItemClicked;

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;

    private ActivityBehaviour mBehaviour;
    private BookListAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private EditText mBookTitleEditText;
    private EditText mBookAuthorEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView mNavigationView = findViewById(R.id.nav_view);
        mRecyclerView = findViewById(R.id.recycler_view);
        Button searchButton = findViewById(R.id.btn_search);
        mToolbar = findViewById(R.id.toolbar);
        mDrawer = findViewById(R.id.drawer);
        mAdapter = new BookListAdapter(this, this);
        mBookTitleEditText = findViewById(R.id.edit_search_title);
        mBookAuthorEditText = findViewById(R.id.edit_search_author);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        searchButton.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            loadFragment(FRAGMENT_MY_COLLECTION);
            mNavigationView.setCheckedItem(R.id.nav_collection);
        } else {
            mCurrentFragment = savedInstanceState.getInt(STATE_CURRENT_FRAGMENT);
            loadFragment(mCurrentFragment);
            List<Book> bookList = savedInstanceState.getParcelableArrayList(STATE_BOOK_LIST);
            mAdapter.setItems(bookList);
        }

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequence(STATE_TOOLBAR_TITLE, mToolbar.getTitle());
        outState.putInt(STATE_CURRENT_FRAGMENT, mCurrentFragment);
        outState.putParcelableArrayList(STATE_BOOK_LIST, (ArrayList<Book>) mAdapter.getItems());
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START))
            mDrawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_collection:
                loadFragment(FRAGMENT_MY_COLLECTION);
                break;
            case R.id.nav_not_read:
                loadFragment(FRAGMENT_NOT_READ);
                break;
            case R.id.nav_reading:
                loadFragment(FRAGMENT_READING);
                break;
            case R.id.nav_have_read:
                loadFragment(FRAGMENT_HAVE_READ);
                break;
            case R.id.nav_find_books:
                loadFragment(FRAGMENT_FIND_BOOKS);
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRecyclerItemClick(View view, int position) {
        mPositionOfLastItemClicked = position;
        ActivityTools.hideKeyboard(this);
        launchBookActivity();
    }

    @Override
    public void onRecyclerItemLongClick(View view, int position) {
        mPositionOfLastItemClicked = position;
        ActivityTools.hideKeyboard(this);
        mBehaviour.handleItemMenu(view);
    }

    @Override
    public void onClick(View v) {
        ActivityTools.hideKeyboard(this);
        loadRecyclerItems();
    }

    // ==============
    // HELPER METHODS
    // ==============

    public void launchBookActivity() {
        Intent showBookIntent = new Intent(this, BookActivity.class);
        showBookIntent.putExtra(EXTRA_BOOK_POSITION, mPositionOfLastItemClicked);
        showBookIntent.putParcelableArrayListExtra(EXTRA_BOOK_LIST, (ArrayList<Book>) mAdapter.getItems());

        startActivity(showBookIntent);
    }

    private void loadFragment(int newFragment) {
        switch (newFragment) {
            case FRAGMENT_MY_COLLECTION:
                mToolbar.setTitle(R.string.title_my_collection);
                mBehaviour = new SQLBehaviour(this, Book.TYPE_NONE);
                break;
            case FRAGMENT_NOT_READ:
                mToolbar.setTitle(R.string.title_not_read);
                mBehaviour = new SQLBehaviour(this, Book.TYPE_NOT_READ);
                break;
            case FRAGMENT_READING:
                mToolbar.setTitle(R.string.title_reading);
                mBehaviour = new SQLBehaviour(this, Book.TYPE_READING);
                break;
            case FRAGMENT_HAVE_READ:
                mToolbar.setTitle(R.string.title_have_read);
                mBehaviour = new SQLBehaviour(this, Book.TYPE_HAVE_READ);
                break;
            case FRAGMENT_FIND_BOOKS:
                mToolbar.setTitle(R.string.title_find_books);
                mBehaviour = new APIBehaviour(this);
                break;
        }

        if (mCurrentFragment == newFragment) return;
        else mCurrentFragment = newFragment;

        resetViews();
        loadRecyclerItems();
    }

    private void resetViews() {
        mBookTitleEditText.setText("");
        mBookTitleEditText.clearFocus();
        mBookAuthorEditText.setText("");
        mBookAuthorEditText.clearFocus();

        mAdapter = new BookListAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadRecyclerItems() {
        String title = "";
        String author = "";

        Editable bookTitleText = mBookTitleEditText.getText();
        Editable bookAuthorText = mBookAuthorEditText.getText();

        if (bookTitleText != null)
            title = bookTitleText.toString();

        if (bookAuthorText != null)
            author = bookAuthorText.toString();

        mBehaviour.loadRecyclerItems(mAdapter, title, author);
    }

    // ===================
    // GETTERS AND SETTERS
    // ===================

    public Book getLastItemClicked() {
        return mAdapter.getItems().get(mPositionOfLastItemClicked);
    }
}

