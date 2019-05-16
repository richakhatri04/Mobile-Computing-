package com.thealienobserver.nikhil.travon.apihandlers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.thealienobserver.nikhil.travon.models.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class EventsHandler {

    private Context applicationContext;

    // start of request url, need to append latitude, longitude, distance away, search query, page, and free/not.
    private static final String EVENT_START_URL = "https://www.eventbriteapi.com/v3/events/search/?sort_by=date&expand=venue&token=75YLW2PESU67Z3C2R7AM";

    public EventsHandler(Context context) { this.applicationContext = context; }

    public void getEventList(LatLng location, int distance, boolean freeonly, String searchQuery, int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(applicationContext);
        String url = EVENT_START_URL + "&location.latitude=" + location.latitude + "&location.longitude=" + location.longitude + "&location.within=" + distance + "km&page=" + page + "&q=" + searchQuery;
        if (freeonly)
            url += "&price=free";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Check if there are more pages
                    boolean newPage = response.getJSONObject("pagination").getBoolean("has_more_items");
                    // Get event
                    JSONArray events = response.getJSONArray("events");
                    ArrayList<Event> eventList = new ArrayList<>();
                    for(int item = 0; item < events.length(); item++) {
                        JSONObject jsonEvent = events.getJSONObject(item);
                        String name = jsonEvent.getJSONObject("name").getString("text");
                        String description = jsonEvent.getJSONObject("description").getString("text");
                        String url = jsonEvent.getString("url");
                        String imageUrl;
                        try {
                            imageUrl = jsonEvent.getJSONObject("logo").getJSONObject("original").getString("url");
                        } catch (JSONException e) {
                            imageUrl = "";
                        }
                        String start = jsonEvent.getJSONObject("start").getString("local");
                        String end = jsonEvent.getJSONObject("end").getString("local");
                        boolean isFree = jsonEvent.getBoolean("is_free");
                        double latitude = jsonEvent.getJSONObject("venue").getJSONObject("address").getDouble("latitude");
                        double longitude = jsonEvent.getJSONObject("venue").getJSONObject("address").getDouble("longitude");
                        String address = jsonEvent.getJSONObject("venue").getJSONObject("address").getString("address_1");
                        String address2 = jsonEvent.getJSONObject("venue").getJSONObject("address").getString("address_2");
                        if (!address2.equals("null"))
                            address += "\n" + jsonEvent.getJSONObject("venue").getJSONObject("address").getString("address_2");
                        // If address_1 and address_2 are null, set a placeholder
                        if (address.equals("null"))
                            address = "an unknown address";

                        LatLng location = new LatLng(latitude, longitude);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        Date startTime = sdf.parse(start);
                        Date endTime = sdf.parse(end);

                        Event event = new Event(name, description, url, imageUrl, startTime, endTime, isFree, location, address);
                        eventList.add(event);

                        EventsHandler.this.eventGatherFinish(eventList, newPage);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Log.d("Event Handler", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public abstract void eventGatherFinish(ArrayList<Event> eventList, boolean newPage);
}
