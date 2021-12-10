package com.example.trackrecorder.services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.trackrecorder.App;
import com.example.trackrecorder.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationRecordService extends Service {

    private FusedLocationProviderClient locationProviderClient;

    private LocationCallback locationCallback;

    private final Integer RECORD_NOTIFICATION_ID = 4;


    public LocationRecordService() {
    }

    @Override
    public IBinder onBind(Intent intent) {return  null;
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



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(RECORD_NOTIFICATION_ID,
                showRecordNotification(intent.getStringExtra("msg")));

        return super.onStartCommand(intent, flags, startId);
    }


    private Notification showRecordNotification(String msg) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), getRecordNotifyChanel())
                .setSmallIcon(R.drawable.ic_message)
                .setContentTitle("Location is recording")
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        return builder.build();
    }

    private String getRecordNotifyChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("record_channel_id",
                    "Allow record location in background",
                    NotificationManager.IMPORTANCE_HIGH);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        return "record_channel_id";
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        locationProviderClient.removeLocationUpdates(locationCallback);
    }
}