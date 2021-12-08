package com.example.trackrecorder.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import com.example.trackrecorder.App;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationRecordService extends Service {

    private FusedLocationProviderClient locationProviderClient;

    private LocationCallback locationCallback;


    public LocationRecordService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                if (locationResult == null) {
                    return;
                } else {
                    for (Location location : locationResult.getLocations()) {

                        Log.d("loc", location.toString());
                        //App.instance.locationPublishSubject.onNext(location);
                        App.getInstance().getLocationPublishSubject().onNext(location);
                    }
                }
            }
        };

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationProviderClient.requestLocationUpdates(locationRequest,
                locationCallback,
                null);

    }
}