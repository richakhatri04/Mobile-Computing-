package com.thealienobserver.nikhil.travon.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thealienobserver.nikhil.travon.R;
import com.thealienobserver.nikhil.travon.models.NewsArticle;

import java.util.ArrayList;

public class NewsCardsAdapter extends RecyclerView.Adapter<NewsCardsAdapter.ViewHolder> {
    private static final String TAG = "NewsCardsAdapter";

    private Context callerContext;
    ArrayList<NewsArticle> newsArticles;

    public NewsCardsAdapter(Context context, ArrayList<NewsArticle> newsArticles) {
        this.callerContext = context;
        this.newsArticles = newsArticles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_news_items, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "bind view called");
        final NewsArticle currentArticle = this.newsArticles.get(i);
        viewHolder.newsDescription.setText(currentArticle.getDescription());
        viewHolder.newsHeadline.setText(currentArticle.getTitle());
        Glide.with(callerContext).load(currentArticle.getImageUrl()).into(viewHolder.newsImage);
        viewHolder.parentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentArticle.getArticleUrl()));
                callerContext.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.newsArticles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImage;
        TextView newsDescription, newsHeadline;
        CardView parentCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImage = itemView.findViewById(R.id.newsImage);
            newsDescription = itemView.findViewById(R.id.newsDescription);
            newsHeadline = itemView.findViewById(R.id.newsHeadline);
            parentCardView = itemView.findViewById(R.id.card_view);
        }
    }

}
