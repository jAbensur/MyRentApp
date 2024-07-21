package com.myapplication.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.myapplication.model.Chamber;
import com.myapplication.model.Property;
import com.myapplication.model.Rent;
import com.myapplication.model.Tenant;
import com.myapplication.model.User;
import com.myapplication.repository.PropertyDao;
import com.myapplication.repository.UserDao;
import com.myapplication.repository.TenantDao;
import com.myapplication.repository.ChamberDao;
import com.myapplication.repository.RentDao;

@androidx.room.Database(entities = {User.class, Tenant.class, Chamber.class, Rent.class,
        Property.class}, exportSchema = false, version = 4)
public abstract class Database extends RoomDatabase {
    private static Database instance;
    private static Context appContext;

    public abstract UserDao userDao();
    public abstract TenantDao tenantDao();
    public abstract ChamberDao chamberDao();
    public abstract RentDao rentDao();
    public abstract PropertyDao propertyDao();

    // insertar el usuario predeterminado al crear la base de datos
    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Run in a background thread
            new Thread(() -> {
                // Get the database instance
                Database database = getInstance(appContext);
                // Create a new user
                User user = new User("Togata", "ruselcucho@gmail.com", "admin");
                // Insert the user into the database
                database.userDao().insert(user);
            }).start();
        }
    };

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, "database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomDatabaseCallback)  // Add the callback here
                    .build();
        }
        return instance;
    }
}
