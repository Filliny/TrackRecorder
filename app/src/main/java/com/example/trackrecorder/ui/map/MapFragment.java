package com.example.trackrecorder.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trackrecorder.App;
import com.example.trackrecorder.R;
import com.example.trackrecorder.databinding.FragmentMapBinding;
import com.example.trackrecorder.services.LocationRecordService;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    Location locationLast;
    Intent intentLocation;
    GoogleMap googleMap;
    FragmentMapBinding binding;
    Marker marker;
    Polyline polyline;
    List<LatLng> points = new ArrayList<>();

    public MapFragment() {
        super(R.layout.fragment_map);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentMapBinding.bind(view);

        SupportMapFragment supportMapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.mapFragment);

//        if (supportMapFragment == null) {
//            FragmentManager fragmentManager = getFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            supportMapFragment = SupportMapFragment.newInstance();
//            fragmentTransaction.replace(R.id.mapFragment, supportMapFragment).commit();
//        }

        supportMapFragment.getMapAsync(this::onMapReady);

        App.getInstance().getLocationPublishSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Location>() {
                    @Override
                    public void accept(Location location) throws Throwable {
                        DrawPosition(location);
                        Log.d("LOCATION", "accept: ");
                    }
                });

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        this.googleMap = googleMap;
        polyline = googleMap.addPolyline(new PolylineOptions()
                .width(5)
                .color(Color.RED));



    }



    private void DrawPosition(Location location){

        if(googleMap == null) return;

        //Update marker position
        if(marker == null){

            MarkerOptions options = new MarkerOptions()
                    .position(new LatLng(location.getLatitude(),location.getLongitude()));
            marker = googleMap.addMarker(options);

        }else{
            marker.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
        }

        CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(),location.getLongitude()),
                15
        );
        googleMap.animateCamera(camera);

        points.add(new LatLng(location.getLatitude(), location.getLongitude()));
        polyline.setPoints(points);

    }



}