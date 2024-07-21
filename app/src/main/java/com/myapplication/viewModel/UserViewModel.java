package com.myapplication.viewModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
            showToast("Error al insertar registro de usuario" + ex);
            Log.i(TAG, "Error al insertar registro de usuario" + ex);

            ex.toString();
        }

        return UsrID;
    }

    public void getAllUsers() {
        try {
            DbRepository dbRepository = new DbRepository(context);
            SQLiteDatabase db = dbRepository.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);
            if (cursor.moveToFirst()) {
                do {
                    int UsrID = cursor.getInt(0);
                    String UsrFirstName = cursor.getString(1);
                    String UsrLastName = cursor.getString(2);
                    String UsrName = cursor.getString(3);
                    String UsrPassword = cursor.getString(4);
                    String UsrEmail = cursor.getString(5);

                    Log.i(TAG, "User ID: " + UsrID + ", First Name: " + UsrFirstName +
                            ", Last Name: " + UsrLastName + ", Username: " + UsrName +
                            ", Password: " + UsrPassword + ", Email: " + UsrEmail + "\n");
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception ex) {
            Log.i(TAG, "Error al obtener registros de usuarios" + ex);
        }
    }

    public boolean login(String UsrEmail, String UsrPassword) {
        boolean loginSuccessful = false;

        try {
            DbRepository dbRepository = new DbRepository(context);
            SQLiteDatabase db = dbRepository.getReadableDatabase();

            String query = "SELECT * FROM " + TABLE_USER + " WHERE UsrEmail = ? AND UsrPassword = ?";
            Cursor cursor = db.rawQuery(query, new String[]{UsrEmail, UsrPassword});

            if (cursor.moveToFirst()) {
                loginSuccessful = true;
            }

            cursor.close();
        } catch (Exception ex) {
            Log.i(TAG, "Error al realizar login: " + ex);
            ex.printStackTrace();
        }

        return loginSuccessful;
    }

    public boolean deleteUser(int UsrID) { // eliminacion fisica

        boolean isDelete = false;

        DbRepository dbRespository = new DbRepository(context);
        SQLiteDatabase db = dbRespository.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_USER + " WHERE UsrID = '" + UsrID + "'");
            isDelete = true;
        } catch (Exception ex) {
            Toast.makeText(context, "Error al eliminar registro: " + ex.toString(), Toast.LENGTH_LONG).show();
            isDelete = false;
        } finally {
            db.close();
        }
        return isDelete;
    }

    public boolean updateUser(int UsrID, String UsrFirstName, String UsrLastName, String UsrName,
                                String UsrPassword, String UsrEmail) {

        boolean isUpdated = false;

        DbRepository dbRepository = new DbRepository(context);
        SQLiteDatabase db = dbRepository.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("UsrFirstName", UsrFirstName);
            values.put("UsrLastName", UsrLastName);
            values.put("UsrName", UsrName);
            values.put("UsrPassword", UsrPassword);
            values.put("UsrEmail", UsrEmail);

            String whereClause = "UsrID=?";
            String[] whereArgs = {String.valueOf(UsrID)};

            int rowsAffected = db.update(TABLE_USER, values, whereClause, whereArgs);

            if (rowsAffected > 0) {
                isUpdated = true;
            } else {
                showToast("Error al actualizar registro");
            }
        } catch (Exception ex) {
            showToast("Error al actualizar registro: " + ex.toString());
        } finally {
            db.close();
        }

        return isUpdated;
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
