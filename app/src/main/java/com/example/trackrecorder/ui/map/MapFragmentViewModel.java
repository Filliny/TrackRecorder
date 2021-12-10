package com.example.trackrecorder.ui.map;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MapFragmentViewModel extends AndroidViewModel  {

    LocationRequest lastLocationRequest;
    FusedLocationProviderClient lastLocationProviderClient;
    LocationCallback lastLocationCallback;

    public MapFragmentViewModel(@NonNull Application application) {
        super(application);



        lastLocationProviderClient =   LocationServices.getFusedLocationProviderClient(getApplication());
        lastLocationRequest = new LocationRequest();
        lastLocationRequest.setInterval(5000);
        lastLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        lastLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                locationResult.getLastLocation();
            }
        };
    }

    @SuppressLint("MissingPermission")


    private void zoomToLocation(Boolean startZoom){

        if(startZoom){

            lastLocationProviderClient.requestLocationUpdates(lastLocationRequest,lastLocationCallback,null);
        }
    }
}
