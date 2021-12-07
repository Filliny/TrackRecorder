package com.example.trackrecorder.ui.login;

import android.app.Application;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.trackrecorder.App;
import com.example.trackrecorder.R;
import com.example.trackrecorder.database.UserRepository;
import com.example.trackrecorder.database.models.ErrorModel;
import com.example.trackrecorder.database.models.UserModel;

import org.xml.sax.ErrorHandler;

public class LoginFragmentViewModel extends AndroidViewModel {

    public UserModel userRequest;
    ErrorModel errorHolder;
    UserRepository userRepository;

    MutableLiveData<UserModel> userToWorkWith = new MutableLiveData<>();
    ObservableField<ErrorModel> errorObservable = new ObservableField<>();

    public LoginFragmentViewModel(@NonNull Application application) {
        super(application);
        userRequest = new UserModel();
        userRequest.setEmail("olegfil@gmail.com");
        userRequest.setPassword("12345");
        userRepository = App.getInstance().getUserRepository();
    }

    public ObservableField<ErrorModel> getErrorObservable() {
        return errorObservable;
    }

    public MutableLiveData<UserModel> getUserToWorkWith() {
        return userToWorkWith;
    }

    public void loginButtonPress() {

        if (!isLoginFormValid()) return;
        //cos userRequest have no id - we get proper user from base
        UserModel workUser = userRepository.getUserFromRequest(userRequest);
        userToWorkWith.postValue(workUser);

    }

    private boolean isLoginFormValid() {

        boolean result = true;
        errorHolder = new ErrorModel();

        if (userRequest.getEmail() == null) {
            errorHolder.emailError = "Enter Your Email";
            result = false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userRequest.getEmail()).matches()) {
            errorHolder.emailError = "Enter Valid Email";
            result = false;
        }

        if (userRequest.getPassword() == null) {
            errorHolder.passwordError = "Enter Your Password";
            result = false;
        }

        if (userRequest.getPassword() != null && userRequest.getEmail() != null) {

            if (!userRepository.isExist(userRequest.getEmail())) {
                //check for existence of user
                errorHolder.passwordError = "No Such User - Please register!";
                errorHolder.emailError = "No Such User - Please register!";
                result = false;
            } else {
                //if user exist check for pass and email validity
                if (userRepository.getUserFromRequest(userRequest) == null) {
                    errorHolder.passwordError = "Wrong password - try again";
                    result = false;
                }
            }

        }

        if (!result) errorObservable.set(errorHolder);
        return result;
    }

}
