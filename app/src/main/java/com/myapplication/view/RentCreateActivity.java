package com.myapplication.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import com.myapplication.R;
import com.myapplication.model.Chamber;
import com.myapplication.model.Tenant;
import com.myapplication.viewmodel.ChamberViewModel;
import com.myapplication.viewmodel.TenantViewModel;
import java.util.List;
import java.util.stream.Collectors;

public class RentCreateActivity extends AppCompatActivity {
    private EditText etStartDate, etEndDate, etPrice;
    private Button btnCancel, btnRegister;
    private List<String> tenants;
    private LiveData<List<Tenant>> tenants2;
    private List<String> tenantsFilters;
    private ArrayAdapter<String> adapter;
    private TenantViewModel tenantViewModel;
    private ChamberViewModel chamberViewModel;
    private TenantSpinnerAdapter tenantSpinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_create);

        EditText editTextFilterTenant = findViewById(R.id.searchTenant);
        EditText editTextFilterChamber = findViewById(R.id.searchChamber);
        Spinner tenantSpinner = findViewById(R.id.spinnerTenants);
        Spinner ChamberSpinner = findViewById(R.id.spinnerChambers);

        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        etPrice = findViewById(R.id.etPrice);
        btnCancel = findViewById(R.id.btnCancel);
        btnRegister = findViewById(R.id.btnRegister);

        try {
            tenantViewModel = new ViewModelProvider(this).get(TenantViewModel.class);
            chamberViewModel = new ViewModelProvider(this).get(ChamberViewModel.class);
            //tenantViewModel.insert(new Tenant("Tenant 1"));
            //tenantViewModel.insert(new Tenant("Tenant 2"));
            //tenantViewModel.insert(new Tenant("Tenant 3"));
            //chamberViewModel.insert(new Chamber("Chamber 1"));
            //chamberViewModel.insert(new Chamber("Chamber 2"));
            //chamberViewModel.insert(new Chamber("Chamber 3"));

            tenantViewModel.getAllTenants().observe(this, new Observer<List<Tenant>>() {
                @Override
                public void onChanged(List<Tenant> tenants) {

                    if (tenants == null || tenants.isEmpty()) {
                        Toast.makeText(RentCreateActivity.this, "No hay inquilinos disponibles", Toast.LENGTH_LONG).show();
                    } else {
                        ArrayAdapter<Tenant> adapter = new ArrayAdapter<>(RentCreateActivity.this, android.R.layout.simple_spinner_item, tenants);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        tenantSpinner.setAdapter(adapter);
                    }
                }
            });
            chamberViewModel.getAllChambers().observe(this, new Observer<List<Chamber>>() {
                @Override
                public void onChanged(List<Chamber> chambers) {
                    if (chambers == null || chambers.isEmpty()) {
                        Toast.makeText(RentCreateActivity.this, "No hay habitaciones disponibles", Toast.LENGTH_LONG).show();
                    } else {
                        ArrayAdapter<Chamber> adapter = new ArrayAdapter<>(RentCreateActivity.this, android.R.layout.simple_spinner_item, chambers);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        ChamberSpinner.setAdapter(adapter);
                    }
                }
            });
        } catch (Exception e) {
            Log.e("MyRentalApp", "Error al inicializar ViewModel o insertar Tenant", e);
        }

        editTextFilterTenant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tenantViewModel.getTenants(s.toString()).observe(RentCreateActivity.this, new Observer<List<Tenant>>() {
                    @Override
                    public void onChanged(List<Tenant> tenants) {
                        ArrayAdapter<Tenant> adapter = new ArrayAdapter<>(RentCreateActivity.this, android.R.layout.simple_spinner_item, tenants);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        tenantSpinner.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        editTextFilterChamber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                chamberViewModel.getChambers(s.toString()).observe(RentCreateActivity.this, new Observer<List<Chamber>>() {
                    @Override
                    public void onChanged(List<Chamber> chambers) {
                        ArrayAdapter<Chamber> adapter = new ArrayAdapter<>(RentCreateActivity.this, android.R.layout.simple_spinner_item, chambers);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        ChamberSpinner.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelRegistration();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerRental();
            }
        });
    }
    private void cancelRegistration() {
        finish();
    }

    private void registerRental() {
        // Implementar lógica para registrar la renta
        String startDate = etStartDate.getText().toString();
        String endDate = etEndDate.getText().toString();
        String price = etPrice.getText().toString();

        // Validar y guardar los datos de la renta
        // ...
    }
}