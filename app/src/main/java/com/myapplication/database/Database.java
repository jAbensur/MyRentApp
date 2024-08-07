package com.myapplication.database;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.myapplication.model.Property;
import com.myapplication.model.Rent;
import com.myapplication.model.Tenant;
import com.myapplication.model.User;
import com.myapplication.repository.PropertyDao;
import com.myapplication.repository.RoomDao;
import com.myapplication.repository.UserDao;
import com.myapplication.repository.TenantDao;
import com.myapplication.repository.RentDao;

@androidx.room.Database(entities = {User.class, Tenant.class, Rent.class,
        Property.class, com.myapplication.model.Room.class},exportSchema = false, version = 2)
public abstract class Database extends RoomDatabase {
    private static Database instance;
    public abstract UserDao userDao();
    public abstract TenantDao tenantDao();
    public abstract RentDao rentDao();
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
