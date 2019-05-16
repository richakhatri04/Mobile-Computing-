package com.thealienobserver.nikhil.travon.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thealienobserver.nikhil.travon.fragments.RecommendedPlacesFragment;
import com.thealienobserver.nikhil.travon.fragments.ServicesFragment;

public class ServicesFragmentPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Plumbers", "Electricians", "Moving companies","Accounting"};
    private String city;

    public ServicesFragmentPagerAdapter(FragmentManager fm, String city) {
        super(fm);
        this.city = city;
    }

    @Override
    public Fragment getItem(int position) {

        //Creating fragment instance based on position

        return ServicesFragment.newInstance(position + 1, city);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        //Set the title base on item position

        return tabTitles[position];
    }
}
