package com.example.trackrecorder.database.models;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.trackrecorder.helpers.BitmapTypeConverter;

@Entity(tableName = "users", indices = {
        @Index(value = { "email" }, unique = true)
})
public class UserModel {

    @PrimaryKey(autoGenerate = true)
    int id;

    String email;
    String password;

    @TypeConverters({BitmapTypeConverter.class})
    Bitmap avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }
}
