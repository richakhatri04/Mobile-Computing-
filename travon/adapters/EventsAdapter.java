package com.thealienobserver.nikhil.travon.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thealienobserver.nikhil.travon.R;
import com.thealienobserver.nikhil.travon.models.Event;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private Context callerContext;
    ArrayList<Event> eventList;

    public EventsAdapter(Context context, ArrayList<Event> eventList) {
        this.callerContext = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_event_items, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Event currentEvent = this.eventList.get(i);
        // Only set first 200 characters of the description
        String description = currentEvent.getDescription();
        if (description.length() <= 200) {
            viewHolder.eventDescription.setText(description);
        }
        else {
            viewHolder.eventDescription.setText(description.substring(0, 200) + "...");
        }
        viewHolder.eventName.setText(currentEvent.getName());
        Glide.with(callerContext).load(currentEvent.getImageUrl()).into(viewHolder.eventImage);
        if (!currentEvent.isFree()) {
            viewHolder.freeText.setVisibility(View.INVISIBLE);
        }
        viewHolder.eventTime.setText("From " + currentEvent.getStartTime().toString()
                + " to " + currentEvent.getEndTime().toString());
        viewHolder.eventLocation.setText("At " + currentEvent.getAddress());

    }

    @Override
    public int getItemCount() { return this.eventList.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView eventImage;
        TextView eventName, eventDescription, eventTime, eventLocation, freeText;

        public ViewHolder(@NonNull View itemView) {
            // TODO: Add new items
            super(itemView);
            eventImage = itemView.findViewById(R.id.eventImage);
            eventName = itemView.findViewById(R.id.eventName);
            eventDescription = itemView.findViewById(R.id.eventDescription);
            eventTime = itemView.findViewById(R.id.eventTime);
            eventLocation = itemView.findViewById(R.id.eventLocation);
            freeText = itemView.findViewById(R.id.freeText);
        }
    }
}
