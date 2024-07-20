package com.myapplication.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.myapplication.model.PropertyDao;
import com.myapplication.model.PropertyModel;
import com.myapplication.model.RoomDao;
import com.myapplication.model.RoomModel;
import com.myapplication.model.User;
import com.myapplication.model.UserDao;

@androidx.room.Database(entities = {
        User.class,
        PropertyModel.class,
        RoomModel.class
        },
        exportSchema = false,
        version = 3)
public abstract class Database extends RoomDatabase {
    private static Database instance;

    public abstract UserDao userDao();
    public abstract PropertyDao propertyDao();
    public abstract RoomDao roomDao();

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
