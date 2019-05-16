package com.thealienobserver.nikhil.travon.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.thealienobserver.nikhil.travon.R;
import com.thealienobserver.nikhil.travon.apihandlers.CostOfLivingHandler;

public class CostOfLivingActivity extends AppCompatActivity {
    // Intent extras parameters
    public static final String CITY = "CITY";
    public static final String COUNTRY = "COUNTRY";

    // Intent extra private parameters
    private String mCity;
    private String mCountry;

    // Layout components
    private CardView mCardView;
    private TextView mCategoryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_living_menu);

        // Intent extras parameters
        mCity = getIntent().getStringExtra(CITY);
        mCountry = getIntent().getStringExtra(COUNTRY);

        // Set title on action bar
        setTitle(getString(R.string.cost_of_living_title) + " " + mCity);

        // Get Cost Of Living Handler instance
        CostOfLivingHandler.getInstance(getApplicationContext()).getCostOfLivingFromCity(mCity, mCountry);
    }

    /**
     * On click method for Transport Card view
     * @param view
     */
    public void transportOnClick(View view) {
        mCardView = findViewById(R.id.transportCv);
        mCategoryTitle = findViewById(R.id.transportTitle);
        costOfLivingMenuItemOnClick(view, mCardView, mCategoryTitle);
    }

    /**
     * On click method for Food Card view
     * @param view
     */
    public void foodOnClick(View view) {
        mCardView = findViewById(R.id.foodCv);
        mCategoryTitle = findViewById(R.id.foodTv);
        costOfLivingMenuItemOnClick(view, mCardView, mCategoryTitle);
    }

    /**
     * On click method for Utilities Card view
     * @param view
     */
    public void utilitiesOnClick(View view) {
        mCardView = findViewById(R.id.utilitiesCv);
        mCategoryTitle = findViewById(R.id.utilitiesTv);
        costOfLivingMenuItemOnClick(view, mCardView, mCategoryTitle);
    }

    /**
     * On click method for Rooms Card view
     * @param view
     */
    public void roomsOnClick(View view) {
        mCardView = findViewById(R.id.roomCv);
        mCategoryTitle = findViewById(R.id.roomTv);
        costOfLivingMenuItemOnClick(view, mCardView, mCategoryTitle);
    }

    /**
     * On click method for Childcare Card view
     * @param view
     */
    public void childcareOnClick(View view) {
        mCardView = findViewById(R.id.childcareCv);
        mCategoryTitle = findViewById(R.id.childcareTv);
        costOfLivingMenuItemOnClick(view, mCardView, mCategoryTitle);
    }

    /**
     * On click method for Clothing Card view
     * @param view
     */
    public void clothingOnClick(View view) {
        mCardView = findViewById(R.id.clothingCv);
        mCategoryTitle = findViewById(R.id.clothingTv);
        costOfLivingMenuItemOnClick(view, mCardView, mCategoryTitle);
    }

    /**
     * Generic On Click for  Cost of living menu items
     * @param view
     * @param cardView
     * @param categoryTitle
     */

    public void costOfLivingMenuItemOnClick(View view, CardView cardView, TextView categoryTitle) {
        Intent intent = new Intent(CostOfLivingActivity.this, CostOfLivingDetailActivity.class);
        int bgColor = cardView.getCardBackgroundColor().getDefaultColor();
        String categoryTitleStr = (String) categoryTitle.getText();

        intent.putExtra(CostOfLivingDetailActivity.CITY, mCity);
        intent.putExtra(CostOfLivingDetailActivity.BG_COLOR, bgColor);
        intent.putExtra(CostOfLivingDetailActivity.CATEGORY_TITLE, categoryTitleStr);

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
