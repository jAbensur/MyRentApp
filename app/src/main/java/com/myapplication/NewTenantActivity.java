package com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.myapplication.repository.DbRepository;
import com.myapplication.viewModel.TenantViewModel;

public class NewTenantActivity extends AppCompatActivity {

    EditText txtTnFirstName, txtTnLastName, txtTnEmail, txtTnPhone, txtTnDNI, txtTnStatus, txtTnType, txtTnGender;
    Button btnGuarda, btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_tenant);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

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

        btnGuarda.setOnClickListener(new View.OnClickListener() {
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


                long id = dbRepository.insertTenant(firstName, lastName, email, phone, dni, status, type, gender);

                if (id > 0) {
                    Toast.makeText(NewTenantActivity.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                    limpiar();
                    regresar();
                } else {
                    Toast.makeText(NewTenantActivity.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void limpiar() {
        txtTnFirstName.setText("");
        txtTnLastName.setText("");
        txtTnEmail.setText("");
        txtTnPhone.setText("");
        txtTnDNI.setText("");
        txtTnStatus.setText("");
        txtTnType.setText("");
        txtTnGender.setText("");
    }

    private void regresar(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}