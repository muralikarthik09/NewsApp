package com.example.newsapp.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("top-headlines")
    public Call<ResponseModel> getLatestNews(@Query("sources") String source, @Query("apiKey") String apiKey);

}
