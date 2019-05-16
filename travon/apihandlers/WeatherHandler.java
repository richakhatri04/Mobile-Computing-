package com.thealienobserver.nikhil.travon.apihandlers;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.thealienobserver.nikhil.travon.models.Weather;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public abstract class WeatherHandler {
    private static final String TAG = "WeatherHandler";
    private Context applicationContext;

    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/forecast?APPID=eb866e903a87bc24b5178943f993718e&lat=";

    public WeatherHandler(Context context) {
        this.applicationContext = context;
    }

    /**
     * Method to get 5 days weather/3 hourly each day based on the location(latitude and longitude)
     * @param latitude
     * @param longitude
     */
    public void getFiveDaysWeather(String latitude,String longitude) {
        RequestQueue requestQueue = Volley.newRequestQueue(applicationContext);
        Float lat=Float.parseFloat(latitude);
        Float lon=Float.parseFloat(longitude);
        latitude=String.format("%.2f", lat);
        longitude=String.format("%.2f", lon);

        //Url to get the forecast. Halifax's latitude and longitude are set default.
        String url = WEATHER_URL.concat(TextUtils.isEmpty(latitude) ? "44.649963&lon=-63.5802565&units=metric" : (latitude + "&lon=" + longitude+"&units=metric"));//+"&cnt=5"
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Weather Handler", response.toString());
                try {
                    // Generate news article list from the api response
                    JSONArray forecasts = response.getJSONArray("list");
                    ArrayList<Weather> forcastList = new ArrayList<>();
                    for(int forecastIdx = 0; forecastIdx < forecasts.length(); forecastIdx++) {
                        JSONObject forecast = forecasts.getJSONObject(forecastIdx);

                        String date = forecast.getString("dt_txt");
                        Double temprature = forecast.getJSONObject("main").getDouble("temp");
                        JSONObject weather = forecast.getJSONArray("weather").getJSONObject(0);
                        String description = weather.getString("description");
                        String imageUrl ="http://openweathermap.org/img/w/"+ weather.getString("icon")+".png";

                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// Convert Json date to readable date format.
                        Weather forecastModel = new Weather(temprature, description, imageUrl, df.parse(date));
                        forcastList.add(forecastModel);
                        Log.d("Reached here",""+forecastModel);
                    }

                    // Call the user's callback for post fetching news articles
                    WeatherHandler.this.postFetchingWeather(forcastList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.d("News Handler", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    /**
     *Abstract method implemented in the WeatherActivity Controller.
     * @param weather
     */
    public abstract void  postFetchingWeather(ArrayList<Weather> weather);

}
