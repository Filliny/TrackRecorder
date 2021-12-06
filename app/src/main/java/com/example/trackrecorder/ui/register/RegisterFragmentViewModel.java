package com.example.trackrecorder.ui.register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.trackrecorder.App;
import com.example.trackrecorder.database.UserRepository;
import com.example.trackrecorder.database.models.UserModel;

/**
 * Created by Oleh Filipchuk ( olegf.droid@gmail.com ) on 06.12.2021.
 */

public class RegisterFragmentViewModel extends AndroidViewModel {

    UserRepository userRepository;
    public UserModel userCandidate;

    public RegisterFragmentViewModel(@NonNull Application application) {
        super(application);
        userCandidate = new UserModel();
        userRepository = App.getInstance().getUserRepository();
    }

    public void registerButtonPress(){
        if(!registerValidate()) return;
        registerUser();
    }

    private boolean registerValidate(){
        return true;
    }

    private void registerUser(){

        userRepository.addUser(userCandidate);
    }
}
