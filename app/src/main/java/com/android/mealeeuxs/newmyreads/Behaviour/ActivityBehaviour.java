package com.android.mealeeuxs.newmyreads.Behaviour;

import android.view.View;

import com.android.mealeeuxs.newmyreads.Adapters.BookListAdapter;
import com.android.mealeeuxs.newmyreads.MainActivity;

public abstract class ActivityBehaviour {
    MainActivity mRoot;

    ActivityBehaviour(MainActivity root) {
        mRoot = root;
    }

    public abstract void loadRecyclerItems(BookListAdapter adapter, String title, String author);
    public abstract void handleItemMenu(View view);
}