package com.example.newsapp.view.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.model.Article;
import com.example.newsapp.utils.OnItemClickListener;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    Context mContext;
    List<Article> mArticleList;
    OnItemClickListener mOnItemClickListener;

    public NewsAdapter(Context context, List<Article> articleList, OnItemClickListener onItemClickListener) {
        mContext = context;
        mArticleList = articleList;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.news_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article articleModel = mArticleList.get(position);
        if (!TextUtils.isEmpty(articleModel.getTitle())) {
            holder.newHeader.setText(articleModel.getTitle());
        }
        if (articleModel.getSource() != null && !TextUtils.isEmpty(articleModel.getSource().getName())) {
            holder.newsSource.setText(articleModel.getSource().getName());
        }
        if (!TextUtils.isEmpty(articleModel.getDescription())) {
            holder.newDate.setText(articleModel.getPublishedAt());
        }

        Glide.with(mContext).load(articleModel.getUrlToImage()).into(holder.newsImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.OnItemClickListener(articleModel);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView newsImage;
        public TextView newHeader, newDate, newsSource;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            newsImage = itemView.findViewById(R.id.news_iv);
            newHeader = itemView.findViewById(R.id.new_head_tv);
            newsSource = itemView.findViewById(R.id.new_source_tv);
            newDate = itemView.findViewById(R.id.date_tv);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

}
