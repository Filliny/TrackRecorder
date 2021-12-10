package com.example.trackrecorder.ui.map;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.View;

import com.example.trackrecorder.App;
import com.example.trackrecorder.R;
import com.example.trackrecorder.databinding.FragmentMapBinding;
import com.example.trackrecorder.ui.MainActivityViewModel;

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


public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap googleMap;
    FragmentMapBinding binding;
    Marker marker;
    Polyline polyline;
    List<LatLng> points = new ArrayList<>();

    MainActivityViewModel mainViewModel;
    MapFragmentViewModel viewModel;

    public MapFragment() {
        super(R.layout.fragment_map);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentMapBinding.bind(view);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapFragment);

        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);

        mainViewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
        viewModel = new ViewModelProvider(this).get(MapFragmentViewModel.class);

        viewModel.getMarkerPosition().observe(getViewLifecycleOwner(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                setMarkerPosition(location);
                moveCamera(location);
            }
        });

        mainViewModel.getGlobalRecordState().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                viewModel.zoomToLocation(aBoolean);
            }
        });


        App.getInstance().getLocationPublishSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> {
                    DrawPosition(location);
                    Log.d("LOCATION", "accept: ");
                });

        if(!mainViewModel.getRecordState().get()){
            mainViewModel.getGlobalRecordState().postValue(false);
        }
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        this.googleMap = googleMap;
        this.googleMap.getUiSettings().setZoomControlsEnabled(true);
        this.googleMap.setPadding(10, 10, 10, 200);
        this.googleMap.setMyLocationEnabled(true);

        polyline = googleMap.addPolyline(new PolylineOptions()
                .width(5)
                .color(Color.RED));

    }


    private void DrawPosition(Location location) {

        if (googleMap == null) return;

        //Update marker position
        setMarkerPosition(location);

        points.add(new LatLng(location.getLatitude(), location.getLongitude()));
        polyline.setPoints(points);

    }

    private void setMarkerPosition(Location location) {

        if (marker == null) {

            MarkerOptions options = new MarkerOptions()
                    .position(new LatLng(location.getLatitude(), location.getLongitude()));
            marker = googleMap.addMarker(options);

        } else {
            marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
        }

    }

    private void moveCamera(Location location) {
        CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()),
                15
        );
        googleMap.animateCamera(camera);
    }


}