package com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    TextView emailLoginTextView;
    Button btnTenantManagement , btnAccountManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeFields();
        setupButtons();
    }

    private void initializeFields() {
        emailLoginTextView = findViewById(R.id.Email_Login);

        btnTenantManagement = findViewById(R.id.btnTenantManagement);
        btnAccountManagement = findViewById(R.id.btnAccountManagement);
    }

    private void setupButtons() {
        btnTenantManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTenantManagement();
            }
        });

        btnAccountManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAccountManagement();
            }
        });
    }

    private void goToTenantManagement() {
        Intent intent = new Intent(this, MainTenantActivity.class);
        startActivity(intent);
    }

    private void goToAccountManagement() {
        Intent intent = new Intent(this, ManagementAccountActivity.class);
        startActivity(intent);
    }
}