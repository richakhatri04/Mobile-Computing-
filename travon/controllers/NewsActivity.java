package com.thealienobserver.nikhil.travon.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.thealienobserver.nikhil.travon.R;
import com.thealienobserver.nikhil.travon.adapters.NewsCardsAdapter;
import com.thealienobserver.nikhil.travon.apihandlers.NewsHandler;
import com.thealienobserver.nikhil.travon.models.NewsArticle;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {
    private static final String TAG = "NewsScreen";

    public static final String COUNTRY_PARAM = "COUNTRY_CODE_PARAM";
    public static final String CITY_PARAM = "CITY_PARAM";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //fetching News
        String countryParam = getIntent().getStringExtra(NewsActivity.COUNTRY_PARAM);
        String cityParam = getIntent().getStringExtra(NewsActivity.CITY_PARAM);

        setTitle(cityParam + " News");
        NewsHandler newsHandler = new NewsHandler(this) {
            @Override
            public void postFetchingNewsArticles(ArrayList<NewsArticle> newsArticles) {
                NewsActivity.this.setupNewsCards(newsArticles);
            }
        };
        newsHandler.getNewsArticles(cityParam, countryParam);
    }

    private void setupNewsCards(ArrayList<NewsArticle> newsArticles) {
        RecyclerView newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setAdapter(new NewsCardsAdapter(this, newsArticles));
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
