package com.example.trackrecorder;

import android.app.Application;

import com.example.trackrecorder.database.DbContext;
import com.example.trackrecorder.database.UserRepository;

/**
 * Created by Oleh Filipchuk ( olegf.droid@gmail.com ) on 06.12.2021.
 */

public class App extends Application {

    UserRepository userRepository;

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
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

}
