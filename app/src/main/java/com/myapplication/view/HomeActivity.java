package com.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.myapplication.R;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ImageView buttonChambersIcon = findViewById(R.id.button_chambers_icon);
        ImageView buttonPropertiesIcon = findViewById(R.id.button_properties_icon);
        ImageView buttonUserIcon = findViewById(R.id.button_user_icon);
        ImageView buttonTenantsIcon = findViewById(R.id.button_tenants_icon);
        ImageView buttonRentalsIcon = findViewById(R.id.button_rentals_icon);

        buttonChambersIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RoomActivity.class);
                startActivity(intent);
            }
        });

        buttonPropertiesIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PropertyActivity.class);
                startActivity(intent);
            }
        });

        buttonUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showToast("Usuario");
                Intent intent = new Intent(HomeActivity.this, ManagementAccountActivity.class);
                startActivity(intent);
            }
        });

        buttonTenantsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TenantActivity.class);
                startActivity(intent);
            }
        });

        buttonRentalsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}