package com.example.trackrecorder.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.trackrecorder.R;
import com.example.trackrecorder.databinding.ActivityMainBinding;
import com.example.trackrecorder.databinding.NavHeaderMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    NavController navController;
    MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbarSupp);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_history, R.id.nav_login)
                .setOpenableLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);


        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        binding.setModelMain(viewModel);
        binding.setLifecycleOwner(this);
        binding.appBarMain.setBarModel(viewModel);

        NavHeaderMainBinding headerMenuBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header_main, binding.navView, false);
        binding.navView.addHeaderView(headerMenuBinding.getRoot());
        headerMenuBinding.setMainModel(viewModel);

        //to show record button only on map fragment- leave here to availability expand it ot all fragments
        navController.addOnDestinationChangedListener((controller, destination, arguments)
                -> viewModel.getShowFloatingButton().set(destination.getId() == R.id.nav_home));

        viewModel.getLoginObserve().observe(this, this::setActualFragment);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setActualFragment(Boolean logged) {

        if (getSupportActionBar() == null) return;

        if (logged) {
            //ask for permission before display any map fragments
            if (isLocationPermissionAvailable()) {

                navController.navigate(R.id.nav_home);
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 1);
            }

        } else {

            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }


    public void selectAvatar(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        startActivityForResult(gallery, 1);
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != -1 && requestCode != 1 || data == null) return;

        ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), data.getData());
        try {
            Bitmap bitmapBig = ImageDecoder.decodeBitmap(source);

            Bitmap bitmap = Bitmap.createScaledBitmap(bitmapBig, 200, 200, false);
            Drawable ava = new BitmapDrawable(getResources(), bitmap);
            viewModel.setGlobalAvatar(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean isLocationPermissionAvailable() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PermissionChecker.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && isLocationPermissionAvailable()) {
            setActualFragment(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}