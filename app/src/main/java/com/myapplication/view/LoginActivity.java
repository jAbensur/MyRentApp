package com.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.myapplication.R;
import com.myapplication.model.Tenant;
import com.myapplication.model.User;
import com.myapplication.viewmodel.TenantViewModel;
import com.myapplication.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {
    private Button btnInsertDefaultUser, btnLogin;
    private EditText emailLoginField, passwordLoginField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeFields();
        setupButtons();
        inserUser();
    }

    private void inserUser(){
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, users -> {
            if (users == null || users.isEmpty()) {
                // Agregar usuario por defecto si la lista está vacía
                insertDefaultUser("Default User", "admin@gmail.com", "admin");
                showToast("Usuario por defecto agregado.");
            }
        });
    }

    private void initializeFields() {
        emailLoginField = findViewById(R.id.txtEmail_Login);
        passwordLoginField = findViewById(R.id.txtPassword_Login);

        btnInsertDefaultUser = findViewById(R.id.button);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void setupButtons() {
        btnLogin.setOnClickListener(view -> {

//            showToast("Boton login presionado");
            handleLogin();

//            insertDefaultUser("Prueba", "prueba@gmail.com", "prueba"); // línea para probar inserción
//            deleteUser(2); // linea para eliminar
        });

        btnInsertDefaultUser.setOnClickListener(view -> {
            showToast("Boton prueba presionado");
            showAllUsers(); // línea para ver todos los usuarios
        });
    }

    private void deleteUser(int indice) {
        UserViewModel userViewModel =  new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.deleteById(indice);
    }

    private void handleLogin() {
        String email = emailLoginField.getText().toString();
        String password = passwordLoginField.getText().toString();

//        String email = "prueba@gmail.com";
//        String password = "prueba";
        if (!validateLoginInputs(email, password)) {
            return;
        }
//        String message = "Email: " + email + "\nPassword: " + password;
//        showToast(message);

        UserViewModel userViewModel =  new ViewModelProvider(this).get(UserViewModel.class);
        boolean valid = userViewModel.isUserValid(email, password);

        if (!valid) {
            showToast("Credenciales incorrectas.");
            return;
        }
        showToast("Inicio de sesión exitoso");
        goToHome();
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

    private void insertDefaultUser(String name, String email, String password) {
        UserViewModel userViewModel =  new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.insert( new User(name, email, password));
    }

    private void showAllUsers() {
//        UserViewModel userViewModel = new UserViewModel(LoginActivity.this);

//        TenantViewModel tenantViewModel =  new ViewModelProvider(this).get(TenantViewModel.class);

        UserViewModel userViewModel =  new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.logAllUsers();
    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void goToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
