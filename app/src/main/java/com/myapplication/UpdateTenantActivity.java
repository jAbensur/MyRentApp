package com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.myapplication.model.Tenant;
import com.myapplication.viewModel.TenantViewModel;

public class UpdateTenantActivity extends AppCompatActivity {

    EditText txtTnFirstName, txtTnLastName, txtTnEmail, txtTnPhone, txtTnDNI, txtTnStatus, txtTnType, txtTnGender;
    Button btnGuarda, btnRegresar;

    Tenant tenant;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tenant);

        txtTnFirstName = findViewById(R.id.txtTnFirstName);
        txtTnLastName = findViewById(R.id.txtTnLastName);
        txtTnEmail = findViewById(R.id.txtTnEmail);
        txtTnPhone = findViewById(R.id.txtTnPhone);
        txtTnDNI = findViewById(R.id.txtTnDNI);
        txtTnStatus = findViewById(R.id.txtTnStatus);
        txtTnType = findViewById(R.id.txtTnType);
        txtTnGender = findViewById(R.id.txtTnGender);

        btnGuarda = findViewById(R.id.btnGuarda);
        btnRegresar = findViewById(R.id.btnRegresar);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresar();
            }
        });

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("TnID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("TnID");
        }

        TenantViewModel dbContactos = new TenantViewModel(UpdateTenantActivity.this);
        tenant = dbContactos.getTenantById(id);


        if (tenant != null) {
            txtTnFirstName.setText(tenant.getTnFirstName());
            txtTnLastName.setText(tenant.getTnLastName());
            txtTnEmail.setText(tenant.getTnEmail());
            txtTnPhone.setText(tenant.getTnPhone());
            txtTnDNI.setText(tenant.getTnDNI());
            txtTnStatus.setText(tenant.getTnStatus());
            txtTnType.setText(tenant.getTnType());
            txtTnGender.setText(tenant.getTnGender());
        }

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = txtTnFirstName.getText().toString();
                String lastName = txtTnLastName.getText().toString();
                String email = txtTnEmail.getText().toString();
                String phone = txtTnPhone.getText().toString();
                String dni = txtTnDNI.getText().toString();
                String status = txtTnStatus.getText().toString();
                String type = txtTnType.getText().toString();
                String gender = txtTnGender.getText().toString();

                if(!firstName.equals("") && !lastName.equals("") && !email.equals("") && !phone.equals("")
                        && !dni.equals("") && !status.equals("") && !type.equals("") && !gender.equals("")){

                    boolean isUpdated = dbContactos.updateTenant(id, firstName, lastName, email, phone, dni, status, type, gender);

                    if (isUpdated) {
                        Toast.makeText(UpdateTenantActivity.this, "Registro actualizado correctamente", Toast.LENGTH_LONG).show();
                        verRegistro();
                    } else {
                        Toast.makeText(UpdateTenantActivity.this, "Error al actualizar registro", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(UpdateTenantActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void regresar(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void verRegistro(){
        Intent intent = new Intent(this, viewTenantActivity.class);
        startActivity(intent);
    }
}