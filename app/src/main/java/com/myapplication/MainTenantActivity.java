package com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapplication.adapter.ListTenantAdapter;
import com.myapplication.model.Tenant;
import com.myapplication.viewModel.TenantViewModel;

import java.util.ArrayList;

public class MainTenantActivity extends AppCompatActivity {

    RecyclerView listTenant;
    ArrayList<Tenant> tenantArrayList;
    FloatingActionButton fabRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tenant);

        fabRegister = findViewById(R.id.fabRegister);
        fabRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newRegistration();
            }
        });

        listTenant = findViewById(R.id.listTenant);
        listTenant.setLayoutManager(new LinearLayoutManager(this));

        TenantViewModel tenantViewModel = new TenantViewModel(MainTenantActivity.this);
        tenantArrayList = new ArrayList<>();

        ListTenantAdapter listTenantAdapter = new ListTenantAdapter(tenantViewModel.mostrarContactos());
        listTenant.setAdapter(listTenantAdapter);
    }

    private void newRegistration(){
        Intent intent = new Intent(this, NewTenantActivity.class);
        startActivity(intent);
    }

}