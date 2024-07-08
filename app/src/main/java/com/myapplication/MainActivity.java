package com.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.myapplication.repository.DbRespository;

public class MainActivity extends AppCompatActivity {

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

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbRespository dbRespository = new DbRespository(MainActivity.this);
                SQLiteDatabase db = dbRespository.getWritableDatabase();
                if (db != null){
                    Toast.makeText(MainActivity.this, "base de datos creada", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "error al crear base de datos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}