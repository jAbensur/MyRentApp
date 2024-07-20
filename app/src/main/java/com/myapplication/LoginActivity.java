package com.myapplication;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.myapplication.viewModel.UserViewModel;

public class LoginActivity extends AppCompatActivity {
    private Button btnInsertDefaultUser, btnLogin;
    private EditText emailLoginField, passwordLoginField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeFields();
        setupButtons();
    }

    private void initializeFields() {
        emailLoginField = findViewById(R.id.txtEmail_Login);
        passwordLoginField = findViewById(R.id.txtPassword_Login);

        btnInsertDefaultUser = findViewById(R.id.button);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void setupButtons() {
        btnLogin.setOnClickListener(view -> handleLogin());

        btnInsertDefaultUser.setOnClickListener(view -> {
            // insertDefaultUser("Prueba", "Prueba", "Prueba", "admin123", "rusel@gmail.com"); // línea para probar inserción
            // showAllUsers(); // línea para ver todos los usuarios
        });
    }

    private void handleLogin() {
        String email = emailLoginField.getText().toString();
        String password = passwordLoginField.getText().toString();

        if (validateLoginInputs(email, password)) {
            String message = "Email: " + email + "\nPassword: " + password;
            showToast(message);
        }
    }

    private boolean validateLoginInputs(String email, String password) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Por favor, ingrese un correo electrónico válido");
            return false;
        }

        if (password.isEmpty()) {
            showToast("Por favor, complete todos los campos");
            return false;
        }
        return true;
    }

    private void insertDefaultUser(String firstName, String lastName, String userName, String password, String email) {
        UserViewModel userViewModel = new UserViewModel(LoginActivity.this);

        long userId = userViewModel.insertUser(firstName, lastName, userName, password, email);

        if (userId <= 0) {
            showToast("ERROR AL GUARDAR REGISTRO");
            return;
        }
        showToast("REGISTRO GUARDADO");
    }

    private void showAllUsers(){
        UserViewModel userViewModel = new UserViewModel(LoginActivity.this);
        userViewModel.getAllUsers();
    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
