package com.android.mealeeuxs.newmyreads.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mealeeuxs.newmyreads.R;
import com.android.mealeeuxs.newmyreads.Tools.RecyclerViewHost;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private final RecyclerViewHost mHost;

    final ImageView iconImageView;
    final TextView titleTextView;
    final TextView authorsTextView;

    BookViewHolder(@NonNull View itemView, RecyclerViewHost host) {
        super(itemView);
        mHost = host;
        iconImageView = itemView.findViewById(R.id.item_book_icon);
        titleTextView = itemView.findViewById(R.id.item_book_title);
        authorsTextView = itemView.findViewById(R.id.item_book_authors);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mHost.onRecyclerItemClick(itemView, getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View view) {
        mHost.onRecyclerItemLongClick(itemView, getAdapterPosition());
        return true;
    }
}
