package com.thealienobserver.nikhil.travon.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thealienobserver.nikhil.travon.R;
import com.thealienobserver.nikhil.travon.models.Room;
import java.util.ArrayList;

/**
 * Class to set the values of each item in Finding Rooms Card.
 */
public class FindingRoomsAdapter extends RecyclerView.Adapter<FindingRoomsAdapter.ViewHolder>{
    private static final String TAG = "AvailableRoomsAdapter";

    private Context callerContext;
    ArrayList<Room> rooms;

    public FindingRoomsAdapter(Context context, ArrayList<Room> rooms) {
        this.callerContext = context;
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public FindingRoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_finding_rooms_item, parent, false);
        FindingRoomsAdapter.ViewHolder holder = new FindingRoomsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FindingRoomsAdapter.ViewHolder viewHolder, int i) {
        Room availableroomsElement = this.rooms.get(i);
        Glide.with(callerContext).load(availableroomsElement.getImg1()).into(viewHolder.roomsImage);
        viewHolder.rent.setText(String.valueOf(availableroomsElement.getRent()));
        viewHolder.sellernumber.setText(String.valueOf(availableroomsElement.getSellerphone()));
        viewHolder.sellerlocation.setText(String.valueOf(availableroomsElement.getSellerlocation()));
        viewHolder.rent.setText(String.valueOf(availableroomsElement.getRent()));
        viewHolder.shortdescription.setText(String.valueOf(availableroomsElement.getShortdescription()));

        String[] images = {availableroomsElement.getImg1(), availableroomsElement.getImg2(), availableroomsElement.getImg3()};
        ImageViewPagerAdapter imageViewPagerAdapter = new ImageViewPagerAdapter(callerContext, images);
        viewHolder.roomImages.setAdapter(imageViewPagerAdapter);
        viewHolder.imageDots.setupWithViewPager(viewHolder.roomImages);
    }

    @Override
    public int getItemCount() {
        return this.rooms.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView roomsImage;
        TextView rent,city,sellernumber,sellerlocation,shortdescription;
        ViewPager roomImages;
        TabLayout imageDots;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomsImage = itemView.findViewById(R.id.roomsImage);
            rent = itemView.findViewById(R.id.rent);
            city=itemView.findViewById(R.id.city);
            sellernumber=itemView.findViewById(R.id.sellernumber);
            sellerlocation=itemView.findViewById(R.id.sellerlocation);
            shortdescription=itemView.findViewById(R.id.shortdescription);
            roomImages = itemView.findViewById(R.id.roomImages);
            imageDots = itemView.findViewById(R.id.image_dots);
        }
    }
}