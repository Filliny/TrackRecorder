package com.example.trackrecorder.ui.map;

import android.annotation.SuppressLint;
import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MapFragmentViewModel extends AndroidViewModel  {

    LocationRequest lastLocationRequest;
    FusedLocationProviderClient lastLocationProviderClient;
    LocationCallback lastLocationCallback;

    public MutableLiveData<Location> getMarkerPosition() {
        return markerPosition;
    }

    MutableLiveData<Location> markerPosition = new MutableLiveData<>();

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
                markerPosition.postValue(locationResult.getLastLocation());

            }
        };


    }

    @SuppressLint("MissingPermission")


    public void zoomToLocation(Boolean isRecordStarted){

        if(isRecordStarted){

            lastLocationProviderClient.removeLocationUpdates(lastLocationCallback);

        }else{
            lastLocationProviderClient.requestLocationUpdates(lastLocationRequest,lastLocationCallback,null);
        }
    }
}
