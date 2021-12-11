package com.example.trackrecorder.ui.map;

import android.annotation.SuppressLint;
import android.app.Application;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.trackrecorder.App;
import com.example.trackrecorder.database.models.PointModel;
import com.example.trackrecorder.database.models.UserModel;
import com.example.trackrecorder.helpers.DateTimeConverter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

public class MapFragmentViewModel extends AndroidViewModel  {

    LocationRequest lastLocationRequest;
    FusedLocationProviderClient lastLocationProviderClient;
    LocationCallback lastLocationCallback;
    Disposable locationSubscription;

    UserModel currentUser;
    List<LatLng> pointsList;

    private static final int FOLLOW_LOCATION_THRESHOLD = 50;

    MutableLiveData<Location> staticPosition = new MutableLiveData<>();
    MutableLiveData<List<LatLng>> pointsToDraw = new MutableLiveData<>();
    MutableLiveData<Location> markerFollow = new MutableLiveData<>();
    MutableLiveData<Location> cameraMove = new MutableLiveData<>();


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
                staticPosition.postValue(locationResult.getLastLocation());
            }
        };

        locationSubscription =  App.getInstance().getLocationPublishSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::recordLocation);


    }

    public void setCurrentUser(UserModel currentUser) {
        this.currentUser = currentUser;
    }

    public MutableLiveData<Location> getStaticPosition() {
        return staticPosition;
    }

    public MutableLiveData<List<LatLng>> getPointsToDraw() {
        return pointsToDraw;
    }

    public MutableLiveData<Location> getMarkerFollow() {
        return markerFollow;
    }

    public MutableLiveData<Location> getCameraMove() {
        return cameraMove;
    }



    @SuppressLint("MissingPermission")
    public void zoomToLocation(Boolean isRecordStarted){

        if(isRecordStarted){

            lastLocationProviderClient.removeLocationUpdates(lastLocationCallback);

        }else{
            lastLocationProviderClient.requestLocationUpdates(lastLocationRequest,lastLocationCallback,null);

        }
    }


    private void recordLocation(Location location){
        Log.d("LOCATION", "accept: "+ location.toString());

        if(pointsList == null){
            pointsList = new ArrayList<>();
        }

        pointsList.add(new LatLng(location.getLatitude(), location.getLongitude()));
        pointsToDraw.postValue(pointsList);

        markerFollow.postValue(location);

        if(pointsList.size()%FOLLOW_LOCATION_THRESHOLD == 0){
            cameraMove.postValue(location);
        }

       savePoint(location);

    }

    private void savePoint(Location location){

        PointModel point = new PointModel();
        point.setLatitude(location.getLatitude());
        point.setLongitude(location.getLongitude());
        point.setUserId(currentUser.getId());
        point.setCreate_date(DateTimeConverter.getCurrentDate());
        point.setCreate_time(DateTimeConverter.getCurrentTime());

        App.getInstance().getPointsRepository().addPoint(point);

    }


    @Override
    protected void onCleared() {
        super.onCleared();

        locationSubscription.dispose();
    }
}
