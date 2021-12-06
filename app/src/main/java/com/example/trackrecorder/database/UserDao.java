package com.example.trackrecorder.database;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.trackrecorder.database.models.UserModel;

import java.util.List;

/**
 * Created by Oleh Filipchuk ( olegf.droid@gmail.com ) on 06.12.2021.
 */
@Dao
public interface UserDao {

    @NonNull
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(@NonNull UserModel user);

    @NonNull
    @Delete
    int  delete(@NonNull UserModel user);

    @NonNull
    @Update
    int update(@NonNull UserModel user);

    @NonNull
    @Query("SELECT * FROM users")
    List<UserModel> getAllUsers();

    @NonNull
    @Query("SELECT * FROM users WHERE users.id = :id")
    UserModel getUser(int id);

    @NonNull
    @Query("SELECT * FROM users WHERE email = :email")
    UserModel getExistUser(String email);

    @NonNull
    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    UserModel getAuthUser(String email, String password);

}
