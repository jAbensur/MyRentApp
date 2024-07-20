package com.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapplication.model.Tenant;
import com.myapplication.viewModel.TenantViewModel;

public class ViewTenantActivity extends AppCompatActivity {

    private EditText firstNameField, lastNameField, emailField, phoneField, dniField;
    private Button saveButton, returnButton;
    private FloatingActionButton updateButton, deleteButton;
    private Spinner genderSpinner, typeSpinner;

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

        TenantViewModel tenantViewModel = new TenantViewModel(ViewTenantActivity.this);
        tenant = tenantViewModel.getTenantById(tenantId);

        if (tenant != null) {
            populateFields();
            disableInputFields();
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

        updateButton.setOnClickListener(view -> {
            Intent intent = new Intent(ViewTenantActivity.this, UpdateTenantActivity.class);
            intent.putExtra("TnID", tenantId);
            startActivity(intent);
        });

        deleteButton.setOnClickListener(view -> showDeleteConfirmationDialog());
    }

    private int getTenantIdFromIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TnID")) {
            return intent.getIntExtra("TnID", 0);
        }
        return 0;
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

        saveButton.setVisibility(View.INVISIBLE);
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

    private void disableInputFields() {
        firstNameField.setInputType(InputType.TYPE_NULL);
        lastNameField.setInputType(InputType.TYPE_NULL);
        emailField.setInputType(InputType.TYPE_NULL);
        phoneField.setInputType(InputType.TYPE_NULL);
        dniField.setInputType(InputType.TYPE_NULL);
//        statusField.setInputType(InputType.TYPE_NULL);

        genderSpinner.setOnTouchListener((v, event) -> true);
        typeSpinner.setOnTouchListener((v, event) -> true);
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(ViewTenantActivity.this)
                .setMessage("¿Desea eliminar este contacto?")
                .setPositiveButton("SI", (dialogInterface, i) -> {
                    TenantViewModel tenantViewModel = new TenantViewModel(ViewTenantActivity.this);
                    if (tenantViewModel.deleteTenant(tenantId)) {
                        returnToMain();
                        showToast("Se eliminó el registro");
                    }
                })
                .setNegativeButton("NO", (dialogInterface, i) -> showToast("No se eliminó el registro"))
                .show();
    }

    private void returnToMain() {
        Intent intent = new Intent(this, MainTenantActivity.class);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(ViewTenantActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
