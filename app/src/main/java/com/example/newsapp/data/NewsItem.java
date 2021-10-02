package com.example.newsapp.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.newsapp.model.Article;


@Entity(tableName = "newsitems")
public class NewsItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String urlToImage;
    private String source;
    private String publishedAt;

    public NewsItem() {

    }

    @Ignore
    public NewsItem(String title, String description, String urlToImage, String source, String publishedAt) {
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
        this.source = source;
        this.publishedAt = publishedAt;
    }

    @Ignore
    public NewsItem(Article article) {
        this.title = article.getTitle();
        this.description = article.getDescription();
        this.urlToImage = article.getUrlToImage();
        this.source = article.getSource().getName();
        this.publishedAt = article.getPublishedAt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }


}
