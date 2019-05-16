package com.thealienobserver.nikhil.travon.controllers;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.thealienobserver.nikhil.travon.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Marker locationMarker;
    private GoogleMap mapInstance;
    private List<Address> addresses;

    private static final int VOICE_ACTIVITY_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setupMap();
        this.setupPlacesSearch();
        locationMarker = null;
    }

    private void setupMap() {
        SupportMapFragment userMapLocation = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        userMapLocation.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                mapInstance = googleMap;

                // Setup marker when user long clicks on the map
                googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        updateUserSelectedLocation(latLng);
                    }
                });

                // Ready map with the marker on user's initial location
                MainActivity.this.setMapToUserLocation();
            }
        });
    }

    private void setupPlacesSearch() {
        SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment) getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setHint(getString(R.string.search_hint));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                LatLng searchedPlace = place.getLatLng();
                updateUserSelectedLocation(searchedPlace);
                moveMapToPlace(searchedPlace);
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(getApplication().getApplicationContext(), "Unable to find place.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void voiceToSearch(View view) {
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.voice_command_text));
        startActivityForResult(speechIntent, VOICE_ACTIVITY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case VOICE_ACTIVITY_CODE:
                if(resultCode == RESULT_OK && data != null) {
                    ArrayList<String> speechResults = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    SupportPlaceAutocompleteFragment placeAutocompleteFragment = (SupportPlaceAutocompleteFragment) getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
                    String searchPlace = speechResults.get(0);
                    placeAutocompleteFragment.setText(searchPlace);
                    Geocoder geocoder = new Geocoder(this);
                    try {
                        List<Address> addresses = geocoder.getFromLocationName(searchPlace, 1);
                        if(addresses.size() > 0){
                            LatLng userLocation = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                            updateUserSelectedLocation(userLocation);
                            moveMapToPlace(userLocation);
                        } else {
                            Toast.makeText(this, "Unable to find place "+ searchPlace, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        Toast.makeText(this, "Unable to find place "+ searchPlace, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
                break;
        }
    }

    private void updateUserSelectedLocation(LatLng location) {
        if (locationMarker == null) {
            locationMarker = mapInstance.addMarker(new MarkerOptions().position(location));
        } else {
            locationMarker.setPosition(location);
        }
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        try {
            TextView placeName = findViewById(R.id.placeName);
            addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);

            String locality = addresses.get(0).getLocality();
            String adminArea = addresses.get(0).getAdminArea();

            Log.d("location", addresses.toString());
            RelativeLayout optionsDialog = findViewById(R.id.optionsDialog);
            if (locality != null || adminArea != null) {
                String area = (locality == null)? adminArea : locality;
                placeName.setText(area);
                optionsDialog.setVisibility(View.VISIBLE);
                locationMarker.setTitle(area);
            } else {
                optionsDialog.setVisibility(View.GONE);
                Toast.makeText(this, "Unable to find data on that location.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveMapToPlace(LatLng place) {
        mapInstance.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 10));
    }

    private void setMapToUserLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
        }
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Set user location if found
                    LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    updateUserSelectedLocation(userLocation);
                    moveMapToPlace(userLocation);
                } else {
                    // Set halifax location as default if user location not found
                    LatLng halifaxLocation = new LatLng(44.65054250000001, -63.606195099999994);
                    updateUserSelectedLocation(halifaxLocation);
                    moveMapToPlace(halifaxLocation);
                }
            }
        });
    }

    public void shortcutsOnClick(View view) {
        Button clickedButton = (Button) view;

        String city = addresses.get(0).getLocality();
        city = city == null ? addresses.get(0).getAdminArea(): city;
        String country = addresses.get(0).getCountryName();
        if (clickedButton.getText().toString().toUpperCase().equals("NEWS")) {

            Intent newsIntent = new Intent(this, NewsActivity.class);
            newsIntent.putExtra(NewsActivity.COUNTRY_PARAM, country);
            newsIntent.putExtra(NewsActivity.CITY_PARAM, city);
            startActivity(newsIntent);
        }
        else if(clickedButton.getText().toString().toUpperCase().equals("WEATHER")){
            LatLng currentLocation = this.locationMarker.getPosition();
            country = addresses.get(0).getCountryCode();
            Intent weatherIntent = new Intent(this, WeatherActivity.class);
            weatherIntent.putExtra(WeatherActivity.COUNTRY_CODE_PARAM, country);
            weatherIntent.putExtra(WeatherActivity.LAT_LON_PARAM, currentLocation);
            weatherIntent.putExtra(WeatherActivity.LATITUDE, currentLocation.latitude);
            weatherIntent.putExtra(WeatherActivity.LONGITUDE, currentLocation.longitude);
            startActivity(weatherIntent);
        } else if (clickedButton.getId() == R.id.eventsButton) {
            LatLng currentLocation = this.locationMarker.getPosition();
            Intent eventIntent = new Intent(this, EventsActivity.class);
            eventIntent.putExtra(EventsActivity.LAT_LON_PARAM, currentLocation);
            eventIntent.putExtra(EventsActivity.CITY_PARAM, city);
            startActivity(eventIntent);
        }
    }

    public void mainMenuOnClick(View view) {
        Intent menuIntent = new Intent(this, MainMenuActivity.class);
        menuIntent.putExtra(MainMenuActivity.ADDRESSES, new ArrayList<>(addresses));
        startActivity(menuIntent);
    }
}
