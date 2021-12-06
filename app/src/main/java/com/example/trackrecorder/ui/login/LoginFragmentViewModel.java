package com.example.trackrecorder.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.trackrecorder.database.models.ErrorModel;
import com.example.trackrecorder.database.models.UserModel;

public class LoginFragmentViewModel extends ViewModel {

    public UserModel user;
    ErrorModel errorHolder;

//    public LoginFragmentViewModel(@NonNull Application application) {
//        super(application);
//    }

    public ErrorModel getErrorHolder() {
        return errorHolder;
    }

    public void loginButtonPress(){
        UserModel userr = user;
    }
}
