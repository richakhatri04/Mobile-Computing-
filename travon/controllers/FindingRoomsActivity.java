package com.thealienobserver.nikhil.travon.controllers;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.thealienobserver.nikhil.travon.R;
import com.thealienobserver.nikhil.travon.adapters.FindingRoomsAdapter;
import com.thealienobserver.nikhil.travon.apihandlers.FindingRoomsHandler;
import com.thealienobserver.nikhil.travon.models.Room;

import java.util.ArrayList;

public class FindingRoomsActivity extends AppCompatActivity {
    public static final String CITY_PARAM = "CITY_PARAM";
    public static final String FINDINGROOMS = "Finding Rooms";

    //String to call herokue service
    private static final String ROOMS_URL ="https://mc-project.herokuapp.com/rooms?city=";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_finding_rooms);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String cityParam = getIntent().getStringExtra(FindingRoomsActivity.CITY_PARAM);
        final String urlWithBase;

        //Set the title of the finding room page
        setTitle(FINDINGROOMS+" in "+cityParam);


        //Conditional statements display the rooms available for three cities Halifax, Toronto or Vancouver for which json data is available on herokue service.
        if(cityParam.equals("Halifax")) {
            urlWithBase=ROOMS_URL.concat("Halifax");
        }
        else if(cityParam.equals("Toronto")) {
            urlWithBase=ROOMS_URL.concat("Toronto");
        }
        else{
            urlWithBase=ROOMS_URL.concat("Vancouver");
        }


        FindingRoomsHandler newsHandler = new FindingRoomsHandler(this) {
            @Override
            public void postFetchingAvailableRooms(ArrayList<Room> availableRooms) {
                FindingRoomsActivity.this.setupRoomsCards(availableRooms);
            }
        };
        newsHandler.getAvailableRooms(urlWithBase);
    }

    /**
     * Generic method to set the Room Cards for Recycler.
     * @param availableRoomsItem
     */
    private void setupRoomsCards(ArrayList<Room> availableRoomsItem) {
        RecyclerView roomsRecyclerView = findViewById(R.id.availableRoomsRecyclerView);
        roomsRecyclerView.setAdapter(new FindingRoomsAdapter(this, availableRoomsItem));
        roomsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


}
