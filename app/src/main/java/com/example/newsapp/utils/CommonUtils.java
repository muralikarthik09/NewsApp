package com.example.newsapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import com.example.newsapp.model.Article;
import com.google.gson.Gson;

public class CommonUtils {
    public static String convertObjectIntoString(Article article) {
        Gson gson = new Gson();
        String myJson = gson.toJson(article);
        return myJson;
    }

    public static Article convertStringIntoArtical(String string) {
        Gson gson = new Gson();
        Article article = gson.fromJson(string, Article.class);
        return article;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
