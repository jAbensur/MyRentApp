package com.myapplication.viewModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.myapplication.model.Tenant;
import com.myapplication.repository.DbRepository;

import java.util.ArrayList;

public class TenantViewModel extends DbRepository {

    Context context;

    public TenantViewModel(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertTenant(String TnFirstName, String TnLastName, String TnEmail
            , String TnPhone, String TnDNI, String TnStatus,String TnType, String TnGender) {

        long TnID = 0;

        try {
            DbRepository dbRespository = new DbRepository(context);
            SQLiteDatabase db = dbRespository.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("TnFirstName", TnFirstName);
            values.put("TnLastName", TnLastName);
            values.put("TnEmail", TnEmail);
            values.put("TnPhone", TnPhone);
            values.put("TnDNI", TnDNI);
            values.put("TnStatus", TnStatus);
            values.put("TnType", TnType);
            values.put("TnGender", TnGender);

            TnID = db.insert(TABLE_TENANT, null, values);
        } catch (Exception ex) {
            Toast.makeText(context, "Error al insertar registro", Toast.LENGTH_LONG).show();
            ex.toString();
        }

        return TnID;
    }

    public ArrayList<Tenant> mostrarContactos() { // aca hay que cambiar de nombre a 'getAllTenants'
        DbRepository dbHelper = new DbRepository(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Tenant> tenantList = new ArrayList<>();
        Tenant tenant = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TENANT, null);

        if (cursor.moveToFirst()) {
            do {
                tenant = new Tenant();
                tenant.setTnID(cursor.getInt(0));
                tenant.setTnFirstName(cursor.getString(1));
                tenant.setTnLastName(cursor.getString(2));
                tenant.setTnEmail(cursor.getString(3));
                tenant.setTnPhone(cursor.getString(4));
                tenant.setTnDNI(cursor.getString(5));
                tenant.setTnStatus(cursor.getString(6));
                tenant.setTnType(cursor.getString(7));
                tenant.setTnGender(cursor.getString(8));

                tenantList.add(tenant);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return tenantList;
    }

    public Tenant getTenantById(int TnID) {
        DbRepository dbRepository = new DbRepository(context);
        SQLiteDatabase db = dbRepository.getWritableDatabase();

        Tenant tenant = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TENANT + " WHERE TnID = " + TnID + " LIMIT 1", null);

        if (cursor.moveToFirst()) {
            tenant = new Tenant();
            tenant.setTnID(cursor.getInt(0));
            tenant.setTnFirstName(cursor.getString(1));
            tenant.setTnLastName(cursor.getString(2));
            tenant.setTnEmail(cursor.getString(3));
            tenant.setTnPhone(cursor.getString(4));
            tenant.setTnDNI(cursor.getString(5));
            tenant.setTnStatus(cursor.getString(6));
            tenant.setTnType(cursor.getString(7));
            tenant.setTnGender(cursor.getString(8));
        }
        cursor.close();

        return tenant;
    }

    public boolean updateTenant(int TnID, String TnFirstName, String TnLastName, String TnEmail
            , String TnPhone, String TnDNI, String TnStatus,String TnType, String TnGender) {

        boolean isUpdated = false;

        DbRepository dbRespository = new DbRepository(context);
        SQLiteDatabase db = dbRespository.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("TnFirstName", TnFirstName);
            values.put("TnLastName", TnLastName);
            values.put("TnEmail", TnEmail);
            values.put("TnPhone", TnPhone);
            values.put("TnDNI", TnDNI);
            values.put("TnStatus", TnStatus);
            values.put("TnType", TnType);
            values.put("TnGender", TnGender);

            String whereClause = "TnID=?";
            String[] whereArgs = {String.valueOf(TnID)};

            int rowsAffected = db.update(TABLE_TENANT, values, whereClause, whereArgs);

            if (rowsAffected > 0) {
                isUpdated = true;
            } else {
                Toast.makeText(context, "Error al actualizar registro", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(context, "Error al actualizar registro: " + ex.toString(), Toast.LENGTH_LONG).show();
        } finally {
            db.close();
        }
        return isUpdated;
    }

    public boolean deleteTenant(int TnID) {

        boolean isDelete = false;

        DbRepository dbRespository = new DbRepository(context);
        SQLiteDatabase db = dbRespository.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_TENANT + " WHERE TnID = '" + TnID + "'");
            isDelete = true;
        } catch (Exception ex) {
            Toast.makeText(context, "Error al actualizar registro: " + ex.toString(), Toast.LENGTH_LONG).show();
            isDelete = false;
        } finally {
            db.close();
        }
        return isDelete;
    }
}
