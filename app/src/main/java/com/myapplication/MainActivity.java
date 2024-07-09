package com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.adapter.ListTenantAdapter;
import com.myapplication.model.Tenant;
import com.myapplication.viewModel.TenantViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView listTenant;
    ArrayList<Tenant> tenantArrayList;

    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newRegistration();
            }
        });

        listTenant = findViewById(R.id.listTenant);
        listTenant.setLayoutManager(new LinearLayoutManager(this));

        TenantViewModel tenantViewModel = new TenantViewModel(MainActivity.this);
        tenantArrayList = new ArrayList<>();

        ListTenantAdapter adapter = new ListTenantAdapter(tenantViewModel.mostrarContactos());
        listTenant.setAdapter(adapter);
    }

    private void newRegistration(){
        Intent intent = new Intent(this, NewTenantActivity.class);
        startActivity(intent);
    }

}