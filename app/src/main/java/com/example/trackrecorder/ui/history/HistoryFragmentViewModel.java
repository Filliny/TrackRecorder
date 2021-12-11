package com.example.trackrecorder.ui.history;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.trackrecorder.App;
import com.example.trackrecorder.database.models.PointModel;
import com.example.trackrecorder.database.models.UserModel;
import com.example.trackrecorder.helpers.DateTimeConverter;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoryFragmentViewModel extends AndroidViewModel {

    UserModel currentUser;
    MutableLiveData<String> dateLabel = new MutableLiveData<>();
    MutableLiveData<List<LatLng>> trackList = new MutableLiveData<>();
    Date dateCurrent;


    public HistoryFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void setCurrentUser(UserModel currentUser) {
        this.currentUser = currentUser;
    }

    public MutableLiveData<List<LatLng>> getTrackList() {
        return trackList;
    }

    public MutableLiveData<String> getDateLabel() {
        return dateLabel;
    }


    public void setDateSelected(Date date) {

        dateCurrent = date;

        setDateLabel(date);

        List<LatLng> resultList = new ArrayList<>();

        List<PointModel> pointsList = App.getInstance()
                .getPointsRepository().getPointsList(currentUser.getId(), date);

        pointsList.forEach(pointModel -> {

            resultList.add(new LatLng(pointModel.getLatitude(), pointModel.getLongitude()));

        });

            trackList.postValue(resultList);

    }

    private void setDateLabel(Date date) {

        String dateLabelValue = DateTimeConverter.getFormattedDate(date);
        dateLabel.postValue(dateLabelValue);
    }

    public void previousDate(View view){
        switchDate(-1);
    }

    public void nextDate(View view){
       switchDate(1);
    }

    private void switchDate(int amount){

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateCurrent);
        cal.add(Calendar.DAY_OF_YEAR,amount);
        Date resultDate = cal.getTime();

        setDateSelected(resultDate);
    }

}
