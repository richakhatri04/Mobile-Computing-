package com.thealienobserver.nikhil.travon.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.thealienobserver.nikhil.travon.R;
import com.thealienobserver.nikhil.travon.models.CostOfLivingItem;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CostOfLivingAdapter extends ArrayAdapter<CostOfLivingItem> {
    //Tag for logging messages
    private static final String TAG = "CostOfLivingAdapter";
    //ArrayList for the cost items
    private ArrayList<CostOfLivingItem> mCostItemsList;

    // Access to app resources
    private Resources res;

    //Formatting to show only 2 digits after decimal point.
    private NumberFormat mNumberFormat = new DecimalFormat("##.###");
    private Context mApplicationContext;

    /**
     * Constructor
     *
     * @param context
     * @param textViewResourceId
     * @param costItemsList
     */

    public CostOfLivingAdapter(Context context, int textViewResourceId, ArrayList<CostOfLivingItem> costItemsList) {
        super(context, textViewResourceId, costItemsList);
        this.mApplicationContext = context;
        this.mCostItemsList = costItemsList;
        this.res = mApplicationContext.getResources();
    }

    /**
     * Sets the values received from the array list in each item that will be shown in the view.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.view_cost_items, null);
        }

        CostOfLivingItem item = mCostItemsList.get(position);

        if (item != null) {
            //Getting the components from the view
            TextView nameTv = v.findViewById(R.id.tvName);
            TextView avgCostTv = v.findViewById(R.id.tvAvgCost);
            TextView rangeTv = v.findViewById(R.id.tvRange);

            //Sets the values for the view components
            nameTv.setText(item.getItemName());
            avgCostTv.setText(mNumberFormat.format(item.getAveragePrice()));
            String pricesRange = mNumberFormat.format(item.getLowestPrice()) + " - " + mNumberFormat.format(item.getHighestPrice());
            rangeTv.setText(pricesRange);
        } else {
            Log.e(TAG, String.format(res.getString(R.string.null_item)));
            Toast.makeText(mApplicationContext, res.getString(R.string.try_again_later), Toast.LENGTH_LONG).show();
        }

        return v;
    }
}
