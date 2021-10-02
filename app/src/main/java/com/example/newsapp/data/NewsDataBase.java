package com.example.newsapp.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import static com.example.newsapp.utils.AppExecutors.LOCK;


@Database(entities = {NewsItem.class}, version = 1, exportSchema = false)
public abstract class NewsDataBase extends RoomDatabase {
    private static final String DB_NAME = "news_db";
    private static NewsDataBase instance;

    public static synchronized NewsDataBase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        NewsDataBase.class, DB_NAME).
                        fallbackToDestructiveMigration()
                        .build();
            }
        }
        return instance;
    }


    public abstract NewsDao newsDao();

}
