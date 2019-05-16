package com.thealienobserver.nikhil.travon.apihandlers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.thealienobserver.nikhil.travon.models.ImmigrationItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImmigrationHandler {
    //Tag for logging messages
    private static final String TAG = "ImmigrationHandler";

    // Instance for singleton
    private static ImmigrationHandler mInstance;
    private Context applicationContext;
    private RequestQueue requestQueue;

    //ArrayList Creation for Immigration submenus
    private ArrayList importantThings = new ArrayList<>();
    private ArrayList offices = new ArrayList<>();
    private ArrayList forms = new ArrayList<>();
    private ArrayList faqs = new ArrayList<>();

    /**
     * Constructor
     *
     * @param context
     */
    public ImmigrationHandler(Context context) {
        this.applicationContext = context;
    }

    public static synchronized ImmigrationHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ImmigrationHandler(context.getApplicationContext());
        }
        return mInstance;
    }


    /**
     * Sends the request to Immigration API to get the results for the selected city
     */
    public void getImmigrationInformation() {
        requestQueue = Volley.newRequestQueue(applicationContext);
        String URL = "https://mc-project.herokuapp.com/immigration?country=canada";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Get Important Things To Do json array from response
                    JSONArray importantThingsJSONArray = response.getJSONArray("important_things");
                    // Get forms json array from response
                    JSONArray formsJSONArray = response.getJSONArray("forms");
                    // Get offices json array from response
                    JSONArray officesJSONArray = response.getJSONArray("offices");
                    // Get faqs json array from response
                    JSONArray faqsJSONArray = response.getJSONArray("FAQs");

                    importantThings = getResults(importantThingsJSONArray);
                    forms = getResults(formsJSONArray);
                    offices = getResults(officesJSONArray);
                    faqs = getResults(faqsJSONArray);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
                Toast.makeText(applicationContext, "There was an error. Please try again later", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Classifies the results to be able to show them in the designated categories
     *
     * @param resultArray
     * @throws JSONException
     */

    public ArrayList getResults(JSONArray resultArray) throws JSONException {
        ArrayList<ImmigrationItem> results = new ArrayList<>();
        for (int pos = 0; pos < resultArray.length(); pos++) {

            //Getting name and description from API
            JSONObject itemJSON = resultArray.getJSONObject(pos);
            String name;
            if (itemJSON.has("name")) {
                name = itemJSON.getString("name");
            } else {
                name = "";
            }
            results.add(new ImmigrationItem(name, itemJSON.getString("description")));
        }
        return results;
    }

    /**
     * Getters for the immigration submenu options results array
     */
    public ArrayList getImportantThings() {
        return importantThings;
    }

    public ArrayList<String> getOffices() {
        return offices;
    }

    public ArrayList<String> getForms() {
        return forms;
    }

    public ArrayList<String> getFaqs() {
        return faqs;
    }
}
