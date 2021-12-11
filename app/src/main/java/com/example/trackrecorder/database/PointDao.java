package com.example.trackrecorder.database;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.trackrecorder.database.models.PointModel;


import java.util.List;

@Dao
public interface PointDao {

    @NonNull
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(@NonNull PointModel point);

    @Query("SELECT * FROM points WHERE points.userId = :userId  AND points.create_date = :createDate")
    List<PointModel> getDayPoints(int userId, String createDate);

}

