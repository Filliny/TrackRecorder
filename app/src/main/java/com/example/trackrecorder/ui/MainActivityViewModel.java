package com.example.trackrecorder.ui;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.trackrecorder.App;
import com.example.trackrecorder.R;
import com.example.trackrecorder.database.SharedLoginStorage;
import com.example.trackrecorder.database.UserRepository;
import com.example.trackrecorder.database.models.UserModel;
import com.example.trackrecorder.services.LocationRecordService;


public class MainActivityViewModel extends AndroidViewModel {

    UserRepository userRepository;
    UserModel currentUser;
    SharedLoginStorage sharedLogin;
    Intent intentLocation;
    private final String SERVICE_INTENT_DATA_NAME = "userId";

    MutableLiveData<Boolean> loginObserve = new MutableLiveData<>();
    ObservableField<UserModel> userObserve = new ObservableField<>();
    ObservableBoolean showFloatingButton = new ObservableBoolean();
    ObservableBoolean recordState = new ObservableBoolean();

    MutableLiveData<Boolean> globalRecordState = new MutableLiveData<>();
    MutableLiveData<Bitmap> globalAvatar = new MutableLiveData<>();


    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        globalRecordState.setValue(false);

        userRepository = App.getInstance().getUserRepository();
        sharedLogin = SharedLoginStorage.getInstance(getApplication());

        if (sharedLogin.isLogged()) {
            setCurrentUser(userRepository.getUser(sharedLogin.getId()));
            loginObserve.postValue(true);

        } else {
            loginObserve.postValue(false);
        }

    }

    public UserModel getCurrentUser() {
        return currentUser;
    }

    public MutableLiveData<Boolean> getLoginObserve() {
        return loginObserve;
    }

    public ObservableField<UserModel> getUserObserve() {
        return userObserve;
    }

    public MutableLiveData<Bitmap> getGlobalAvatar() {
        return globalAvatar;
    }

    public ObservableBoolean getRecordState() {
        return recordState;
    }

    public ObservableBoolean getShowFloatingButton() {
        return showFloatingButton;
    }

    public MutableLiveData<Boolean> getGlobalRecordState() {
        return globalRecordState;
    }

    public void logoutUser() {
        sharedLogin.logoutUser();
        setCurrentUser(null);
        loginObserve.postValue(false);
    }

    public void loginUser(UserModel user) {
        sharedLogin.loginUser(user.getId());
        setCurrentUser(user);
        loginObserve.postValue(true);

    }

    private void setCurrentUser(UserModel user) {
        this.currentUser = user;
        userObserve.set(user);
    }

    public void setGlobalAvatar(Bitmap bitmap) {

        globalAvatar.postValue(bitmap);

        if (currentUser != null) {
            currentUser.setAvatar(bitmap);
            userObserve.set(currentUser);
            userObserve.notifyChange();
            userRepository.updateUser(currentUser);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void switchLocationService() {
        intentLocation = new Intent(getApplication(), LocationRecordService.class);
        intentLocation.putExtra(SERVICE_INTENT_DATA_NAME, currentUser.getId());

        if (recordState.get()) {
            getApplication().stopService(intentLocation);
            getRecordState().set(false);
            globalRecordState.postValue(false);
            globalRecordState.setValue(false);

            Toast.makeText(getApplication(), getApplication().getText(R.string.toast_record_stopped)
                    , Toast.LENGTH_LONG).show();
        } else {
            getApplication().startForegroundService(intentLocation);
            getRecordState().set(true);
            getGlobalRecordState().postValue(true);
            globalRecordState.setValue(true);

            Toast.makeText(getApplication(),  getApplication().getText(R.string.toat_record_started),
                    Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCleared() {
        getApplication().stopService(intentLocation);
        super.onCleared();
    }


}
