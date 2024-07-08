package com.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.adapter.ListTenantAdapter;
import com.myapplication.model.Tenant;
import com.myapplication.repository.DbRepository;
import com.myapplication.viewModel.TenantViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView listTenant;
    ArrayList<Tenant> listArrayTenant;

    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoRegistro();
            }
        });

        listTenant = findViewById(R.id.listTenant);
        listTenant.setLayoutManager(new LinearLayoutManager(this));

        TenantViewModel tenantViewModel = new TenantViewModel(MainActivity.this);
        listArrayTenant = new ArrayList<>();

        ListTenantAdapter adapter = new ListTenantAdapter(tenantViewModel.mostrarContactos());
        listTenant.setAdapter(adapter);

    }

    private void nuevoRegistro(){
        Intent intent = new Intent(this, NewTenantActivity.class);
        startActivity(intent);
    }

}