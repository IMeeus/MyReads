package com.android.mealeeuxs.newmyreads.Behaviour;

import android.content.DialogInterface;
import android.view.MenuItem;
import android.view.View;

import com.android.mealeeuxs.newmyreads.Adapters.BookListAdapter;
import com.android.mealeeuxs.newmyreads.Database.Book;
import com.android.mealeeuxs.newmyreads.Database.BookViewModel;
import com.android.mealeeuxs.newmyreads.MainActivity;
import com.android.mealeeuxs.newmyreads.R;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class SQLBehaviour extends ActivityBehaviour implements PopupMenu.OnMenuItemClickListener {
    private BookViewModel mViewModel;
    private int mBookType;

    public SQLBehaviour(MainActivity root, int bookType) {
        super(root);

        mBookType = bookType;
        mViewModel =  ViewModelProviders.of(mRoot).get(BookViewModel.class);
    }

    @Override
    public void loadRecyclerItems(final BookListAdapter adapter, String title, String author) {
        mViewModel.getBooks(mBookType, title, author).observe(mRoot, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> bookList) {
                adapter.setItems(bookList);
            }
        });
    }

    @Override
    public void handleItemMenu(View view) {
        PopupMenu popup = new PopupMenu(mRoot, view);
        popup.getMenuInflater().inflate(R.menu.menu_sql_book, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        MenuPopupHelper popupHelper = new MenuPopupHelper(mRoot, (MenuBuilder) popup.getMenu(), view);
        popupHelper.setForceShowIcon(true);
        popupHelper.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_preview:
                mRoot.launchBookActivity();
                return true;
            case R.id.action_change_state:
                handleSelectStateDialog();
                return true;
            case R.id.action_remove:
                handleDeleteBookDialog();
                return true;
        }

        return false;
    }

    private void handleSelectStateDialog() {
        final Book selectedBook = mRoot.getLastItemClicked();
        final int bookType = selectedBook.getType();

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mRoot);
        alertBuilder.setTitle("Assign to?");
        alertBuilder.setSingleChoiceItems(R.array.book_states, bookType - 1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedItem) {
                        selectedBook.setType(selectedItem + 1);
                        mViewModel.update(selectedBook);
                    }
                });

        alertBuilder.setPositiveButton("Confirm", null);
        alertBuilder.show();
    }

    private void handleDeleteBookDialog() {
        final Book selectedBook = mRoot.getLastItemClicked();

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mRoot);
        alertBuilder.setTitle("Delete Book?");
        alertBuilder.setMessage("Are you sure you want to delete this book from your collection? " +
                "All data about this book will be lost.");
        alertBuilder.setPositiveButton("Accept",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mViewModel.delete(selectedBook);
                    }
                });
        alertBuilder.setNegativeButton("Cancel", null);
        alertBuilder.show();
    }
}
