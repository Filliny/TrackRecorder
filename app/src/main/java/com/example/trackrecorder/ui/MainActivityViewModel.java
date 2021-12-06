package com.example.trackrecorder.ui;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.trackrecorder.App;
import com.example.trackrecorder.database.SharedLoginStorage;
import com.example.trackrecorder.database.UserRepository;
import com.example.trackrecorder.database.models.UserModel;


public class MainActivityViewModel extends AndroidViewModel {

    UserRepository userRepository;
    UserModel currentUser;
    MutableLiveData<Boolean> loginObserve = new MutableLiveData<>();
    SharedLoginStorage sharedLogin;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        userRepository = App.getInstance().getUserRepository();
        sharedLogin = SharedLoginStorage.getInstance(getApplication());
        int id = sharedLogin.getId();

        if(sharedLogin.isLogged()){
            currentUser = userRepository.getUser(sharedLogin.getId());
            loginObserve.postValue(true);

        }else{
            loginObserve.postValue(false);
        }

    }

    public UserModel getCurrentUser() { return currentUser; }

    public MutableLiveData<Boolean> getLoginObserve() {
        return loginObserve;
    }

    public void logoutUser(){
        sharedLogin.logoutUser();
        loginObserve.postValue(false);
    }

    public void loginUser(UserModel user){
        sharedLogin.loginUser(user.getId());
        currentUser = user;
        loginObserve.postValue(true);

    }

}
