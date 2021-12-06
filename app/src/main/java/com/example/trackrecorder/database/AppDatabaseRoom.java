package com.example.trackrecorder.database;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.trackrecorder.database.models.UserModel;


@SuppressLint("RestrictedApi")
@Database(entities = {UserModel.class, }, version = 1)
public abstract class AppDatabaseRoom extends RoomDatabase {

    @NonNull
    public abstract UserDao getUserDao();


   public static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.d("db-migrate", "success");
//            database.execSQL("DROP TABLE `movies`");
//            database.execSQL("CREATE TABLE IF NOT EXISTS `movies` ( " +
//                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT," +
//                    "`title` TEXT," +
//                    "`year` TEXT," +
//                    "`posterUrl` TEXT," +
//                    "`userId` INTEGER, FOREIGN KEY (`userId`) REFERENCES `users`(`id`) ON DELETE CASCADE ) ");
        }
    };

}
