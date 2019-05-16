package com.thealienobserver.nikhil.travon.controllers;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thealienobserver.nikhil.travon.R;
import com.thealienobserver.nikhil.travon.adapters.CostOfLivingAdapter;
import com.thealienobserver.nikhil.travon.apihandlers.CostOfLivingHandler;
import com.thealienobserver.nikhil.travon.models.CostOfLivingItem;

import java.util.ArrayList;

public class CostOfLivingDetailActivity extends AppCompatActivity {
    // Intent extras parameters
    public static final String CITY = "CITY";
    public static final String BG_COLOR = "BG_COLOR";
    public static final String CATEGORY_TITLE = "CATEGORY_TITLE";

    // Private parameters
    private int mBgColor;
    private String mCity;
    private String mCategoryTitle;

    // Category names
    public static final String transport = "Transport";
    public static final String food = "Food";
    public static final String utilities = "Utilities";
    public static final String childcare = "Childcare";
    public static final String room = "Room";
    public static final String clothing = "Clothing";

    // Class parameters
    private ArrayList<CostOfLivingItem> mCostList;
    private CostOfLivingAdapter mAdapter;
    private CostOfLivingHandler mCostOfLivingHandlerInstance;

    // Layout components
    private TextView mCategoryTitleTv, mNameTV, mAvgCost, mRange, mLastUpdated;
    private RelativeLayout mCategoryLayout;
    private ListView mLvCostItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_of_living_detail);

        //App context
        mCostOfLivingHandlerInstance = CostOfLivingHandler.getInstance(getApplicationContext());

        // Intent extras parameters
        mCity = getIntent().getStringExtra(CITY);
        mBgColor = getIntent().getIntExtra(BG_COLOR, 0);
        mCategoryTitle = getIntent().getStringExtra(CATEGORY_TITLE);
        mCostList = new ArrayList<>();

        // Support Action config.
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(mBgColor));
        getSupportActionBar().setElevation(0);


        // Getting view components
        mLvCostItems = findViewById(R.id.costItems);
        mCategoryTitleTv = findViewById(R.id.categoryTitleTextView);
        mNameTV = findViewById(R.id.nameTV);
        mAvgCost = findViewById(R.id.avgCost);
        mRange = findViewById(R.id.range);
        mCategoryLayout = findViewById(R.id.categoryLayout);
        mLastUpdated = findViewById(R.id.lastUpdatedTv);

        // Setting view components
        mCategoryTitleTv.setText(mCategoryTitle);
        mLastUpdated.setText(mCostOfLivingHandlerInstance.getLastUpdated());

        //Setting color for some view components according to the selected option
        mCategoryLayout.setBackgroundColor(mBgColor);
        mNameTV.setTextColor(mBgColor);
        mAvgCost.setTextColor(mBgColor);
        mAvgCost.setText(mAvgCost.getText() + "(" + mCostOfLivingHandlerInstance.getCurrency() + ")");
        mRange.setTextColor(mBgColor);

        //Configuring adapter
        mAdapter = new CostOfLivingAdapter(this, R.layout.view_cost_items, mCostList);
        mLvCostItems.setAdapter(mAdapter);


        //Setting items content
        switch (mCategoryTitle) {
            case transport:
                ArrayList transport = mCostOfLivingHandlerInstance.getTransportation();
                setCostsListOnView(transport);
                break;
            case food:
                ArrayList food = mCostOfLivingHandlerInstance.getFood();
                setCostsListOnView(food);
                break;
            case utilities:
                ArrayList utilities = mCostOfLivingHandlerInstance.getUtilities();
                setCostsListOnView(utilities);
                break;
            case room:
                ArrayList room = mCostOfLivingHandlerInstance.getRoom();
                setCostsListOnView(room);
                break;
            case childcare:
                ArrayList childcare = mCostOfLivingHandlerInstance.getChildcare();
                setCostsListOnView(childcare);
                break;
            case clothing:
                ArrayList clothing = mCostOfLivingHandlerInstance.getClothing();
                setCostsListOnView(clothing);
                break;
        }
    }

    /**
     * Adds results to the costs and notifies the adapter.
     *
     * @param items
     */
    public void setCostsListOnView(ArrayList items) {
        mCostList.clear();
        // get and set items data ArrayList
        for (int i = 0; i < items.size(); i++) {
            CostOfLivingItem item = (CostOfLivingItem) items.get(i);
            mCostList.add(item);
        }
        mAdapter.notifyDataSetChanged();
    }
}
