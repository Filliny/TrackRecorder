package com.example.trackrecorder.database;

import android.annotation.SuppressLint;

import com.example.trackrecorder.database.models.PointModel;
import com.example.trackrecorder.helpers.DateTimeConverter;

import java.util.Date;
import java.util.List;

public class PointsRepository {

    private static PointsRepository instance;

    AppDatabaseRoom db;

    @SuppressLint("UnsafeOptInUsageError")
    private PointsRepository(AppDatabaseRoom databaseContext) {
        this.db = databaseContext;
    }

    public static PointsRepository getInstance(AppDatabaseRoom databaseContext) {

        if (instance == null) instance = new PointsRepository(databaseContext);
        return instance;
    }

    public void addPoint(PointModel point) {

        db.getPointDao().insert(point);
    }

    public List<PointModel> getPointsList(int userId, Date date) {

        String dateS = DateTimeConverter.getFormattedDate(date);
        return db.getPointDao().getDayPoints(userId, dateS);
    }

}
