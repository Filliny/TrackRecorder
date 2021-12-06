package com.example.trackrecorder.database;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginHolder {

    private static SharedPreferences sharedPreferences;
    private static final String USER_SHARED_PREFS_NAME = "LOGGED_USER";
    private static final String USER_ID_KEY = "USER_ID";
    private static LoginHolder instance;

    public LoginHolder(Context context){
        sharedPreferences = context.getSharedPreferences(USER_SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static LoginHolder getInstance(Context context){
        if(instance == null){
            instance = new LoginHolder(context);
        }
        return  instance;
    }

    private SharedPreferences.Editor getEditor(){
        return sharedPreferences.edit();
    }

    public void loginUser(int id){

        SharedPreferences.Editor editor = getEditor();
        editor.putInt(USER_ID_KEY,id);
        editor.commit();

    }

    public boolean isLogged(){
       return sharedPreferences.contains(USER_ID_KEY);
    }

    public void logoutUser(){
        getEditor().remove(USER_ID_KEY).commit();
    }

    public int getId(){
      return sharedPreferences.getInt(USER_ID_KEY,0);
    }
}
