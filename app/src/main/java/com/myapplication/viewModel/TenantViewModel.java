package com.myapplication.viewModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.myapplication.MainActivity;
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

        long id = 0;

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

            id = db.insert(TABLE_TENANT, null, values);
        } catch (Exception ex) {
            Toast.makeText(context, "Error al insertar registro", Toast.LENGTH_LONG).show();
            ex.toString();
        }

        return id;
    }

    public ArrayList<Tenant> mostrarContactos() {
        DbRepository dbHelper = new DbRepository(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Tenant> listaContactos = new ArrayList<>();
        Tenant tenant = null;
        Cursor cursorContactos = null;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_TENANT, null);

        if (cursorContactos.moveToFirst()) {
            do {
                tenant = new Tenant();
                tenant.setTnID(cursorContactos.getInt(0));
                tenant.setTnFirstName(cursorContactos.getString(1));
                tenant.setTnLastName(cursorContactos.getString(2));
                tenant.setTnEmail(cursorContactos.getString(3));
                tenant.setTnPhone(cursorContactos.getString(4));
                tenant.setTnDNI(cursorContactos.getString(5));
                tenant.setTnStatus(cursorContactos.getString(6));
                tenant.setTnType(cursorContactos.getString(7));
                tenant.setTnGender(cursorContactos.getString(8));

                listaContactos.add(tenant);
            } while (cursorContactos.moveToNext());
        }
        cursorContactos.close();

        return listaContactos;
    }

}
