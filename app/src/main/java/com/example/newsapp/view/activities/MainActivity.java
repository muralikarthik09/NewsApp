package com.example.newsapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.newsapp.R;
import com.example.newsapp.data.NewsDataBase;
import com.example.newsapp.data.NewsItem;
import com.example.newsapp.model.ApiServices;
import com.example.newsapp.model.Article;
import com.example.newsapp.model.NetworkApi;
import com.example.newsapp.model.ResponseModel;
import com.example.newsapp.utils.AppExecutors;
import com.example.newsapp.view.adapters.NewsAdapter;
import com.example.newsapp.viewmodel.NewsViewModel;
import com.example.newsapp.utils.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.newsapp.constants.AppConstants.API_KEY;
import static com.example.newsapp.utils.CommonUtils.convertObjectIntoString;
import static com.example.newsapp.utils.CommonUtils.isNetworkAvailable;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    private RecyclerView mRecyclerView;
    private NewsViewModel newsViewModel;
    private NewsDataBase newsDataBase;
    private List<Article> articleList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rec_view);
        //getNews();
        newsDataBase = NewsDataBase.getInstance(getApplicationContext());
        if (isNetworkAvailable(this)) {
            getNewsLiveData();
        } else {
            getOfflineData();
        }
    }

    private void getOfflineData() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<NewsItem> newsItemList = newsDataBase.newsDao().getNewsItems();
                for (NewsItem newsItem : newsItemList) {
                    Article article = new Article(newsItem);
                    articleList.add(article);
                }
                setDataToAdapter(articleList);

            }
        });
    }

    private void insertNewsItems(List<Article> articleList) {
        List<NewsItem> newsItems = new ArrayList<>();
        for (Article article : articleList) {
            NewsItem item = new NewsItem(articleList.get(0));
            newsItems.add(item);
        }

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                newsDataBase.newsDao().insertNewsItems(newsItems);

            }
        });
    }

    private void getNewsLiveData() {
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        newsViewModel.init();
        newsViewModel.getNews();
        newsViewModel.getArticlesLiveData().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                if (articles != null && articles.size() > 0) {
                    insertNewsItems(articles);
                    setDataToAdapter(articles);

                }

            }
        });
    }

    private void getNews() {
        ApiServices apiService = NetworkApi.getClient().create(ApiServices.class);
        Call<ResponseModel> call = apiService.getLatestNews("techcrunch", API_KEY);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body().getStatus().equals("ok")) {
                    List<Article> articleList = response.body().getArticles();
                    if (articleList.size() > 0) {
                        setDataToAdapter(articleList);

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("out", t.toString());

            }
        });
    }

    private void setDataToAdapter(List<Article> articleList) {
        NewsAdapter adapter = new NewsAdapter(MainActivity.this, articleList, MainActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void OnItemClickListener(Article article) {
        String string = convertObjectIntoString(article);
        Intent intent = new Intent(this, NewsDetailsActivity.class);
        intent.putExtra("myjson", string);
        startActivity(intent);


    }
}