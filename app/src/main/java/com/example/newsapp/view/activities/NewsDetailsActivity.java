package com.example.newsapp.view.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.model.Article;

import static com.example.newsapp.utils.CommonUtils.convertStringIntoArtical;

public class NewsDetailsActivity extends AppCompatActivity {
    ImageView newsImage;
    TextView newHeader, newDate, newsSource, des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        newsImage = findViewById(R.id.news_iv);
        newHeader = findViewById(R.id.new_head_tv);
        newsSource = findViewById(R.id.new_source_tv);
        newDate = findViewById(R.id.date_tv);
        des = findViewById(R.id.desc);
        Article article = convertStringIntoArtical(getIntent().getStringExtra("myjson"));

        if (!TextUtils.isEmpty(article.getTitle())) {
            newHeader.setText(article.getTitle());
        }
        if (!TextUtils.isEmpty(article.getDescription())) {
            des.setText(article.getDescription());
        }
        if (article.getSource() != null && !TextUtils.isEmpty(article.getSource().getName())) {
            newsSource.setText(article.getSource().getName());
        }
        if (!TextUtils.isEmpty(article.getDescription())) {
            newDate.setText(article.getPublishedAt());
        }
        String url = article.getUrlToImage();
        Glide.with(this).load(url).into(newsImage);
    }
}
