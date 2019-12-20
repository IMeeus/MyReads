package com.android.mealeeuxs.newmyreads.Behaviour;

import android.content.DialogInterface;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.mealeeuxs.newmyreads.API.ApiResponse;
import com.android.mealeeuxs.newmyreads.API.BooksApi;
import com.android.mealeeuxs.newmyreads.API.Volume;
import com.android.mealeeuxs.newmyreads.Adapters.BookListAdapter;
import com.android.mealeeuxs.newmyreads.Database.Book;
import com.android.mealeeuxs.newmyreads.Database.BookViewModel;
import com.android.mealeeuxs.newmyreads.MainActivity;
import com.android.mealeeuxs.newmyreads.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIBehaviour extends ActivityBehaviour implements PopupMenu.OnMenuItemClickListener {
    private BookViewModel mViewModel;
    private BooksApi mBooksApi;

    public APIBehaviour(MainActivity root) {
        super(root);

        mViewModel = ViewModelProviders.of(mRoot).get(BookViewModel.class);
        mBooksApi = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/books/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BooksApi.class);
    }

    @Override
    public void loadRecyclerItems(final BookListAdapter adapter, String title, String author) {
        if (title.isEmpty() && author.isEmpty()) {
            adapter.setItems(new ArrayList<Book>());
            return;
        }

        String url = "https://www.googleapis.com/books/v1/volumes" +
                "?key=AIzaSyAwZdYuT5flIS1pDUdEQXEMEKMEyk6L2w8" +
                "&printType=books" +
                "&projection=lite" +
                "&fields=items(id, volumeInfo(title,authors,publisher,publishedDate,description,imageLinks(smallThumbnail,thumbnail),infoLink))" +
                "&q=";

        if (!title.isEmpty()) url += "+intitle:" + title;
        if (!author.isEmpty()) url += "+inauthor:" + author;

        Call<ApiResponse> call = mBooksApi.getBooks(url);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (!response.isSuccessful()) return;

                List<Book> newBookList = new ArrayList<>();

                ApiResponse apiResponse = response.body();
                List<Volume> results = null;
                if (apiResponse != null)
                    results = apiResponse.getItems();
                if (results != null) {
                    for (Volume result : results) {
                        Book newBook = new Book(
                                result.getId(),
                                Book.TYPE_NONE,
                                result.getTitle(),
                                result.getAuthors(),
                                result.getPublisher(),
                                result.getPublishedDate(),
                                result.getDescription(),
                                result.getSmallThumbnail(),
                                result.getThumbnail(),
                                result.getInfoLink()
                        );
                        newBookList.add(newBook);
                    }
                }

                adapter.setItems(newBookList);
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Log.d("ERROR", t.getMessage());
            }
        });
    }

    @Override
    public void handleItemMenu(View view) {
        PopupMenu popup = new PopupMenu(mRoot, view);
        popup.getMenuInflater().inflate(R.menu.menu_api_book, popup.getMenu());
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
                break;
            case R.id.action_add_to_collection:
                handleSelectStateDialog();
                break;
        }

        return false;
    }

    private void handleSelectStateDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mRoot);
        alertBuilder.setTitle("Assign to?");

        final Book selectedBook = mRoot.getLastItemClicked();
        final int[] selectedType = new int[1];
        selectedType[0] = Book.TYPE_NOT_READ;

        alertBuilder.setSingleChoiceItems(R.array.book_states, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selection) {
                switch (selection) {
                    case 0:
                        selectedType[0] = Book.TYPE_NOT_READ;
                        break;
                    case 1:
                        selectedType[0] = Book.TYPE_READING;
                        break;
                    case 2:
                        selectedType[0] = Book.TYPE_HAVE_READ;
                        break;
                    default:
                        selectedType[0] = Book.TYPE_NONE;
                }
            }
        });

        alertBuilder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedBook.setType(selectedType[0]);
                        mViewModel.insert(selectedBook);
                    }
                });

        alertBuilder.setNegativeButton("Cancel", null);
        alertBuilder.show();
    }
}
