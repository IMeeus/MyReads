package com.android.mealeeuxs.newmyreads.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mealeeuxs.newmyreads.Database.Book;
import com.android.mealeeuxs.newmyreads.R;
import com.android.mealeeuxs.newmyreads.Tools.RecyclerViewHost;

import java.util.ArrayList;
import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookViewHolder>
{
    private final Context mContext;
    private final RecyclerViewHost mHost;
    private final LayoutInflater mInflater;

    private List<Book> mBookList;

    public BookListAdapter(Context context, RecyclerViewHost host)
    {
        mContext = context;
        mHost = host;
        mInflater = LayoutInflater.from(context);
        mBookList = new ArrayList<>();
    }

    // ================
    // OVERRIDE METHODS
    // ================

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type)
    {
        View itemView = mInflater.inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(itemView, mHost);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder viewHolder, int position)
    {
        Book currentBook = mBookList.get(position);

        switch (currentBook.getType()) {
            case Book.TYPE_NONE:
                viewHolder.iconImageView.setImageDrawable(mContext.getDrawable(R.drawable.ic_book));
                break;
            case Book.TYPE_NOT_READ:
                viewHolder.iconImageView.setImageDrawable(mContext.getDrawable(R.drawable.ic_close));
                break;
            case Book.TYPE_READING:
                viewHolder.iconImageView.setImageDrawable(mContext.getDrawable(R.drawable.ic_progress));
                break;
            case Book.TYPE_HAVE_READ:
                viewHolder.iconImageView.setImageDrawable(mContext.getDrawable(R.drawable.ic_check));
                break;
        }

        viewHolder.titleTextView.setText(currentBook.getTitle());

        List<String> authors = currentBook.getAuthors();
        if (authors != null) {
            StringBuilder authorText = new StringBuilder();
            for (int i = 0; i < authors.size(); i++) {
                if (i != 0) authorText.append(", ");
                authorText.append(authors.get(i));
            }
            viewHolder.authorsTextView.setText(authorText);
        }
    }

    @Override
    public int getItemCount()
    {
        return mBookList.size();
    }

    // =====================
    // PUBLIC ACCESS METHODS
    // =====================

    public List<Book> getItems() {
        return mBookList;
    }

    public void setItems(List<Book> books)
    {
        mBookList = books;
        notifyDataSetChanged();
    }
}
