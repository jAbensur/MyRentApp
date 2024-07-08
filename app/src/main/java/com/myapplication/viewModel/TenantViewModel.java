package com.myapplication.viewModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.myapplication.MainActivity;
import com.myapplication.repository.DbRepository;

public class TenantViewModel extends DbRepository {

    Context context;

    public TenantViewModel(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    /*
    Tenant
- TnID (PK)
- TnFirstName
- TnLastName
- TnEmail
- TnPhone
- TnDNI
- TnStatus
- TnType
- TnGender
     */
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

}
