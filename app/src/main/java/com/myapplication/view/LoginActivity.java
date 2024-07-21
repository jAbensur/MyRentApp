package com.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.myapplication.R;
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
    }

    private void initializeFields() {
        emailLoginField = findViewById(R.id.txtEmail_Login);
        passwordLoginField = findViewById(R.id.txtPassword_Login);

        btnInsertDefaultUser = findViewById(R.id.button);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void setupButtons() {
        btnLogin.setOnClickListener(view -> {
            handleLogin();

//            deleteUser(1); // linea para eliminar
        });

        btnInsertDefaultUser.setOnClickListener(view -> {
//             insertDefaultUser("Russell Nobaru", "Prueba", "Prueba", "admin", "ruselcucho@gmail.com"); // línea para probar inserción
            showAllUsers(); // línea para ver todos los usuarios
//            showToast("Boton presionado");
        });
    }

    private void deleteUser(int userId) {
        /*
        UserViewModel tenantViewModel = new UserViewModel(LoginActivity.this);
        if (tenantViewModel.deleteUser(userId)) {
//        returnToMain();
            showToast("Se eliminó el registro");
        }*/
    }

    private void handleLogin() {
        String email = emailLoginField.getText().toString();
        String password = passwordLoginField.getText().toString();

//        if (validateLoginInputs(email, password)) {
//            String message = "Email: " + email + "\nPassword: " + password;
//            showToast(message);
//        }

        if (!validateLoginInputs(email, password)) {
            return;
        }

        //UserViewModel userViewModel = new UserViewModel(LoginActivity.this);
        //boolean isLoggedIn = userViewModel.login(email, password);
        /*
        if (isLoggedIn) {
            showToast("Inicio de sesión exitoso");
            goToHome();
        } else {
            showToast("Credenciales incorrectas");
        }*/

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

    private void insertDefaultUser(String firstName, String lastName, String userName, String password, String email) {
        /*
        UserViewModel userViewModel = new UserViewModel(LoginActivity.this);

        long userId = userViewModel.insertUser(firstName, lastName, userName, password, email);

        if (userId <= 0) {
            showToast("ERROR AL GUARDAR REGISTRO");
            return;
        }
        showToast("REGISTRO GUARDADO");*/
    }

    private void showAllUsers() {
        //UserViewModel userViewModel = new UserViewModel(LoginActivity.this);
        //userViewModel.getAllUsers();
    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void goToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
