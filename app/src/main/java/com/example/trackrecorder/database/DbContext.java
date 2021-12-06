package com.example.trackrecorder.database;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.room.Room;

import java.util.concurrent.TimeUnit;

public class DbContext {

    private  AppDatabaseRoom  db_context;
    private static DbContext instance;


    private DbContext(AppDatabaseRoom db_context){
        this.db_context = db_context;
    }

    @SuppressLint("UnsafeOptInUsageError")
    public static AppDatabaseRoom getDbContext(Context context){
        if(instance == null){

            AppDatabaseRoom in_context = Room.databaseBuilder(context,AppDatabaseRoom.class,"movie_app_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    //.addMigrations(AppDatabaseRoom.MIGRATION_5_6)
                    .setAutoCloseTimeout(10, TimeUnit.MINUTES)
                    .build();

            instance = new DbContext(in_context);
        }

        return instance.db_context;
    }

}
