package com.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapplication.R;
import com.myapplication.model.Tenant;
import com.myapplication.viewmodel.TenantViewModel;

public class TenantUpdateActivity extends AppCompatActivity {

    private EditText firstNameField, lastNameField, emailField, phoneField, dniField;
    private Spinner genderSpinner, typeSpinner;
    private Button saveButton, returnButton;
    private FloatingActionButton updateButton, deleteButton;

    private Tenant tenant;
    private int tenantId = 0;
    private static final String TAG = "ViewTenantActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tenant);
        getSupportActionBar().setTitle("Actualizar Inquilino");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeFields();
        setupSpinners();
        setupButtons();

        tenantId = getTenantIdFromIntent(savedInstanceState);

        TenantViewModel tenantViewModel =  new ViewModelProvider(this).get(TenantViewModel.class);

        tenantViewModel.getTenntById(tenantId).observe((LifecycleOwner) TenantUpdateActivity.this, new Observer<Tenant>() {
            @Override
            public void onChanged(Tenant _tenant) {
                if (_tenant != null) {
                    tenant = _tenant;
                    populateFields();
                }
            }
        });
        /*
        if (tenant != null) {
            populateFields();
        }*/
    }

    private void initializeFields() {
        firstNameField = findViewById(R.id.txtTnFirstName);
        lastNameField = findViewById(R.id.txtTnLastName);
        emailField = findViewById(R.id.txtTnEmail);
        phoneField = findViewById(R.id.txtTnPhone);
        dniField = findViewById(R.id.txtTnDNI);

        genderSpinner = findViewById(R.id.spnrGender);
        typeSpinner = findViewById(R.id.spnrType);

        saveButton = findViewById(R.id.btnSave);
        returnButton = findViewById(R.id.btnReturn);
        updateButton = findViewById(R.id.fabUpdate);
        deleteButton = findViewById(R.id.fatDelete);

        updateButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);
    }

    private void setupSpinners() {
        setupSpinner(genderSpinner, R.array.spnrGender);
        setupSpinner(typeSpinner, R.array.spnrType);
    }

    private void setupSpinner(Spinner spinner, int arrayResourceId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                arrayResourceId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setupButtons() {
        returnButton.setOnClickListener(view -> returnToMain());
        saveButton.setOnClickListener(view -> saveTenant());
    }

    private int getTenantIdFromIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        return (intent != null && intent.hasExtra("TnID")) ? intent.getIntExtra("TnID", 0) : 0;
    }

    private void populateFields() {
        firstNameField.setText(tenant.getFirstName());
        lastNameField.setText(tenant.getLastName());
        emailField.setText(tenant.getEmail());
        phoneField.setText(tenant.getPhone());
        dniField.setText(tenant.getDni());

        setSpinnerSelection(genderSpinner, R.array.spnrGender, tenant.getGender());
        setSpinnerSelection(typeSpinner, R.array.spnrType, tenant.getType());

    }

    private void setSpinnerSelection(Spinner spinner, int arrayResourceId, String value) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).toString().equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void saveTenant() {
        if (!validateInputs()) {
            return;
        }

        TenantViewModel tenantViewModel =  new ViewModelProvider(this).get(TenantViewModel.class);

        String firstName = firstNameField.getText().toString();
        String lastName = lastNameField.getText().toString();
        String email = emailField.getText().toString();
        String phone = phoneField.getText().toString();
        String dni = dniField.getText().toString();
        String status = "active";
        String type = typeSpinner.getSelectedItem().toString();
        String gender = genderSpinner.getSelectedItem().toString();

        Tenant tenant = new Tenant();
        tenant.setId(tenantId);
        tenant.setFirstName(firstName);
        tenant.setLastName(lastName);
        tenant.setEmail(email);
        tenant.setPhone(phone);
        tenant.setDni(dni);
        tenant.setStatus(status);
        tenant.setType(type);
        tenant.setGender(gender);

        tenantViewModel.update(tenant);
        showToast("Registro actualizado correctamente");

        returnToMain();
    }

    private boolean validateInputs() {
        String firstName = firstNameField.getText().toString();
        String lastName = lastNameField.getText().toString();
        String email = emailField.getText().toString();
        String phone = phoneField.getText().toString();
        String dni = dniField.getText().toString();

        if (dni.length() < 8) {
            showToast("El DNI debe tener al menos 8 dígitos");
            return false;
        }

        if (phone.length() < 9) {
            showToast("El Teléfono debe tener al menos 9 dígitos");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Por favor, ingrese un correo electrónico válido");
            return false;
        }

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || dni.isEmpty() || typeSpinner.getSelectedItemPosition() == 0 || genderSpinner.getSelectedItemPosition() == 0) {
            showToast("Por favor, complete todos los campos");
            return false;
        }

        return true;
    }

    private void showToast(String message) {
        Toast.makeText(TenantUpdateActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void returnToMain() {
        Intent intent = new Intent(this, TenantActivity.class);
        startActivity(intent);
    }

    private void viewRecord() {
        Intent intent = new Intent(this, TenantViewActivity.class);
        intent.putExtra("TnID", tenantId);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp(){
        getOnBackPressedDispatcher().onBackPressed();
        return super.onSupportNavigateUp();
    }
}
