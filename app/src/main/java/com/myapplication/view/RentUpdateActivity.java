package com.myapplication.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.myapplication.R;
import com.myapplication.model.Rent;
import com.myapplication.model.Room;
import com.myapplication.model.Tenant;
import com.myapplication.viewmodel.RentViewModel;
import com.myapplication.viewmodel.RoomViewModel;
import com.myapplication.viewmodel.TenantViewModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RentUpdateActivity extends AppCompatActivity {
    private EditText etStartDate, etEndDate, etPrice;
    private Button btnCancel, btnRegister;
    private TenantViewModel tenantViewModel;
    private RoomViewModel roomViewModel;
    private RentViewModel rentViewModel;
    private Spinner tenantSpinner, chamberSpinner;
    private int rentId;
    private Rent rent;
    private List<Tenant> tenantList;
    private List<Room> chamberList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_update);
        getSupportActionBar().setTitle("Actualizar Alquiler");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText editTextFilterTenant = findViewById(R.id.searchTenant);
        EditText editTextFilterChamber = findViewById(R.id.searchChamber);
        tenantSpinner = findViewById(R.id.spinnerTenants);
        chamberSpinner = findViewById(R.id.spinnerChambers);

        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        etPrice = findViewById(R.id.etPrice);
        btnCancel = findViewById(R.id.btnCancel);
        btnRegister = findViewById(R.id.btnRegister);

        rentId = getIntent().getIntExtra("id", -1);

        tenantViewModel = new ViewModelProvider(this).get(TenantViewModel.class);
        roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);
        rentViewModel = new ViewModelProvider(this).get(RentViewModel.class);

        tenantViewModel.getAllTenants().observe(this, new Observer<List<Tenant>>() {
            @Override
            public void onChanged(List<Tenant> tenants) {
                if (tenants == null || tenants.isEmpty()) {
                    Toast.makeText(RentUpdateActivity.this, "No hay inquilinos disponibles", Toast.LENGTH_LONG).show();
                } else {
                    tenantList = tenants;
                    ArrayAdapter<Tenant> adapter = new ArrayAdapter<>(RentUpdateActivity.this, android.R.layout.simple_spinner_item, tenants);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    tenantSpinner.setAdapter(adapter);
                    rentViewModel.getRentById(rentId).observe(RentUpdateActivity.this,new Observer<Rent>() {
                        @Override
                        public void onChanged(Rent rent) {
                            if (rent != null) {
                                etStartDate.setText(rent.getStartDate());
                                etEndDate.setText(rent.getEndDate());
                                etPrice.setText(String.valueOf(rent.getPrice()));
                                if (tenantList != null) {
                                    for (int i = 0; i < tenantList.size(); i++) {
                                        if (tenantList.get(i).getId() == rent.getTenantId()) {
                                            tenantSpinner.setSelection(i);
                                            break;
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(RentUpdateActivity.this, "Renta no encontrada", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

        });
        roomViewModel.getAllRooms().observe(this, new Observer<List<Room>>() {
            @Override
            public void onChanged(List<Room> chambers) {
                if (chambers == null || chambers.isEmpty()) {
                    Toast.makeText(RentUpdateActivity.this, "No hay habitaciones disponibles", Toast.LENGTH_LONG).show();
                } else {
                    chamberList = chambers;
                    ArrayAdapter<Room> adapter = new ArrayAdapter<>(RentUpdateActivity.this, android.R.layout.simple_spinner_item, chambers);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    chamberSpinner.setAdapter(adapter);
                    rentViewModel.getRentById(rentId).observe(RentUpdateActivity.this,new Observer<Rent>() {
                        @Override
                        public void onChanged(Rent rent) {
                            if (rent != null) {
                                if (chamberList != null) {
                                    for (int i = 0; i < chamberList.size(); i++) {
                                        if (chamberList.get(i).getId() == rent.getChamberId()) {
                                            chamberSpinner.setSelection(i);
                                            break;
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(RentUpdateActivity.this, "Renta no encontrada", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        editTextFilterTenant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tenantViewModel.getTenants(s.toString()).observe(RentUpdateActivity.this, new Observer<List<Tenant>>() {
                    @Override
                    public void onChanged(List<Tenant> tenants) {
                        ArrayAdapter<Tenant> adapter = new ArrayAdapter<>(RentUpdateActivity.this, android.R.layout.simple_spinner_item, tenants);
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
                roomViewModel.getRooms(s.toString()).observe(RentUpdateActivity.this, new Observer<List<Room>>() {
                    @Override
                    public void onChanged(List<Room> chambers) {
                        ArrayAdapter<Room> adapter = new ArrayAdapter<>(RentUpdateActivity.this, android.R.layout.simple_spinner_item, chambers);
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
                updateRental();
            }
        });
    }

    private void cancelRegistration() {
        finish();
    }

    private void updateRental() {
        String startDate = etStartDate.getText().toString();
        String endDate = etEndDate.getText().toString();
        String price = etPrice.getText().toString();
        Tenant selectedTenant = (Tenant) tenantSpinner.getSelectedItem();
        Room selectedChamber = (Room) chamberSpinner.getSelectedItem();

        if(isValidInputs(startDate, endDate, price, selectedTenant.getId(), selectedChamber.getId())){
            message("Success");
            Rent rent = new Rent(startDate,endDate,Double.parseDouble(price),
                    selectedTenant.getId(), selectedChamber.getId());
            rent.setId(rentId);
            rentViewModel.update(rent);
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

    private void message(String text){
        Toast.makeText(RentUpdateActivity.this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onSupportNavigateUp(){
        getOnBackPressedDispatcher().onBackPressed();
        return super.onSupportNavigateUp();
    }
}