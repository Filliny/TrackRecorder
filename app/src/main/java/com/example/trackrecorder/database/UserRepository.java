package com.example.trackrecorder.database;

import android.annotation.SuppressLint;

import com.example.trackrecorder.database.models.UserModel;

/**
 * Created by Oleh Filipchuk ( olegf.droid@gmail.com ) on 06.12.2021.
 */

public class UserRepository {

    private static UserRepository instance;

    AppDatabaseRoom db;

    @SuppressLint("UnsafeOptInUsageError")
    private UserRepository(AppDatabaseRoom databaseContext) {
        this.db = databaseContext;
    }

    public static UserRepository getInstance(AppDatabaseRoom databaseContext) {

        if (instance == null) instance = new UserRepository(databaseContext);
        return instance;
    }

    public void addUser(UserModel user) {

        db.getUserDao().insert(user);
    }

    public void deleteUsers(UserModel user) {
        db.getUserDao().delete(user);
    }

    public UserModel getUser(int id) {

        return db.getUserDao().getUser(id);
    }

    public UserModel getUserFromRequest(UserModel userRequest) {

        return db.getUserDao().getAuthUser(userRequest.getEmail(), userRequest.getPassword());
    }

    public boolean isExist(String email) {
        return !(db.getUserDao().getExistUser(email) == null);
    }


    public void updateUser(UserModel user) {
        db.getUserDao().update(user);
    }

    public boolean isEmpty() {
        return db.getUserDao().getAllUsers().size() == 0;
    }
}
