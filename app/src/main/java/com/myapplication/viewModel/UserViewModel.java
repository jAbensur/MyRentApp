package com.myapplication.viewModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.myapplication.LoginActivity;
import com.myapplication.repository.DbRepository;

public class UserViewModel extends DbRepository {
    private static final String TAG = "UserViewModel";

    Context context;

    public UserViewModel(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertUser(String UsrFirstName, String UsrLastName, String UsrName,
                           String UsrPassword, String UsrEmail) {
        long UsrID = 0;

        try {
            DbRepository dbRepository = new DbRepository(context);
            SQLiteDatabase db = dbRepository.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("UsrFirstName", UsrFirstName);
            values.put("UsrLastName", UsrLastName);
            values.put("UsrName", UsrName);
            values.put("UsrPassword", UsrPassword);
            values.put("UsrEmail", UsrEmail);

            UsrID = db.insert(TABLE_USER, null, values);
        } catch (Exception ex) {
//            showToast("Error al insertar registro de usuario" + ex);
            Log.i(TAG, "Error al insertar registro de usuario" + ex);

            ex.toString();
        }

        return UsrID;
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
