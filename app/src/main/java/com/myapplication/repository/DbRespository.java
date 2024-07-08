package com.myapplication.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbRespository extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "rent.db";
    public static final String TABLE_TENANT = "t_Tenant";

    public DbRespository(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TENANT + "(" +
                "TnID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TnFirstName TEXT NOT NULL," +
                "TnLastName TEXT NOT NULL," +
                "TnEmail TEXT NOT NULL," +
                "TnPhone TEXT NOT NULL," +
                "TnDNI TEXT NOT NULL," +
                "TnStatus TEXT NOT NULL," +
                "TnType TEXT NOT NULL," +
                "TnGender TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_TENANT);
        onCreate(sqLiteDatabase);
    }
}
