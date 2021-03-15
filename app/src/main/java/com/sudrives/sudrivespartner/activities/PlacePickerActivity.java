package com.sudrives.sudrivespartner.activities;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.databinding.ActivityPlacePickerBinding;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PlacePickerActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String TAG = PlacePickerActivity.class.getSimpleName();
    double lat = 0, lng = 0;
    LatLng latLng;
    static boolean isGpsEnabled = false;
    static boolean isNetworkLocationEnabled = false;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1000;
    Geocoder geocoder;
    GoogleMap.OnCameraIdleListener onCameraIdleListener;
    String latHome, longHome, homeTown, country, state, city;
    Button btn_setLocation;
    ActivityPlacePickerBinding binding;
    TextView textView;
    Button setLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_place_picker);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
        binding.btnSetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("hometown", homeTown);
                intent.putExtra("country", country);
                intent.putExtra("latitude", latHome);
                intent.putExtra("longitude", longHome);
                intent.putExtra("city", city);
                intent.putExtra("state", state);
                setResult(Activity.RESULT_OK, intent);

                finish();
            }

        });
        // Initialize Places.
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyAVNsvTkS6bt2-q12RgQoL0S9g7o_VbxeQ");
        }

// Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize the AutocompleteSupportFragment.
        textView = findViewById(R.id.autocomplete_fragment);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchCalled();
            }
        });

        //implementation of geocoder
        geocoder = new Geocoder(PlacePickerActivity.this);

        configureCameraIdle();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (getLocationWithCheckNetworkAndGPS(this) != null) {


            // Add a marker in Sydney and move the camera
            LatLng latLng = new LatLng(getLocationWithCheckNetworkAndGPS(this).getLatitude(), getLocationWithCheckNetworkAndGPS(this).getLongitude());
            //mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            CameraUpdate center =
                    CameraUpdateFactory.newLatLng(latLng);
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);

            mMap.moveCamera(center);
            mMap.animateCamera(zoom);

            try {
                List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                textView.setText(addressList.get(0).getAddressLine(0));
                if (addressList != null && addressList.size() > 0) {
                    String locality = addressList.get(0).getAddressLine(0);
                    String countr = addressList.get(0).getCountryName();
                    String city_name = addressList.get(0).getLocality();
                    String state_name = addressList.get(0).getAdminArea();
                    if (!locality.isEmpty() && !countr.isEmpty())
                        textView.setText(locality + "  " + countr);
                    latHome = latLng.latitude + "";
                    longHome = latLng.longitude + "";
                    country = countr;
                    homeTown = locality;
                    city = city_name;
                    state = state_name;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMap.setOnCameraIdleListener(onCameraIdleListener);


        }

    }

    public void onSearchCalled() {
        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getAddress());
                Toast.makeText(getApplicationContext(), "ID: " + place.getId() + "address:" + place.getAddress() + "Name:" + place.getName() + " latlong: " + place.getLatLng(), Toast.LENGTH_LONG).show();
                String address = place.getAddress();

                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                CameraUpdate center =
                        CameraUpdateFactory.newLatLng(new LatLng(place.getLatLng().latitude,
                                place.getLatLng().longitude));
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);

                mMap.moveCamera(center);
                mMap.animateCamera(zoom);

                textView.setText(address);
                geocoder = new Geocoder(PlacePickerActivity.this);

                try {
                    List<Address> addressList = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        String locality = addressList.get(0).getAddressLine(0);
                        String countr = addressList.get(0).getCountryName();
                        String city_name = addressList.get(0).getLocality();
                        String state_name = addressList.get(0).getAdminArea();
                        if (!locality.isEmpty() && !countr.isEmpty())
                            textView.setText(locality + "  " + countr);
                        latHome = place.getLatLng().latitude + "";
                        longHome = place.getLatLng().longitude + "";
                        country = countr;
                        homeTown = locality;
                        city = city_name;
                        state = state_name;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                // do query with address

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(getApplicationContext(), "Error: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    public static Location getLocationWithCheckNetworkAndGPS(Context mContext) {
        LocationManager lm = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        assert lm != null;
        isGpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkLocationEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location networkLoacation = null, gpsLocation = null, finalLoc = null;
        if (isGpsEnabled)
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return null;
            }
        gpsLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (isNetworkLocationEnabled)
            networkLoacation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (gpsLocation != null && networkLoacation != null) {

            //smaller the number more accurate result will
            if (gpsLocation.getAccuracy() > networkLoacation.getAccuracy())
                return finalLoc = networkLoacation;
            else
                return finalLoc = gpsLocation;

        } else {

            if (gpsLocation != null) {
                return finalLoc = gpsLocation;
            } else if (networkLoacation != null) {
                return finalLoc = networkLoacation;
            }
        }
        return finalLoc;
    }


    private void configureCameraIdle() {
        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                LatLng latLng = mMap.getCameraPosition().target;
                geocoder = new Geocoder(PlacePickerActivity.this);

                try {
                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        String locality = addressList.get(0).getAddressLine(0);
                        String countr = addressList.get(0).getCountryName();
                        if (!locality.isEmpty() && !countr.isEmpty())
                            textView.setText(locality + "  " + countr);
                        latHome = latLng.latitude + "";
                        longHome = latLng.longitude + "";
                        country = countr;
                        homeTown = locality;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
    }


}
