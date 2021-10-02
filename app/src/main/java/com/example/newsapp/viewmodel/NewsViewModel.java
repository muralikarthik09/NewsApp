package com.example.newsapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.newsapp.model.Article;
import com.example.newsapp.model.NewsRepository;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    private NewsRepository newsRepository;
    private MutableLiveData<List<Article>> articleMutableLiveData;
    public NewsViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        newsRepository = new NewsRepository();
        articleMutableLiveData = newsRepository.getArticles();
    }

    public MutableLiveData<List<Article>> getArticlesLiveData() {
        return articleMutableLiveData;
    }

    public void getNews() {
        newsRepository.getNews();
    }
}
