package com.example.newsapp.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.newsapp.model.ApiServices;
import com.example.newsapp.model.Article;
import com.example.newsapp.model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.newsapp.constants.AppConstants.API_KEY;

public class NewsRepository {

    public static final String BASE_URL = "https://newsapi.org/v2/";
    private static Retrofit retrofit = null;
    private MutableLiveData<List<Article>> articleMutableLiveData;
    private ApiServices apiServices;


    public NewsRepository() {
        apiServices = getClient().create(ApiServices.class);
        articleMutableLiveData = new MutableLiveData<>();
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public void getNews() {
        Call<ResponseModel> call = apiServices.getLatestNews("techcrunch", API_KEY);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body().getStatus().equals("ok")) {
                    List<Article> articleList = response.body().getArticles();
                    if (articleList.size() > 0) {
                        articleMutableLiveData.postValue(articleList);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("out", t.toString());
                articleMutableLiveData.postValue(null);

            }
        });
    }

    public MutableLiveData<List<Article>> getArticles() {
        return articleMutableLiveData;
    }
}
