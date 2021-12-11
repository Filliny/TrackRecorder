package com.example.trackrecorder.ui.history;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.trackrecorder.R;
import com.example.trackrecorder.databinding.FragmentHistoryBinding;
import com.example.trackrecorder.ui.MainActivityViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class HistoryFragment extends Fragment implements DatePickerDialog.OnDateSetListener, OnMapReadyCallback {

    GoogleMap googleMap;
    FragmentHistoryBinding binding;
    Polyline polyline;
    MainActivityViewModel mainViewModel;
    HistoryFragmentViewModel viewModel;

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

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapFragment);

        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);

        mainViewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
        viewModel = new ViewModelProvider(this).get(HistoryFragmentViewModel.class);

        viewModel.setCurrentUser(mainViewModel.getCurrentUser());

        viewModel.getTrackList().observe(getViewLifecycleOwner(), this::DrawTrack);

        binding.setModelHistory(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.setDateSelected(new Date());

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.history_top_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Date date = Calendar.getInstance().getTime();

        if (item.getItemId() == R.id.calendarOpen) {
            DatePickerDialog dialog = new DatePickerDialog(getContext(), this, date.getYear() + 1900, date.getMonth(), date.getDate());
            dialog.show();
            return true;
        }
        return false;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Toast.makeText(getContext(), i + " " + i1 + " " + i2, Toast.LENGTH_LONG).show();

        Calendar calendar = Calendar.getInstance();
        calendar.set(i, i1, i2);

        Date res = calendar.getTime();

        viewModel.setDateSelected(res);
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

        if (points.size() > 1) {
            moveCamera(points.get(0));
        }

    }

    private void moveCamera(LatLng location) {
        CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(
                location,
                15
        );
        googleMap.animateCamera(camera);
    }


}