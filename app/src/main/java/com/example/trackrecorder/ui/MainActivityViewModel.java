package com.example.trackrecorder.ui;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.trackrecorder.App;
import com.example.trackrecorder.database.SharedLoginStorage;
import com.example.trackrecorder.database.UserRepository;
import com.example.trackrecorder.database.models.UserModel;


public class MainActivityViewModel extends AndroidViewModel {

    UserRepository userRepository;
    UserModel currentUser;
    SharedLoginStorage sharedLogin;

    MutableLiveData<Boolean> loginObserve = new MutableLiveData<>();
    ObservableField<UserModel> userObserve = new ObservableField<>();
    ObservableBoolean showFloatingButton = new ObservableBoolean();
    ObservableBoolean recordState = new ObservableBoolean();

    public MutableLiveData<Boolean> getGlobalRecordState() {
        return globalRecordState;
    }

    MutableLiveData<Boolean> globalRecordState = new MutableLiveData<>();
    MutableLiveData<Bitmap> globalAvatar = new MutableLiveData<>();


    public MainActivityViewModel(@NonNull Application application) {
        super(application);

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

}
