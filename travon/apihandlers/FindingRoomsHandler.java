package com.thealienobserver.nikhil.travon.apihandlers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.thealienobserver.nikhil.travon.models.Room;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;


public abstract class FindingRoomsHandler {
    private Context applicationContext;


    public FindingRoomsHandler(Context context) {
        this.applicationContext = context;
    }

    /**
     * Method to get the list of rooms and set it on appropriate properties.
     * @param url(Heroku url for the city selected).
     */
    public void getAvailableRooms(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(applicationContext);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("c", response.toString());
                try {
                    // Generate news article list from the api response
                    JSONArray newsArticles = response;
                    ArrayList<Room> toprooms = new ArrayList<>();
                    for(int roomsItr=0; roomsItr < newsArticles.length(); roomsItr++) {
                        JSONObject roomsArticle = newsArticles.getJSONObject(roomsItr);
                        String title = roomsArticle.getString("shortdescription");
                        String rent = roomsArticle.getString("rent");
                        String location=roomsArticle.getString("location");
                        String currency = roomsArticle.getString("currency");
                        String description=roomsArticle.getString("Description");
                        String bathroom=roomsArticle.getString("Bathrooms");
                        String bedroom=roomsArticle.getString("Bedrooms");
                        String furnished=roomsArticle.getString("Furnished");
                        String petfriendly=roomsArticle.getString("PetFriendly");
                        String postingdate=roomsArticle.getString("PostingDate");
                        String img1=roomsArticle.getString("img1");
                        String img2=roomsArticle.getString("img2");
                        String img3=roomsArticle.getString("img3");
                        String sellername=roomsArticle.getString("sellername");
                        String sellerlocation=roomsArticle.getString("sellerlocation");
                        String sellermailId=roomsArticle.getString("sellermailId");
                        String sellerphone=roomsArticle.getString("sellerphone");
                        Room rooms = new Room(title,rent,location,currency,description,bathroom,bedroom,furnished,petfriendly,postingdate,img1,img2,img3,sellername,sellerlocation,sellermailId,sellerphone);
                        toprooms.add(rooms);

                        // Call the user's callback for post fetching news articles
                        FindingRoomsHandler.this.postFetchingAvailableRooms(toprooms);
                    }
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
     * Abstract method implemented in the FindingRoomsActivity Controller.
     * @param availablerooms
     */
    public abstract void  postFetchingAvailableRooms(ArrayList<Room> availablerooms);
}

