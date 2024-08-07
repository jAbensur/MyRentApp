package com.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.myapplication.R;
import com.myapplication.model.User;
import com.myapplication.viewmodel.UserViewModel;

public class ManagementAccountActivity extends AppCompatActivity {

    private EditText txtUserName, txtEmail, txtPassword;
    private Button btnEdit, btnInsertDefaultUser;
    private static final String TAG = "ManagementAccountActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_account);
        initializeFields();
        setupButtons();
        observeUserById(1);
    }

    private void observeUserById(int userId) {
        UserViewModel userViewModel =  new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUserById(userId).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    Log.i(TAG, "User: " + user.toString());
                    // Set text fields with user data
                    txtUserName.setText(user.getName());
                    txtEmail.setText(user.getEmail());
                    txtPassword.setText(user.getPassword());
                } else {
                    Log.i(TAG, "User not found.");
                }
            }
        });
    }

    private void initializeFields() {
        txtUserName = findViewById(R.id.txtUserName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnInsertDefaultUser = findViewById(R.id.button);

        btnEdit = findViewById(R.id.btnEdit);
    }

    private void setupButtons() {
        btnEdit.setOnClickListener(view -> {

            if (!validateInputs()) {
                return;
            }

            int tenantId = 1;
            String userName = txtUserName.getText().toString();
            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();

            User updatedUser = new User(userName, email, password);

            int userId = 1;
            updatedUser.setId(userId);

            UserViewModel userViewModel =  new ViewModelProvider(this).get(UserViewModel.class);
            userViewModel.update(updatedUser);

            Log.i(TAG, "UserUpdate: " + updatedUser.toString());
            returnToHome();
        });

        btnInsertDefaultUser.setOnClickListener(view -> {
            returnToHome();
        });
    }

    private void mostrarAdmin(){
        UserViewModel userViewModel =  new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUserById(1).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    Log.i(TAG, "User: " + user.toString());
                } else {
                    Log.i(TAG, "User not found.");
                }
            }
        });
    }

    private void returnToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private boolean validateInputs() {
        String userName = txtUserName.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        if (userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
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