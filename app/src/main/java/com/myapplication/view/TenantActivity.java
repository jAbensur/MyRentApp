package com.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapplication.R;
import com.myapplication.model.Tenant;
import com.myapplication.viewmodel.TenantViewModel;
import java.util.List;

public class TenantActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fabRegister;
    TenantViewModel tenantViewModel;
    TenantAdapter tenantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tenant);

        fabRegister = findViewById(R.id.fabRegister);
        recyclerView = findViewById(R.id.listTenant);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tenantViewModel =  new ViewModelProvider(this).get(TenantViewModel.class);

        tenantViewModel.getAllTenants().observe(this, new Observer<List<Tenant>>() {
            @Override
            public void onChanged(List<Tenant> tenants) {
                tenantAdapter = new TenantAdapter(tenants);
                recyclerView.setAdapter(tenantAdapter);
            }
        });

        fabRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newRegistration();
            }
        });
    }

    private void newRegistration(){
        Intent intent = new Intent(this, TenantAddActivity.class);
        startActivity(intent);
    }

}