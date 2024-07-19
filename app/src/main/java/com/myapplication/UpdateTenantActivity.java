package com.myapplication;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapplication.model.Tenant;
import com.myapplication.viewModel.TenantViewModel;

public class UpdateTenantActivity extends AppCompatActivity {

    private EditText firstNameField, lastNameField, emailField, phoneField, dniField;
    private Spinner genderSpinner, typeSpinner;
    private Button saveButton, returnButton;
    private FloatingActionButton updateButton, deleteButton;

    private Tenant tenant;
    private int tenantId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tenant);

        initializeFields();
        setupSpinners();
        setupButtons();

        tenantId = getTenantIdFromIntent(savedInstanceState);

        TenantViewModel tenantViewModel = new TenantViewModel(UpdateTenantActivity.this);
        tenant = tenantViewModel.getTenantById(tenantId);

        if (tenant != null) {
            populateFields();
        }
    }

    private void initializeFields() {
        firstNameField = findViewById(R.id.txtTnFirstName);
        lastNameField = findViewById(R.id.txtTnLastName);
        emailField = findViewById(R.id.txtTnEmail);
        phoneField = findViewById(R.id.txtTnPhone);
        dniField = findViewById(R.id.txtTnDNI);
//        statusField = findViewById(R.id.txtTnStatus);

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
        firstNameField.setText(tenant.getTnFirstName());
        lastNameField.setText(tenant.getTnLastName());
        emailField.setText(tenant.getTnEmail());
        phoneField.setText(tenant.getTnPhone());
        dniField.setText(tenant.getTnDNI());
//        statusField.setText(tenant.getTnStatus());

        setSpinnerSelection(genderSpinner, R.array.spnrGender, tenant.getTnGender());
        setSpinnerSelection(typeSpinner, R.array.spnrType, tenant.getTnType());

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

        TenantViewModel tenantViewModel = new TenantViewModel(UpdateTenantActivity.this);

        String firstName = firstNameField.getText().toString();
        String lastName = lastNameField.getText().toString();
        String email = emailField.getText().toString();
        String phone = phoneField.getText().toString();
        String dni = dniField.getText().toString();
        String status = "active";
        String type = typeSpinner.getSelectedItem().toString();
        String gender = genderSpinner.getSelectedItem().toString();

        boolean isUpdated = tenantViewModel.updateTenant(tenantId, firstName, lastName, email, phone, dni, status, type, gender);

        if (isUpdated) {
            showToast("Registro actualizado correctamente");
            viewRecord();
        } else {
            showToast("Error al actualizar registro");
        }
    }

    private boolean validateInputs() {
        String firstName = firstNameField.getText().toString();
        String lastName = lastNameField.getText().toString();
        String email = emailField.getText().toString();
        String phone = phoneField.getText().toString();
        String dni = dniField.getText().toString();
//        String status = statusField.getText().toString();

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
        Toast.makeText(UpdateTenantActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void returnToMain() {
        Intent intent = new Intent(this, MainTenantActivity.class);
        startActivity(intent);
    }

    private void viewRecord() {
        Intent intent = new Intent(this, ViewTenantActivity.class);
        intent.putExtra("TnID", tenantId);
        startActivity(intent);
    }
}
