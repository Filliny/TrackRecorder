package com.example.trackrecorder;

import android.app.Application;
import android.location.Location;

import com.example.trackrecorder.database.DbContext;
import com.example.trackrecorder.database.PointsRepository;
import com.example.trackrecorder.database.UserRepository;

import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * Created by Oleh Filipchuk ( olegf.droid@gmail.com ) on 06.12.2021.
 */

public class App extends Application {

    UserRepository userRepository;
    PointsRepository pointsRepository;

    PublishSubject<Location> locationPublishSubject;

    private static App appInstance;

    public static App getInstance() {
        if (appInstance == null) appInstance = new App();

        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = this;
        userRepository = UserRepository.getInstance(DbContext.getDbContext(this));
        pointsRepository = PointsRepository.getInstance(DbContext.getDbContext(this));
        locationPublishSubject = PublishSubject.create();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public PointsRepository getPointsRepository() {
        return pointsRepository;
    }

    public PublishSubject<Location> getLocationPublishSubject() {
        return locationPublishSubject;
    }

}
