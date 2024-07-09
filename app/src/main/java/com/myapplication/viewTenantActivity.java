package com.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapplication.model.Tenant;
import com.myapplication.viewModel.TenantViewModel;

public class viewTenantActivity extends AppCompatActivity {

    EditText txtTnFirstName, txtTnLastName, txtTnEmail, txtTnPhone, txtTnDNI, txtTnStatus, txtTnType, txtTnGender;
    Button btnSave, btnReturn;

    FloatingActionButton fabUpdate, fatDelete;

    Tenant tenant;
    int TnID = 0;

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

        btnSave = findViewById(R.id.btnSave);
        btnReturn = findViewById(R.id.btnReturn);
        fabUpdate = findViewById(R.id.fabUpdate);
        fatDelete = findViewById(R.id.fatDelete);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToMain();
            }
        });

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                TnID = Integer.parseInt(null);
            } else {
                TnID = extras.getInt("TnID");
            }
        } else {
            TnID = (int) savedInstanceState.getSerializable("TnID");
        }

        TenantViewModel dbContactos = new TenantViewModel(viewTenantActivity.this);
        tenant = dbContactos.getTenantById(TnID);


        if (tenant != null) {
            txtTnFirstName.setText(tenant.getTnFirstName());
            txtTnLastName.setText(tenant.getTnLastName());
            txtTnEmail.setText(tenant.getTnEmail());
            txtTnPhone.setText(tenant.getTnPhone());
            txtTnDNI.setText(tenant.getTnDNI());
            txtTnStatus.setText(tenant.getTnStatus());
            txtTnType.setText(tenant.getTnType());
            txtTnGender.setText(tenant.getTnGender());

            btnSave.setVisibility(View.INVISIBLE);

            txtTnFirstName.setInputType(InputType.TYPE_NULL);
            txtTnLastName.setInputType(InputType.TYPE_NULL);
            txtTnEmail.setInputType(InputType.TYPE_NULL);
            txtTnPhone.setInputType(InputType.TYPE_NULL);
            txtTnDNI.setInputType(InputType.TYPE_NULL);
            txtTnStatus.setInputType(InputType.TYPE_NULL);
            txtTnType.setInputType(InputType.TYPE_NULL);
            txtTnGender.setInputType(InputType.TYPE_NULL);
        }

        fabUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(viewTenantActivity.this, UpdateTenantActivity.class);
                intent.putExtra("TnID", TnID);
                startActivity(intent);
            }
        });

        fatDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(viewTenantActivity.this);
                builder.setMessage("¿Desea eliminar este contacto?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (dbContactos.deleteTenant(TnID)) {
                                    returnToMain();
                                    Toast.makeText(viewTenantActivity.this, "Se elimino el registro", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(viewTenantActivity.this, "No se elimino el registro", Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });

    }

    private void returnToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}