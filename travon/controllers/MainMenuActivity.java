package com.thealienobserver.nikhil.travon.controllers;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;


import com.google.android.gms.maps.model.LatLng;
import com.thealienobserver.nikhil.travon.R;
import com.thealienobserver.nikhil.travon.apihandlers.MainMenuHandler;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {
    //    public static final String PlaceID = "PLACE_ID";
    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String CITY = "CITY";

    public static final String ADDRESSES = "ADDRESSES";
    private TextView cityTextView;
    private ImageView cityImageview;

    private ArrayList<Address> addresses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        cityTextView = findViewById(R.id.cityTextView);
        cityImageview = findViewById(R.id.cityImageView);

        addresses = getIntent().getParcelableArrayListExtra(ADDRESSES);
        cityTextView.setText(addresses.get(0).getLocality());

        String latitude = String.valueOf(addresses.get(0).getLatitude());
        String longitude = String.valueOf(addresses.get(0).getLongitude());
        MainMenuHandler mainMenuHandler = new MainMenuHandler(this, cityImageview);
        mainMenuHandler.getPlaceID(longitude, latitude);
    }

    public void welcomingOnClick(View view) {
        String city = addresses.get(0).getLocality();
        city = (city == null) ? addresses.get(0).getAdminArea() : city;

        Intent intent = new Intent(MainMenuActivity.this, WelcomingActivity.class);
        intent.putExtra(CITY, city);
        startActivity(intent);
    }


    public void recommendedPlacesOnClick(View view) {
        String latitude = String.valueOf(addresses.get(0).getLatitude());
        String longitude = String.valueOf(addresses.get(0).getLongitude());
        String city = addresses.get(0).getLocality();
        city = (city == null) ? addresses.get(0).getAdminArea() : city;

        Intent intent = new Intent(MainMenuActivity.this, RecommendedPlacesActivity.class);
        intent.putExtra(LATITUDE, latitude);
        intent.putExtra(LONGITUDE, longitude);
        intent.putExtra(CITY, city);
        startActivity(intent);

    }

    public void immigrationOnClick(View view) {
        String city = addresses.get(0).getLocality();
        city = (city == null) ? addresses.get(0).getAdminArea() : city;
        Intent immigrationInfoIntent = new Intent(MainMenuActivity.this, ImmigrationActivity.class);
        immigrationInfoIntent.putExtra(CITY, city);
        startActivity(immigrationInfoIntent);
    }

    public void eventsOnClick(View view) {
        LatLng currentLocation = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
        String city = addresses.get(0).getLocality();
        city = (city == null) ? addresses.get(0).getAdminArea() : city;

        Intent eventIntent = new Intent(this, EventsActivity.class);
        eventIntent.putExtra(EventsActivity.LAT_LON_PARAM, currentLocation);
        eventIntent.putExtra(EventsActivity.CITY_PARAM, city);
        startActivity(eventIntent);
    }

    public void findingRoomsOnClick(View view) {
        String city = addresses.get(0).getLocality();
        city = (city == null) ? addresses.get(0).getAdminArea() : city;
        LatLng currentLocation = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
        Intent availableRoomIntent = new Intent(this, FindingRoomsActivity.class);
        availableRoomIntent.putExtra(NewsActivity.CITY_PARAM, city);
        startActivity(availableRoomIntent);
    }

    public void comingSoonOnClick(View view) {
        Toast.makeText(this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
    }

    public void newsOnClick(View view) {
        String city = addresses.get(0).getLocality();
        city = (city == null) ? addresses.get(0).getAdminArea() : city;
        Intent newsIntent = new Intent(MainMenuActivity.this, NewsActivity.class);
        newsIntent.putExtra(NewsActivity.COUNTRY_PARAM, addresses.get(0).getCountryName());
        newsIntent.putExtra(NewsActivity.CITY_PARAM, city);
        startActivity(newsIntent);
    }

    public void weatherOnClick(View view) {
        LatLng currentLocation = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
        String country = addresses.get(0).getCountryCode();
        Intent weatherIntent = new Intent(this, WeatherActivity.class);
        weatherIntent.putExtra(WeatherActivity.COUNTRY_CODE_PARAM, country);
        weatherIntent.putExtra(WeatherActivity.LAT_LON_PARAM, currentLocation);
        weatherIntent.putExtra(WeatherActivity.LATITUDE, currentLocation.latitude);
        weatherIntent.putExtra(WeatherActivity.LONGITUDE, currentLocation.longitude);
        startActivity(weatherIntent);
    }

    public void costOfLivingOnClick(View view) {
        String city = addresses.get(0).getLocality();
        String country = addresses.get(0).getCountryName();

        Intent costOfLivingIntent = new Intent(this, CostOfLivingActivity.class);
        costOfLivingIntent.putExtra(CostOfLivingActivity.CITY, city);
        costOfLivingIntent.putExtra(CostOfLivingActivity.COUNTRY, country);

        startActivity(costOfLivingIntent);

    }

    public void servicesOnClick(View view) {
        String latitude = String.valueOf(addresses.get(0).getLatitude());
        String longitude = String.valueOf(addresses.get(0).getLongitude());
        String city = addresses.get(0).getLocality();
        city = (city == null) ? addresses.get(0).getAdminArea() : city;

        Intent intent = new Intent(MainMenuActivity.this, ServicesActivity.class);
        intent.putExtra(LATITUDE, latitude);
        intent.putExtra(LONGITUDE, longitude);
        intent.putExtra(CITY, city);
        startActivity(intent);

    }

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
