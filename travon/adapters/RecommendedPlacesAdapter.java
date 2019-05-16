package com.thealienobserver.nikhil.travon.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thealienobserver.nikhil.travon.R;
import com.thealienobserver.nikhil.travon.controllers.RecommendedPlacesDetailActivity;
import com.thealienobserver.nikhil.travon.models.RecommendedPlace;

import java.util.ArrayList;

public class RecommendedPlacesAdapter extends RecyclerView.Adapter<RecommendedPlacesAdapter.ViewHolder> {
    private static final String TAG = "PlaceCardsAdapter";
    public static final String PLACE_ADDRESS = "address";
    public static final String PLACE_PHONE = "phone";

    private Context callerContext;
    private ArrayList<RecommendedPlace> recommendedPlaces;

    public static final String PLACE_IMAGE = "placeImage";
    public static final String PLACE_TITLE = "placeTitle";
    public static final String PLACE_DESCRIPTION = "placeDescription";

    public RecommendedPlacesAdapter(Context context, ArrayList<RecommendedPlace> recommendedPlaces) {
        this.callerContext = context;
        this.recommendedPlaces = recommendedPlaces;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_places_items, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "bind view called");
        final RecommendedPlace currentPlace = this.recommendedPlaces.get(i);

        //Setting  the data to the views

        viewHolder.tv_description.setText(currentPlace.getDescription());
        viewHolder.tv_title.setText(currentPlace.getName());
        viewHolder.tv_address.setText(currentPlace.getFormattedAddress());
        viewHolder.tv_phone.setText(currentPlace.getFormattedPhoneNumber());


        //Creating Circular loading for place holder

        CircularProgressDrawable loading = new CircularProgressDrawable(callerContext);
        loading.setStrokeWidth(5f);
        loading.setCenterRadius(30f);
        loading.start();

        //Showing Place Image using glide with loading place holder


        Glide.with(callerContext).load(currentPlace.getImage_ref()).
                apply(new RequestOptions().placeholder(loading).error(loading)).into(viewHolder.iv_recommended_place);

        //Creating the Intent for Deatil activity with place details

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(callerContext, RecommendedPlacesDetailActivity.class);
                intent.putExtra(PLACE_DESCRIPTION, currentPlace.getDescription());
                intent.putExtra(PLACE_IMAGE, currentPlace.getImage_ref());
                intent.putExtra(PLACE_TITLE, currentPlace.getName());
                intent.putExtra(PLACE_ADDRESS, currentPlace.getFormattedAddress());
                intent.putExtra(PLACE_PHONE, currentPlace.getFormattedPhoneNumber());
                callerContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        // Showing no of places base recommended places size

        return this.recommendedPlaces.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        //Creating references for views

        ImageView iv_recommended_place;
        TextView tv_description, tv_title, tv_phone, tv_address;
        CardView container;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_recommended_place = itemView.findViewById(R.id.iv_recommended_place);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_title = itemView.findViewById(R.id.tv_title);
            container = itemView.findViewById(R.id.container);
            tv_phone = itemView.findViewById(R.id.tv_phone_number);
            tv_address = itemView.findViewById(R.id.tv_location);


        }
    }

}
