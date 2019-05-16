package com.thealienobserver.nikhil.travon.apihandlers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.thealienobserver.nikhil.travon.models.RecommendedPlace;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class MainMenuHandler {

    private Context applicationContext;
    private ImageView cityImageview;
    private static String api_key = "&key=AIzaSyDCywJBYgafoLew81-vpeGTN03_2vBB7jk";
    private RequestQueue requestQueue;

    public MainMenuHandler(Context context, ImageView cityImageview) {
        this.applicationContext = context;
        this.cityImageview = cityImageview;
    }

    public void getPlaceID(String longitude, String latitude) {
        requestQueue = Volley.newRequestQueue(applicationContext);
        String getPlaceIDURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitude + "," + longitude + "&radius=500" + api_key;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getPlaceIDURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    JSONObject cityJSON = results.getJSONObject(0);
                    String placeID = cityJSON.getString("id");
                    JSONArray photoArray = cityJSON.getJSONArray("photos");
                    String photoReference = photoArray.getJSONObject(0).getString("photo_reference");
                    Log.d("photoReference", photoReference);
                    getPlacePhoto(photoReference);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.d("Results", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void getPlacePhoto(String photoReference) {
        requestQueue = Volley.newRequestQueue(applicationContext);
        //https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJdd4hrwug2EcRmSrV3Vo6llI&key=API_KEY
        String getPlacePhotoURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photoReference + api_key;

        ImageRequest imageRequest = new ImageRequest(getPlacePhotoURL, new Response.Listener<Bitmap>() {

            @Override
            public void onResponse(Bitmap response) {
                cityImageview.setImageBitmap(response);

            }
        }, 0, 0, null, null);

        requestQueue.add(imageRequest);



    }


}


