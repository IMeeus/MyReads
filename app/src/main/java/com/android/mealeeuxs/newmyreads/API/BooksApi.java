package com.android.mealeeuxs.newmyreads.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface BooksApi
{
    @GET
    Call<ApiResponse> getBooks(@Url String url);
}
