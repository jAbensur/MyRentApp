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
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import com.myapplication.R;
import com.myapplication.model.Chamber;
import com.myapplication.model.Rent;
import com.myapplication.model.Tenant;
import com.myapplication.viewmodel.ChamberViewModel;
import com.myapplication.viewmodel.RentViewModel;
import com.myapplication.viewmodel.TenantViewModel;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class RentCreateActivity extends AppCompatActivity {
    private EditText etStartDate, etEndDate, etPrice;
    private TextView tvTitle;
    private Button btnCancel, btnRegister;
    private TenantViewModel tenantViewModel;
    private ChamberViewModel chamberViewModel;
    private RentViewModel rentViewModel;
    private Spinner tenantSpinner;
    private Spinner chamberSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_create);

        EditText editTextFilterTenant = findViewById(R.id.searchTenant);
        EditText editTextFilterChamber = findViewById(R.id.searchChamber);
        tenantSpinner = findViewById(R.id.spinnerTenants);
        chamberSpinner = findViewById(R.id.spinnerChambers);

        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        etPrice = findViewById(R.id.etPrice);
        btnCancel = findViewById(R.id.btnCancel);
        btnRegister = findViewById(R.id.btnRegister);
        tvTitle = findViewById(R.id.tvTitle);

        tenantViewModel = new ViewModelProvider(this).get(TenantViewModel.class);
        chamberViewModel = new ViewModelProvider(this).get(ChamberViewModel.class);
        rentViewModel = new ViewModelProvider(this).get(RentViewModel.class);

        try {

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
                        chamberSpinner.setAdapter(adapter);
                    }
                }
            });

            tvTitle.setText("Registrar Alquiler");

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String currentDate = dateFormat.format(calendar.getTime());

            etStartDate.setText(currentDate);
            etEndDate.setText(currentDate);
            etPrice.setText("0");
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
                        chamberSpinner.setAdapter(adapter);
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
        String startDate = etStartDate.getText().toString();
        String endDate = etEndDate.getText().toString();
        String price = etPrice.getText().toString();
        Tenant selectedTenant = (Tenant) tenantSpinner.getSelectedItem();
        Chamber selectedChamber = (Chamber) chamberSpinner.getSelectedItem();

        if(isValidInputs(startDate, endDate, price, selectedTenant.getId(), selectedChamber.getId())){
            message("Success");
            rentViewModel.insert(new Rent(startDate,endDate,Double.parseDouble(price),
                    selectedTenant.getId(), selectedChamber.getId()));
            finish();
        }
    }

    private boolean isValidInputs(String startDate, String endDate, String price, int idTenant,
                                  int idChamber){
        if(startDate.isEmpty()){
            message("Ingrese la fecha inicial");
            return false;
        }else if(endDate.isEmpty()){
            message("Ingrese la fecha final");
            return false;
        }else if(price.isEmpty()){
            message("Ingrese el monto");
            return false;
        }else if(idTenant == -1){
            message("Seleccione un inquilino");
            return false;
        }else if(idChamber == -1){
            message("Seleccione una habitación");
            return false;
        }else if(!isValidDate(startDate)){
            message("Ingrese la fecha inicial con formato dd-MM-yyyy");
            return false;
        }else if(!isValidDate(endDate)){
            message("Ingrese la fecha final con formato dd-MM-yyyy");
            return false;
        }else if(Double.parseDouble(price) <= 0){
            message("El monto debe ser mayor a 0");
            return false;
        }
        return true;
    }

    private void message(String text){
        Toast.makeText(RentCreateActivity.this, text, Toast.LENGTH_LONG).show();
    }

    private static boolean isValidDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);

        try {
            Date dateFormat = sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}