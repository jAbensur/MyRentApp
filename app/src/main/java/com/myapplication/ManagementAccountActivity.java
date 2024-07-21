package com.myapplication;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ManagementAccountActivity extends AppCompatActivity {

    private EditText txtFirstName, txtLastName, txtUserName, txtEmail, txtPassword;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_account);

        initializeFields();
        setupButtons();
    }

    private void initializeFields() {
        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtUserName = findViewById(R.id.txtUserName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        btnSave = findViewById(R.id.btnSave);
    }

    private void setupButtons() {
        btnSave.setOnClickListener(view -> {
//            if (!validateInputs()) {
//                return;
//            }

                String firstName = txtFirstName.getText().toString();
                String lastName = txtLastName.getText().toString();
                String userName = txtUserName.getText().toString();
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

                String message = "First Name: " + firstName + "\n" +
                        "Last Name: " + lastName + "\n" +
                        "User Name: " + userName + "\n" +
                        "Email: " + email + "\n" +
                        "Password: " + password;

                showToast(message);

        });
    }

    private boolean validateInputs() {
        String firstName = txtFirstName.getText().toString();
        String lastName = txtLastName.getText().toString();
        String userName = txtUserName.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        if (firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showToast("Por favor, complete todos los campos");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Por favor, ingrese un correo electrónico válido");
            return false;
        }

        if (password.length() < 4) {
            showToast("La contrasena debe tener al menos 4 dígitos");
            return false;
        }

        return true;
    }

    private void showToast(String message) {
        Toast.makeText(ManagementAccountActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
