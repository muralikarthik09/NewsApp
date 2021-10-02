package com.example.newsapp.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface NewsDao {

    @Query("Select * from newsitems")
    List<NewsItem> getNewsItems();

    @Insert
    void insertNewsItems(List<NewsItem> newsItem);
  @Insert
    void insertNewsItems(NewsItem newsItem);

    @Update
    void updateNewsItems(List<NewsItem> newsItem);

    @Delete
    void deleteNewsItems(List<NewsItem> newsItems);
}
