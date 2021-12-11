package com.example.trackrecorder.database;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedLoginStorage {

    private static SharedPreferences sharedPreferences;
    private static final String USER_SHARED_PREFS_NAME = "LOGGED_USER";
    private static final String USER_ID_KEY = "USER_ID";
    private static SharedLoginStorage instance;

    public SharedLoginStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(USER_SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static SharedLoginStorage getInstance(Context context) {
        if (instance == null) {
            instance = new SharedLoginStorage(context);
        }
        return instance;
    }

    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }

    public void loginUser(int id) {

        SharedPreferences.Editor editor = getEditor();
        editor.putInt(USER_ID_KEY, id);
        editor.commit();

    }

    public boolean isLogged() {
        return sharedPreferences.contains(USER_ID_KEY);
    }

    public void logoutUser() {
        getEditor().remove(USER_ID_KEY).commit();
    }

    public int getId() {
        return sharedPreferences.getInt(USER_ID_KEY, 0);
    }
}
