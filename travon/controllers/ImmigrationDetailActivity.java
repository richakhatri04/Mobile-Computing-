package com.thealienobserver.nikhil.travon.controllers;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thealienobserver.nikhil.travon.R;
import com.thealienobserver.nikhil.travon.adapters.ImmigrationAdapter;
import com.thealienobserver.nikhil.travon.apihandlers.ImmigrationHandler;
import com.thealienobserver.nikhil.travon.models.ImmigrationItem;

import java.util.ArrayList;

public class ImmigrationDetailActivity extends AppCompatActivity {
    // Intent extras parameters
    public static final String CATEGORY_TITLE = "CATEGORY_TITLE";
    public static final String BG_COLOR = "BG_COLOR";


    // Private parameters
    private int mBgColor;
    private String mCategoryTitle;


    // Class parameters
    private ImmigrationAdapter mAdapter;
    private ImmigrationHandler mImmigrationHandlerInstance;
    private ArrayList<ImmigrationItem> mImmigrationItemList;

    // Layout components
    private TextView mTvimmigrationTitle;
    private ListView mLvimmigrationItems;
    private RelativeLayout mImmigrationLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immigration_detail);

        //App context
        mImmigrationHandlerInstance = ImmigrationHandler.getInstance(getApplicationContext());

        // Intent extras parameters
        mBgColor = getIntent().getIntExtra(BG_COLOR, 0);
        mCategoryTitle = getIntent().getStringExtra(CATEGORY_TITLE);
        mImmigrationItemList = new ArrayList<>();


        // Support Action config.
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(mBgColor));
        getSupportActionBar().setElevation(0);


        // Getting view components
        mLvimmigrationItems = findViewById(R.id.immigrationItemsLv);
        mTvimmigrationTitle = findViewById(R.id.immigrationTitleTextView);
        mImmigrationLayout = findViewById(R.id.immigrationLayout);


        // Setting view components
        mTvimmigrationTitle.setText(mCategoryTitle);
        mImmigrationLayout.setBackgroundColor(mBgColor);

        //Configuring adapter
        mAdapter = new ImmigrationAdapter(this, R.layout.activity_immigration_detail, mImmigrationItemList);
        mLvimmigrationItems.setAdapter(mAdapter);

        //Setting items content
        switch (mCategoryTitle) {
            case "Important Things To Do":
                ArrayList impThings = mImmigrationHandlerInstance.getImportantThings();
                setItemsListOnView(impThings);
                break;
            case "Forms":
                ArrayList forms = mImmigrationHandlerInstance.getForms();
                setItemsListOnView(forms);
                break;
            case "Offices":
                ArrayList offices = mImmigrationHandlerInstance.getOffices();
                setItemsListOnView(offices);
                break;
            case "FAQ":
                ArrayList faqs = mImmigrationHandlerInstance.getFaqs();
                setItemsListOnView(faqs);
                break;
        }
        }

        public void setItemsListOnView(ArrayList items) {
        mImmigrationItemList.clear();
        // get and set items data ArrayList
        for (int i = 0; i < items.size(); i++) {
            ImmigrationItem item = (ImmigrationItem) items.get(i);
            mImmigrationItemList.add(item);
        }
        mAdapter.notifyDataSetChanged();
    }
}