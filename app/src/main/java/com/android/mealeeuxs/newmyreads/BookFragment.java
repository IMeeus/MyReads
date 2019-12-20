package com.android.mealeeuxs.newmyreads;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mealeeuxs.newmyreads.Database.Book;
import com.android.mealeeuxs.newmyreads.Tools.ConverterTools;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BookFragment extends Fragment {
    private Book mBook;

    private TextView mTitleTextView;
    private TextView mAuthorsTextView;
    private TextView mPublisherTextView;
    private TextView mPublishedDateTextView;
    private TextView mDescriptionTextView;
    private ImageView mThumbnailImageView;

    public static BookFragment getInstance(Book book) {
        BookFragment fragment = new BookFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(Book.KEY_BOOK, book);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(Book.KEY_BOOK))
            mBook = arguments.getParcelable(Book.KEY_BOOK);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book, container, false);

        mTitleTextView = rootView.findViewById(R.id.text_book_title);
        mAuthorsTextView = rootView.findViewById(R.id.text_book_authors);
        mPublisherTextView = rootView.findViewById(R.id.text_book_publisher);
        mPublishedDateTextView = rootView.findViewById(R.id.text_book_publish_date);
        mDescriptionTextView = rootView.findViewById(R.id.text_book_description);
        mThumbnailImageView = rootView.findViewById(R.id.img_book_thumbnail);

        LoadDataForViews();

        Button btnMoreInfo = rootView.findViewById(R.id.btn_more_info);

        btnMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openInfoLinkIntent = new Intent(Intent.ACTION_VIEW);
                openInfoLinkIntent.setData(Uri.parse(mBook.getInfoLink()));
                startActivity(openInfoLinkIntent);
            }
        });

        return rootView;
    }

    // ===============
    // PRIVATE METHODS
    // ===============

    private void LoadDataForViews() {
        if (mBook != null) {
            mTitleTextView.setText(mBook.getTitle());

            List<String> authors = mBook.getAuthors();
            if (authors != null) {
                StringBuilder authorsText = new StringBuilder();
                for (int i = 0; i < authors.size(); i++) {
                    if (i != 0) authorsText.append(", ");
                    authorsText.append(authors.get(i));
                }
                mAuthorsTextView.setText(authorsText);
            }

            mPublisherTextView.setText(mBook.getPublisher());
            mPublishedDateTextView.setText(mBook.getPublishedDate());
            mDescriptionTextView.setText(mBook.getDescription());

            new LoadImageAsyncTask(mThumbnailImageView, mBook.getThumbnail()).execute();
        }
    }

    // ===========
    // ASYNC TASKS
    // ===========

    private static class LoadImageAsyncTask extends AsyncTask<Void, Void, Drawable> {
        private WeakReference<ImageView> mImageView;
        private WeakReference<String> mImageUrl;

        LoadImageAsyncTask(ImageView imageView, String imageUrl) {
            mImageView = new WeakReference<>(imageView);
            mImageUrl = new WeakReference<>(imageUrl);
        }

        @Override
        protected Drawable doInBackground(Void... voids) {
            String imageUrl = mImageUrl.get();

            return ConverterTools.UrlToDrawable(imageUrl);
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            super.onPostExecute(drawable);

            ImageView imageView = mImageView.get();
            imageView.setImageDrawable(drawable);
        }
    }
}
