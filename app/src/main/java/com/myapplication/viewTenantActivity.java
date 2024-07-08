package com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.myapplication.model.Tenant;
import com.myapplication.viewModel.TenantViewModel;

public class viewTenantActivity extends AppCompatActivity {

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

        TenantViewModel dbContactos = new TenantViewModel(viewTenantActivity.this);
        tenant = dbContactos.getTenantById(id);

        Toast.makeText(viewTenantActivity.this, "llego aca2", Toast.LENGTH_LONG).show();

        if (tenant != null) {
            txtTnFirstName.setText(tenant.getTnFirstName());
            txtTnLastName.setText(tenant.getTnLastName());
            txtTnEmail.setText(tenant.getTnEmail());
            txtTnPhone.setText(tenant.getTnPhone());
            txtTnDNI.setText(tenant.getTnDNI());
            txtTnStatus.setText(tenant.getTnStatus());
            txtTnType.setText(tenant.getTnType());
            txtTnGender.setText(tenant.getTnGender());

            btnGuarda.setVisibility(View.INVISIBLE);

            txtTnFirstName.setInputType(InputType.TYPE_NULL);
            txtTnLastName.setInputType(InputType.TYPE_NULL);
            txtTnEmail.setInputType(InputType.TYPE_NULL);
            txtTnPhone.setInputType(InputType.TYPE_NULL);
            txtTnDNI.setInputType(InputType.TYPE_NULL);
            txtTnStatus.setInputType(InputType.TYPE_NULL);
            txtTnType.setInputType(InputType.TYPE_NULL);
            txtTnGender.setInputType(InputType.TYPE_NULL);
        }

    }

    private void regresar(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}