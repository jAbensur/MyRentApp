package com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.myapplication.viewModel.TenantViewModel;

public class NewTenantActivity extends AppCompatActivity {

    EditText txtTnFirstName, txtTnLastName, txtTnEmail, txtTnPhone, txtTnDNI, txtTnStatus, txtTnType, txtTnGender;
    Button btnSave, btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tenant);

        txtTnFirstName = findViewById(R.id.txtTnFirstName);
        txtTnLastName = findViewById(R.id.txtTnLastName);
        txtTnEmail = findViewById(R.id.txtTnEmail);
        txtTnPhone = findViewById(R.id.txtTnPhone);
        txtTnDNI = findViewById(R.id.txtTnDNI);
        txtTnStatus = findViewById(R.id.txtTnStatus);
        txtTnType = findViewById(R.id.txtTnType);
        txtTnGender = findViewById(R.id.txtTnGender);

        btnSave = findViewById(R.id.btnSave);
        btnReturn = findViewById(R.id.btnReturn);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToMain();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TenantViewModel dbRepository = new TenantViewModel(NewTenantActivity.this);

                String firstName = txtTnFirstName.getText().toString();
                String lastName = txtTnLastName.getText().toString();
                String email = txtTnEmail.getText().toString();
                String phone = txtTnPhone.getText().toString();
                String dni = txtTnDNI.getText().toString();
                String status = txtTnStatus.getText().toString();
                String type = txtTnType.getText().toString();
                String gender = txtTnGender.getText().toString();

                long TnID = dbRepository.insertTenant(firstName, lastName, email, phone, dni, status, type, gender);

                if (TnID > 0) {
                    Toast.makeText(NewTenantActivity.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                    clearFields();
                    returnToMain();
                } else {
                    Toast.makeText(NewTenantActivity.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void clearFields() {
        txtTnFirstName.setText("");
        txtTnLastName.setText("");
        txtTnEmail.setText("");
        txtTnPhone.setText("");
        txtTnDNI.setText("");
        txtTnStatus.setText("");
        txtTnType.setText("");
        txtTnGender.setText("");
    }

    private void returnToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}