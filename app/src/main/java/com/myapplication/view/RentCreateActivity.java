package com.myapplication.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import com.myapplication.R;
import com.myapplication.model.Rent;
import com.myapplication.model.Room;
import com.myapplication.model.Tenant;
import com.myapplication.viewmodel.RentViewModel;
import com.myapplication.viewmodel.RoomViewModel;
import com.myapplication.viewmodel.TenantViewModel;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class RentCreateActivity extends AppCompatActivity {
    private EditText etStartDate, etEndDate, etPrice;
    private Button btnCancel, btnRegister;
    private TenantViewModel tenantViewModel;
    private RoomViewModel roomViewModel;
    private RentViewModel rentViewModel;
    private Spinner tenantSpinner, chamberSpinner;
    private Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_create);
        getSupportActionBar().setTitle("Registrar Alquiler");
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

        tenantViewModel = new ViewModelProvider(this).get(TenantViewModel.class);
        roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);
        rentViewModel = new ViewModelProvider(this).get(RentViewModel.class);

        try {
            tenantViewModel.getAllTenants().observe(this, new Observer<List<Tenant>>() {
                @Override
                public void onChanged(List<Tenant> tenants) {
                    if (tenants == null || tenants.isEmpty()) {
                        Toast.makeText(RentCreateActivity.this, "No hay inquilinos disponibles", Toast.LENGTH_LONG).show();
                    } else {
                        TenantSpinnerAdapter adapter = new TenantSpinnerAdapter(RentCreateActivity.this, tenants);
                        tenantSpinner.setAdapter(adapter);
                    }
                }

            });
            roomViewModel.getAllAvailableRooms().observe(this, new Observer<List<Room>>() {
                @Override
                public void onChanged(List<Room> rooms) {
                    if (rooms == null || rooms.isEmpty()) {
                        Toast.makeText(RentCreateActivity.this, "No hay habitaciones disponibles", Toast.LENGTH_LONG).show();
                    } else {
                        RoomSpinnerAdapter adapter = new RoomSpinnerAdapter(RentCreateActivity.this, rooms);
                        chamberSpinner.setAdapter(adapter);
                    }
                }
            });

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
                roomViewModel.getRooms(s.toString()).observe(RentCreateActivity.this, new Observer<List<Room>>() {
                    @Override
                    public void onChanged(List<Room> rooms) {
                        ArrayAdapter<Room> adapter = new ArrayAdapter<>(RentCreateActivity.this, android.R.layout.simple_spinner_item, rooms);
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
        chamberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Room selectedRoom = (Room) parent.getSelectedItem();
                if (selectedRoom != null) {
                    room = selectedRoom;
                    etPrice.setText(String.valueOf(selectedRoom.getPricePerMonth()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
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
        Room selectedChamber = (Room) chamberSpinner.getSelectedItem();

        if(isValidInputs(startDate, endDate, price, selectedTenant.getId(), selectedChamber.getId())){
            message("Success");
            rentViewModel.insert(new Rent(startDate,endDate,Double.parseDouble(price),
                    selectedTenant.getId(), selectedChamber.getId()));
            room.setState(0);
            roomViewModel.updateRoom(room);
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

    @Override
    public boolean onSupportNavigateUp(){
        getOnBackPressedDispatcher().onBackPressed();
        return super.onSupportNavigateUp();
    }
}