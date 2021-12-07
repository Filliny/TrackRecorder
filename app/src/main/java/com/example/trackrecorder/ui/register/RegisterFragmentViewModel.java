package com.example.trackrecorder.ui.register;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.trackrecorder.App;
import com.example.trackrecorder.R;
import com.example.trackrecorder.database.UserRepository;
import com.example.trackrecorder.database.models.ErrorModel;
import com.example.trackrecorder.database.models.UserModel;

/**
 * Created by Oleh Filipchuk ( olegf.droid@gmail.com ) on 06.12.2021.
 */

public class RegisterFragmentViewModel extends AndroidViewModel {


    @NonNull
    public UserModel userCandidate;
    ErrorModel errorHolder;
    UserRepository userRepository;

    ObservableField<ErrorModel> errorObservable = new ObservableField<>();
    ObservableBoolean avatarHintVisibility = new ObservableBoolean(true);
    ObservableField<UserModel> observableUser = new ObservableField<>();

    MutableLiveData<UserModel> userToWorkWith = new MutableLiveData<>();

    public RegisterFragmentViewModel(@NonNull Application application) {
        super(application);
        userCandidate = new UserModel();
        userCandidate.setAvatar(BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.tr_default_image));
        observableUser.set(userCandidate);
        userRepository = App.getInstance().getUserRepository();
    }

    public ObservableField<ErrorModel> getErrorObservable() {
        return errorObservable;
    }

    public MutableLiveData<UserModel> getUserToWorkWith() {
        return userToWorkWith;
    }

    public ObservableField<UserModel> getObservableUser() {
        return observableUser;
    }

    public ObservableBoolean getAvatarHintVisibility() {
        return avatarHintVisibility;
    }


    public void registerButtonPress() {
        if (!isRegFormValidated()) return;

        userRepository.addUser(userCandidate);
        UserModel workUser = userRepository.getUserFromRequest(userCandidate);
        userToWorkWith.postValue(workUser);
    }

    private boolean isRegFormValidated() {

        boolean result = true;
        errorHolder = new ErrorModel();

        if (userCandidate.getEmail() == null) {
            errorHolder.emailError = "Enter Your Email";
            result = false;

        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userCandidate.getEmail()).matches()) {

            errorHolder.emailError = "Enter Valid Email";
            result = false;
        }

        if (userCandidate.getPassword() == null) {
            errorHolder.passwordError = "Enter Your Password";
            result = false;
        }

        if (userCandidate.getPassword() != null && userCandidate.getEmail() != null) {

            if (userRepository.isExist(userCandidate.getEmail())) {
                //check for existence of email
                errorHolder.passwordError = "Email Already Registered - Proceed to login !";
                errorHolder.emailError = "Email Already Registered - Proceed to login !";
                result = false;
            }

            //if user exist with password and email
            if (userRepository.getUserFromRequest(userCandidate) != null) {
                errorHolder.emailError = "User Already Registered - Proceed to login !";
                errorHolder.passwordError = "User Already Registered - Proceed to login !";
                result = false;
            }

        }
        if (!result) errorObservable.set(errorHolder);
        return result;

    }

    public void postAvatar(Bitmap bitmap) {
        userCandidate.setAvatar(bitmap);
        observableUser.set(userCandidate);
        observableUser.notifyChange();
        avatarHintVisibility.set(false);
    }

}
