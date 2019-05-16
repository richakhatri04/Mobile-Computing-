package com.thealienobserver.nikhil.travon.apihandlers;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.thealienobserver.nikhil.travon.models.CityWeather;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class CityWeatherHandler {
    private static final String TAG = "WeatherApiHandler";
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?appid=eb866e903a87bc24b5178943f993718e&units=metric&lat=";
    public static final String ICON_URL = "http://openweathermap.org/img/w/";

    public void getWeatherByCity(final Context context, String latitude,String longitude) {

        Float lat = Float.parseFloat(latitude);
        Float lon = Float.parseFloat(longitude);
        latitude = String.format("%.2f", lat);
        longitude = String.format("%.2f", lon);
        //set default lat ang longitude for Halifax
        String url = BASE_URL.concat(TextUtils.isEmpty(latitude) ? "44.649963&lon=-63.5802565" : (latitude + "&lon=" + longitude + "&cnt=5"));
        RequestQueue queue = Volley.newRequestQueue(context);
        // Set city in the url an send get request for current weather.
        JsonObjectRequest openWeatherRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d(TAG, response.toString());
                    // Parse response and get current weather.
                    CityWeather cityWeather = CityWeatherHandler.this.parseWeatherResponse(response);
                    CityWeatherHandler.this.postWeatherApiCall(cityWeather);
                } catch (JSONException e) {
                    // Data response in incorrect format.
                    Log.d(TAG, "Error while parsing the json.");
                    CityWeatherHandler.this.postWeatherApiCall(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message;
                if (error instanceof NetworkError) {
                    // Unable to establish Internet connection.
                    message = "Cannot connect to Internet...Please check your connection!";
                } else {
                    // Unable to find city data or city name invalid.
                    message = "Unable to find data for the city you entered. ";
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Unable to query the api");

                // Notify the view that error has occured.
                CityWeatherHandler.this.postWeatherApiCall(null);
            }
        });

        // Send json request to network
        queue.add(openWeatherRequest);
    }

    private CityWeather parseWeatherResponse(JSONObject response) throws JSONException {
        // Using only first object of weather
        JSONObject weather = response.getJSONArray("weather").getJSONObject(0);
        JSONObject main = response.getJSONObject("main");
        double temperature = main.getDouble("temp");
        String description = weather.getString("description");
        double humidity = main.getDouble("humidity");
        double tempMin = main.getDouble("temp_min");
        double tempMax = main.getDouble("temp_max");
        String city=response.getString("name");
        String iconUrl = ICON_URL + weather.getString("icon") + ".png";

        int clouds = response.getJSONObject("clouds").getInt("all");
        CityWeather cityWeather = new CityWeather(city, temperature, description, humidity, tempMin, tempMax, clouds, iconUrl);
        return cityWeather;
    }

    public abstract void postWeatherApiCall(CityWeather cityWeather);
}
