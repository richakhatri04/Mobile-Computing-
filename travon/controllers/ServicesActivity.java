
package com.thealienobserver.nikhil.travon.controllers;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.thealienobserver.nikhil.travon.R;
import com.thealienobserver.nikhil.travon.adapters.RecommendedFragmentPagerAdapter;
import com.thealienobserver.nikhil.travon.adapters.ServicesFragmentPagerAdapter;


public class ServicesActivity extends AppCompatActivity {

    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomended_places);

        //Setting the tool bar title
        city = getIntent().getStringExtra(MainMenuActivity.CITY);
        setTitle("Services in " + city);


        // Creating the  reference for viewpager and adding adapter to it

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new ServicesFragmentPagerAdapter(getSupportFragmentManager(), city));


        //Creating the reference for tablayout and adding viewpager to it

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
