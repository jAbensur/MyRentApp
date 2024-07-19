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

import com.myapplication.viewModel.TenantViewModel;

public class NewTenantActivity extends AppCompatActivity {

    private EditText firstNameField, lastNameField, emailField, phoneField, dniField;
    private Spinner genderSpinner, typeSpinner;
    private Button saveButton, returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tenant);

        initializeFields();
        setupSpinners();
        setupButtons();
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
    }

    private void setupSpinners() {
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.spnrGender, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.spnrType, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
    }

    private void setupButtons() {
        returnButton.setOnClickListener(view -> returnToMain());

        saveButton.setOnClickListener(view -> saveTenant());
    }

    private void saveTenant() {
        if (!validateInputs()) {
            return;
        }

        TenantViewModel dbRepository = new TenantViewModel(NewTenantActivity.this);

        String firstName = firstNameField.getText().toString();
        String lastName = lastNameField.getText().toString();
        String email = emailField.getText().toString();
        String phone = phoneField.getText().toString();
        String dni = dniField.getText().toString();
        String status = "active"; // no eliminado
        String type = typeSpinner.getSelectedItem().toString();
        String gender = genderSpinner.getSelectedItem().toString();

        long tenantId = dbRepository.insertTenant(firstName, lastName, email, phone, dni, status, type, gender);

        if (tenantId <= 0) {
            showToast("ERROR AL GUARDAR REGISTRO");
            return;
        }

        showToast("REGISTRO GUARDADO");
        clearFields();
        returnToMain();
    }

    private boolean validateInputs() {
        String firstName = firstNameField.getText().toString();
        String lastName = lastNameField.getText().toString();
        String email = emailField.getText().toString();
        String phone = phoneField.getText().toString();
        String dni = dniField.getText().toString();
//        String status = statusField.getText().toString();
        String type = typeSpinner.getSelectedItem().toString();
        String gender = genderSpinner.getSelectedItem().toString();

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
        Toast.makeText(NewTenantActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        dniField.setText("");
//        statusField.setText("");
    }

    private void returnToMain() {
        Intent intent = new Intent(this, MainTenantActivity.class);
        startActivity(intent);
    }
}
