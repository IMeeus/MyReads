package com.android.mealeeuxs.newmyreads.Tools;

import android.view.View;

public interface RecyclerViewHost
{
    public void onRecyclerItemClick(View view, int position);
    public void onRecyclerItemLongClick(View view, int position);
}
