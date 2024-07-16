package com.myapplication.repository;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.myapplication.model.Chamber;
import com.myapplication.model.Rent;
import com.myapplication.model.Tenant;
import com.myapplication.model.User;

@androidx.room.Database(entities = {User.class, Tenant.class, Chamber.class, Rent.class}, version = 1)
public abstract class Database extends RoomDatabase {
    private static Database instance;
    public abstract UserDao userDao();
    public abstract TenantDao tenantDao();
    public abstract ChamberDao chamberDao();
    public abstract RentDao rentDao();
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
