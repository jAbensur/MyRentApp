package com.myapplication.repository;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.myapplication.model.Chamber;
import com.myapplication.model.Rent;
import com.myapplication.model.Tenant;

import com.myapplication.model.PropertyDao;
import com.myapplication.model.PropertyModel;
import com.myapplication.model.User;
import com.myapplication.model.UserDao;

@androidx.room.Database(entities = {User.class, Tenant.class, Chamber.class, Rent.class, User.class,
        PropertyModel.class},exportSchema = false, version = 1)
public abstract class Database extends RoomDatabase {
    private static Database instance;
    public abstract UserDao userDao();
    public abstract TenantDao tenantDao();
    public abstract ChamberDao chamberDao();
    public abstract RentDao rentDao();
    public abstract PropertyDao propertyDao();

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
