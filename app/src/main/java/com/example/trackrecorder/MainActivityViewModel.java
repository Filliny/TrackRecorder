package com.example.trackrecorder;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.trackrecorder.database.LoginHolder;


public class MainActivityViewModel extends AndroidViewModel {

    public MutableLiveData<Boolean> getLoginObserve() {
        return loginObserve;
    }

    MutableLiveData<Boolean> loginObserve = new MutableLiveData<>();
    LoginHolder loginHolder;
    public String cat = "Murzik";

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        loginHolder = LoginHolder.getInstance(getApplication());
        //loginHolder.loginUser(1);


        if(loginHolder.isLogged()){

            loginObserve.postValue(true);
        }else{
            loginObserve.postValue(false);
        }

    }


    public void logoutUser(){
        loginHolder.logoutUser();
        loginObserve.postValue(false);
    }



}
