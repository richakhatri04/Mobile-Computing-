package com.thealienobserver.nikhil.travon.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thealienobserver.nikhil.travon.R;
import com.thealienobserver.nikhil.travon.adapters.WeatherCardAdapter;
import com.thealienobserver.nikhil.travon.apihandlers.CityWeatherHandler;
import com.thealienobserver.nikhil.travon.apihandlers.WeatherHandler;
import com.thealienobserver.nikhil.travon.models.Weather;
import com.thealienobserver.nikhil.travon.models.CityWeather;


import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity {

    public static final String COUNTRY_CODE_PARAM = "COUNTRY_CODE_PARAM";
    public static final String LAT_LON_PARAM = "LAT_LON_PARAM";
    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String TITLE_WEATHER = "Current Weather & Forecast";
    private CityWeatherHandler cityWeatherHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        //Get the values of set on the main activity through intent's bundle
        Intent i = getIntent();
        Bundle b = i.getExtras();
        String Latitude = String.valueOf(b.get("LATITUDE"));
        String Longitude = String.valueOf(b.get("LONGITUDE"));

        //Set the title of the weather page
        setTitle(TITLE_WEATHER);

        this.cityWeatherHandler = new CityWeatherHandler() {
            @Override
            public void postWeatherApiCall(CityWeather cityWeather) {
                WeatherActivity.this.updateWeatherOnScreen(cityWeather);
            }
        };

        WeatherHandler weatherHandler = new WeatherHandler(this) {
            @Override
            public void postFetchingWeather(ArrayList<Weather> weatherModel) {
                WeatherActivity.this.setupWeatherCards(weatherModel);
            }
        };
        weatherHandler.getFiveDaysWeather(Latitude, Longitude);
        cityWeatherHandler.getWeatherByCity(this, Latitude, Longitude);
    }

    /**
     *Method to set elements in Card View
     * @param weather
     */
    private void setupWeatherCards(ArrayList<Weather> weather) {
        RecyclerView weatherRecyclerView = findViewById(R.id.weatherRecyclerView);
        weatherRecyclerView.setAdapter(new WeatherCardAdapter(this, weather));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        weatherRecyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     *Method to set the value on each element on the layout
     * @param cityWeather
     */
    public void updateWeatherOnScreen(CityWeather cityWeather) {
        // Method to update current weather.
        TextView city = findViewById(R.id.city);
        TextView temp = findViewById(R.id.temperature);
        TextView minMax = findViewById(R.id.minMax);
        TextView humidity = findViewById(R.id.humidity);
        TextView clouds = findViewById(R.id.clouds);
        TextView description = findViewById(R.id.description);
        ImageView weatherIcon = findViewById(R.id.weatherIcon);
        city.setText(cityWeather.getCity().toUpperCase());
        temp.setText(String.valueOf(Math.round(cityWeather.getTemperature())) + "\u2103");
        minMax.setText("Min. " + Math.round(cityWeather.getTempMin()) + "\u2103" + "   Max. " + Math.round(cityWeather.getTempMax()) + "\u2103");
        humidity.setText(Math.round(cityWeather.getHumidity()) + "%\nHumidity");
        clouds.setText(Math.round(cityWeather.getClouds()) + "%\nClouds");
        description.setText(cityWeather.getDescription().toUpperCase());

        // Glide library to download image and set to ImageView
        Glide.with(this).load(cityWeather.getIconUrl()).into(weatherIcon);
    }
}