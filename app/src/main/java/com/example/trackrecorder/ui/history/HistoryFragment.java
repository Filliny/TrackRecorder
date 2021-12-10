package com.example.trackrecorder.ui.history;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.trackrecorder.R;
import com.example.trackrecorder.databinding.FragmentHistoryBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.time.DateTimeException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class HistoryFragment extends Fragment implements DatePickerDialog.OnDateSetListener, OnMapReadyCallback {

    GoogleMap googleMap;
    Marker marker;
    FragmentHistoryBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    public HistoryFragment() {
        super(R.layout.fragment_history);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding = FragmentHistoryBinding.bind(view);


        SupportMapFragment supportMapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.mapFragment);


        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Toast.makeText(getContext(),i+" "+i1+" "+i2,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.history_top_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Date date = Calendar.getInstance().getTime();

        if(item.getItemId() == R.id.calendarOpen){
            DatePickerDialog dialog = new DatePickerDialog(getContext(),this,date.getYear()+1900,date.getMonth(),date.getDate());
            dialog.show();
            return true;
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMyLocationEnabled(true);

    }


    private void goToLocation(Location location){

        if(googleMap == null) return;

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
    }
}