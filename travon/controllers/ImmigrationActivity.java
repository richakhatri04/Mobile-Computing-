package com.thealienobserver.nikhil.travon.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import com.thealienobserver.nikhil.travon.R;
import com.thealienobserver.nikhil.travon.apihandlers.ImmigrationHandler;

public class ImmigrationActivity extends AppCompatActivity {

    // Intent extra private parameters
    public static String CITY = "city";
    public String city;

    // Layout components
    private CardView cardView;
    private TextView categoryTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immigration_menu_screen);


        // Intent extras parameters
        String city = getIntent().getStringExtra(CITY);
        setTitle("Immigration");

        // Get Immigration Handler instance
        ImmigrationHandler.getInstance(getApplicationContext()).getImmigrationInformation();
    }

    /**
     * On click method for Important Things To Do List view
     * @param view
     */
    public void impThingstodoOnClick(View view) {
        cardView = findViewById(R.id.impThingsCv);
        categoryTitle = findViewById(R.id.impThingsTv);
        immigrationMenuItemOnClick(view, cardView, categoryTitle);
    }

    /**
     * On click method for Forms List view
     * @param view
     */
    public void formsOnClick(View view) {
        cardView = findViewById(R.id.formsCv);
        categoryTitle = findViewById(R.id.formsTv);
        immigrationMenuItemOnClick(view, cardView, categoryTitle);
    }
    /**
     * On click method for Offices List view
     * @param view
     */
    public void officesOnClick(View view) {
        cardView = findViewById(R.id.officesCv);
        categoryTitle = findViewById(R.id.officesTv);
        immigrationMenuItemOnClick(view, cardView, categoryTitle);
    }

    /**
     * On click method for FAQs List View
     * @param view
     */
    public void faqsOnClick(View view) {
        cardView = findViewById(R.id.faqCv);
        categoryTitle = findViewById(R.id.faqTv);
        immigrationMenuItemOnClick(view, cardView, categoryTitle);
    }

    /**
     * Generic On Click for Immigration menu items
     * @param view
     * @param cardView
     * @param categoryTitle
     */

    public void immigrationMenuItemOnClick(View view, CardView cardView, TextView categoryTitle) {
        Intent intent = new Intent(ImmigrationActivity.this, ImmigrationDetailActivity.class);
        int bgColor = cardView.getCardBackgroundColor().getDefaultColor();
        String categoryTitleStr = (String) categoryTitle.getText();


        intent.putExtra(ImmigrationDetailActivity.BG_COLOR, bgColor);
        intent.putExtra(ImmigrationDetailActivity.CATEGORY_TITLE, categoryTitleStr);

        startActivity(intent);
    }

    /**
     * Activity cycle methods
     */
    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

}

