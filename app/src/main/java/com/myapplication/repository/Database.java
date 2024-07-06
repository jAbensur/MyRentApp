package com.myapplication.repository;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.buendia.myrentalapp.model.User;

@androidx.room.Database(entities = {User.class}, version = 1)
public abstract class Database extends RoomDatabase {
    private static Database instance;

    public abstract UserDao userDao();

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, "database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
