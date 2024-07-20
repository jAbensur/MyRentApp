package com.myapplication;

import android.database.sqlite.SQLiteDatabase;
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
import com.myapplication.viewModel.UserViewModel;

public class LoginActivity extends AppCompatActivity {
    Button button, btnLogin;
    private EditText txtEmailLogin, txtPasswordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button= findViewById(R.id.button);
        btnLogin= findViewById(R.id.btnLogin);

        txtEmailLogin = findViewById(R.id.txtEmail_Login);
        txtPasswordLogin = findViewById(R.id.txtPassword_Login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmailLogin.getText().toString();
                String password = txtPasswordLogin.getText().toString();

                String message = "Email: " + email + "\nPassword: " + password;
                showToast(message);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                insertDefaultUser("Prueba", "Prueba", "Prueba", "admin123", "rusel@gmail.com"); // linea para probar insersion
//                showAllUsers(); // linea para ver todos los usuarios
            }
        });
    }

    private void insertDefaultUser(String firstName, String lastName, String userName,
                                   String password, String email) {
        UserViewModel userViewModel = new UserViewModel(LoginActivity.this);

        long userId = userViewModel.insertUser(firstName, lastName, userName, password, email);

        if (userId <= 0) {
            showToast("ERROR AL GUARDAR REGISTRO");
            return;
        }

        showToast("REGISTRO GUARDADO");
    }

    private void showAllUsers(){
        UserViewModel tenantViewModel = new UserViewModel(LoginActivity.this);
        tenantViewModel.getAllUsers();
    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }
}