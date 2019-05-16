package com.thealienobserver.nikhil.travon.apihandlers;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.thealienobserver.nikhil.travon.R;
import com.thealienobserver.nikhil.travon.models.CostOfLivingItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CostOfLivingHandler {
    //Tag for logging messages
    private static final String TAG = "CostOfLivingHandler";

    // Instance for singleton
    private static CostOfLivingHandler mInstance;

    private Context mApplicationContext;
    private RequestQueue mRequestQueue;
    // Access to app resources
    private Resources res;


    //API key to access to Numbeo information
    private static String mApiKey = "api_key=rkouvmmc5fm0zj";

    //Currency from the selected city
    private String mCurrency;
    // Date of last update by day/month
    private String mLastUpdated;

    //Cost of living items results
    private ArrayList<CostOfLivingItem> mFood = new ArrayList();
    private ArrayList<CostOfLivingItem> mTransportation = new ArrayList();
    private ArrayList<CostOfLivingItem> mUtilities = new ArrayList();
    private ArrayList<CostOfLivingItem> mRoom = new ArrayList();
    private ArrayList<CostOfLivingItem> mClothing = new ArrayList();
    private ArrayList<CostOfLivingItem> mChildcare = new ArrayList();

    /**
     * Constructor
     *
     * @param context
     */

    public CostOfLivingHandler(Context context) {
        this.mApplicationContext = context;
        this.res = mApplicationContext.getResources();
    }

    /**
     * Singleton method
     *
     * @param context
     * @return
     */
    public static synchronized CostOfLivingHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CostOfLivingHandler(context.getApplicationContext());
        }
        return mInstance;
    }

    /**
     * Sends the request to numbeo to get the results for the selected city
     *
     * @param city
     * @param country
     */

    public void getCostOfLivingFromCity(String city, String country) {
        mRequestQueue = Volley.newRequestQueue(mApplicationContext);
        String getCostOfLivingURL = "https://www.numbeo.com/api/city_prices?" + mApiKey + "&query=" + city + "%20" + country;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getCostOfLivingURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Get prices json array from response
                    JSONArray pricesJSONArray = response.getJSONArray("prices");
                    // Get currency from json response
                    mCurrency = response.getString("currency");
                    // Get last update date from json response
                    mLastUpdated = String.format(res.getString(R.string.last_updated_value), response.getString("monthLastUpdate"), response.getString("yearLastUpdate"));
                    // Classify results according to designated categories
                    classifyResults(pricesJSONArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
                Toast.makeText(mApplicationContext, res.getString(R.string.try_again_later), Toast.LENGTH_LONG).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    /**
     * Classifies the results to be able to show them in the designated categories
     *
     * @param pricesJSONArray
     * @throws JSONException
     */
    public void classifyResults(JSONArray pricesJSONArray) throws JSONException {
        for (int pos = 0; pos < pricesJSONArray.length(); pos++) {
            //Actual cost item
            JSONObject itemJSON = pricesJSONArray.getJSONObject(pos);

            //Item related attributes
            String item_name = itemJSON.getString("item_name");
            double lowest_price = itemJSON.getDouble("lowest_price");
            double average_price = itemJSON.getDouble("average_price");
            double highest_price = itemJSON.getDouble("highest_price");

            if (item_name.contains("Restaurants") || item_name.contains("Market")) {
                mFood.add(new CostOfLivingItem(item_name, lowest_price, average_price, highest_price));
            } else {
                if (item_name.contains("Transportation")) {
                    mTransportation.add(new CostOfLivingItem(item_name, lowest_price, average_price, highest_price));
                } else {
                    if (item_name.contains("Utilities")) {
                        mUtilities.add(new CostOfLivingItem(item_name, lowest_price, average_price, highest_price));
                    } else {
                        if (item_name.contains("Rent Per Month") || item_name.contains("Buy Apartment Price")) {
                            mRoom.add(new CostOfLivingItem(item_name, lowest_price, average_price, highest_price));
                        } else {
                            if (item_name.contains("Clothing And Shoes")) {
                                mClothing.add(new CostOfLivingItem(item_name, lowest_price, average_price, highest_price));
                            } else {
                                if (item_name.contains("Childcare")) {
                                    mChildcare.add(new CostOfLivingItem(item_name, lowest_price, average_price, highest_price));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Getters for the categories results array
     */
    public ArrayList getFood() {
        return mFood;
    }

    public ArrayList getTransportation() {
        return mTransportation;
    }

    public ArrayList<CostOfLivingItem> getUtilities() {
        return mUtilities;
    }

    public ArrayList<CostOfLivingItem> getRoom() {
        return mRoom;
    }

    public ArrayList<CostOfLivingItem> getClothing() {
        return mClothing;
    }

    public ArrayList<CostOfLivingItem> getChildcare() {
        return mChildcare;
    }

    /**
     * Currency getter
     *
     * @return mCurrency
     */
    public String getCurrency() {
        return mCurrency;
    }

    /**
     * Last update date
     *
     * @return mLastUpdated
     */
    public String getLastUpdated() {
        return mLastUpdated;
    }
}
