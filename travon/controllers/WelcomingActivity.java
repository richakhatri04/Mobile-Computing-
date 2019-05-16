package com.thealienobserver.nikhil.travon.controllers;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.TextView;

import com.thealienobserver.nikhil.travon.R;
import com.thealienobserver.nikhil.travon.adapters.CategoryScoresAdapter;
import com.thealienobserver.nikhil.travon.apihandlers.WelcomingHandler;
import com.thealienobserver.nikhil.travon.models.CategoryScore;

import java.util.ArrayList;


public class WelcomingActivity extends AppCompatActivity {

    public static final String CITY = "CITY";

    private String cityName;
    private TextView welcomingTitleTv;
    private TextView descriptionTv;
    private ListView categoryItems;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcoming);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));
        getSupportActionBar().setElevation(0);

        cityName = getIntent().getStringExtra(CITY);


        welcomingTitleTv = findViewById(R.id.welcomingTitleTV);
        descriptionTv = findViewById(R.id.descriptionTv);

        welcomingTitleTv.setText("Welcome to " + cityName + "!");
        WelcomingHandler welcomingHandler = new WelcomingHandler(this, descriptionTv) {
            @Override
            public void postFetchingCategoriesScores(ArrayList<CategoryScore> categoryScores) {
                WelcomingActivity.this.setupCategoryScores(categoryScores);
            }
        };
        welcomingHandler.getCityScores(cityName);
    }

    private void setupCategoryScores(ArrayList<CategoryScore> categoryScores) {
        RecyclerView categoriesRecyclerView = findViewById(R.id.welcomingRecyclerView);
        categoriesRecyclerView.setAdapter(new CategoryScoresAdapter(this, categoryScores));
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
