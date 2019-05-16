package com.thealienobserver.nikhil.travon.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.google.android.gms.maps.model.LatLng;
import com.thealienobserver.nikhil.travon.R;
import com.thealienobserver.nikhil.travon.adapters.EventsAdapter;
import com.thealienobserver.nikhil.travon.apihandlers.EventsHandler;
import com.thealienobserver.nikhil.travon.models.Event;

import java.util.ArrayList;

/**
 * Created by Charley LeBlanc
 */

public class EventsActivity extends AppCompatActivity {

    public static final String LAT_LON_PARAM = "LAT_LON_PARAM";
    public static final String CITY_PARAM = "CITY_PARAM";

    private int page = 1;
    private int distance = 100;
    private EventsHandler eventsHandler;
    private LatLng currentlocation;
    private Button advanced, prev, next, search;
    private LinearLayout advSearch;
    private SeekBar distanceBar;
    private EditText distanceText, searchQuery;
    private CheckBox freeOnly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // Gather event details
        currentlocation = (LatLng) getIntent().getExtras().get(LAT_LON_PARAM);
        String cityName = getIntent().getExtras().getString(CITY_PARAM);

        setTitle(cityName + " Events");

        advanced = findViewById(R.id.advanced);
        prev = findViewById(R.id.prevPage);
        next = findViewById(R.id.nextPage);

        advSearch = findViewById(R.id.advancedSearchWindow);

        searchQuery = findViewById(R.id.searchQuery);
        distanceBar = findViewById(R.id.distanceBar);
        distanceText = findViewById(R.id.distanceText);
        freeOnly = findViewById(R.id.searchFreeCheckbox);
        search = findViewById(R.id.searchConfirm);

        eventsHandler = new EventsHandler(this) {
            @Override
            public void eventGatherFinish(ArrayList<Event> eventList, boolean newPage) {
                EventsActivity.this.setupEvents(eventList);
                next.setEnabled(newPage);
                if (page > 1)
                    prev.setEnabled(true);
                else
                    prev.setEnabled(false);
            }
        };
        // TODO: Handle options such as distance and search, as well as multiple pages.
        eventsHandler.getEventList(currentlocation, distanceBar.getProgress(), freeOnly.isChecked(), searchQuery.getText().toString(), page);

        advanced.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               if (advSearch.getVisibility() == View.VISIBLE)
                   advSearch.setVisibility(View.GONE);
               else
                   advSearch.setVisibility(View.VISIBLE);
           }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                prev.setEnabled(false);
                next.setEnabled(false);
                page--;
                try {
                    eventsHandler.getEventList(currentlocation, Integer.parseInt(distanceText.getText().toString()), freeOnly.isChecked(), searchQuery.getText().toString(), page);
                } catch (NumberFormatException e) {
                    eventsHandler.getEventList(currentlocation, distanceBar.getProgress(), freeOnly.isChecked(), searchQuery.getText().toString(), page);
                }            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                prev.setEnabled(false);
                next.setEnabled(false);
                page++;
                try {
                    eventsHandler.getEventList(currentlocation, Integer.parseInt(distanceText.getText().toString()), freeOnly.isChecked(), searchQuery.getText().toString(), page);
                } catch (NumberFormatException e) {
                    eventsHandler.getEventList(currentlocation, distanceBar.getProgress(), freeOnly.isChecked(), searchQuery.getText().toString(), page);
                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                page = 1;
                prev.setEnabled(false);
                next.setEnabled(false);
                try {
                    eventsHandler.getEventList(currentlocation, Integer.parseInt(distanceText.getText().toString()), freeOnly.isChecked(), searchQuery.getText().toString(), page);
                } catch (NumberFormatException e) {
                    eventsHandler.getEventList(currentlocation, distanceBar.getProgress(), freeOnly.isChecked(), searchQuery.getText().toString(), page);
                }
            }
        });

        distanceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    distanceText.setText(Integer.toString(progress));
            }
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        distanceText.addTextChangedListener(new TextWatcher() {
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
           public void onTextChanged(CharSequence s, int start, int before, int count) {}
           public void afterTextChanged(Editable s) {
               if (s.toString().equals("")) {
                   distanceText.setText("0");
                   return;
               }
               distanceBar.setProgress(Integer.parseInt(s.toString()));
           }
        });
    }

    private void setupEvents(ArrayList<Event> events) {

        RecyclerView eventsRecyclerView = findViewById(R.id.eventsRecyclerView);
        eventsRecyclerView.setAdapter(new EventsAdapter(this, events));
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //eventsRecyclerView.setAdapter();
    }
}
