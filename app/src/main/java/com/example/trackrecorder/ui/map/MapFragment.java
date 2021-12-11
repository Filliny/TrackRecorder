package com.example.trackrecorder.ui.map;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap googleMap;
    FragmentMapBinding binding;
    Marker marker;
    Polyline polyline;

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

        viewModel.setCurrentUser(mainViewModel.getCurrentUser());


        //move to static position
        viewModel.getStaticPosition().observe(getViewLifecycleOwner(), location -> {
            setMarkerPosition(location);
            moveCamera(location);
        });

        //get current state
        viewModel.zoomToLocation(mainViewModel.getGlobalRecordState().getValue());
        //subscribe to changes
        mainViewModel.getGlobalRecordState().observe(getViewLifecycleOwner(),
                aBoolean -> viewModel.zoomToLocation(aBoolean));

        viewModel.getPointsToDraw().observe(getViewLifecycleOwner(), this::DrawTrack);

        viewModel.getMarkerFollow().observe(getViewLifecycleOwner(), this::setMarkerPosition);

        viewModel.getCameraMove().observe(getViewLifecycleOwner(), this::moveCamera);
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

    private void DrawTrack(List<LatLng> points) {

        if (googleMap == null) return;
        polyline.setPoints(points);

    }

    private void setMarkerPosition(Location location) {

        if (googleMap == null) return;

        if (marker == null) {

            MarkerOptions options = new MarkerOptions()
                    .position(new LatLng(location.getLatitude(), location.getLongitude()));
            marker = googleMap.addMarker(options);

        } else {
            marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
        }

    }

    private void moveCamera(Location location) {
        if (googleMap == null) return;

        CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()),
                15
        );
        googleMap.animateCamera(camera);
    }


}