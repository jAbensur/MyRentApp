package com.myapplication.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.myapplication.model.Tenant;
import com.myapplication.model.User;
import com.myapplication.repository.UserRepository;
import com.myapplication.view.LoginActivity;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private static final String TAG = "UserViewModel";

    private UserRepository userRepository;
    private LiveData<List<User>> allUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        allUsers = userRepository.getAllUsers();
    }

    public void insert(User user) {
        userRepository.insert(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void deleteById(int userId) {
        userRepository.deleteById(userId);
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void logAllUsers() {
        allUsers.observeForever(users -> {
            if (users != null) {
                for (User user : users) {
                    Log.i(TAG, "User: " + user.toString());
                }
            } else {
                Log.i(TAG, "No users found.");
            }
        });
    }

    public boolean isUserValid(String email, String password) {
        return userRepository.isUserValid(email, password);
    }

    public LiveData<User> getUserById(int userId) {
        return userRepository.getUserById(userId);
    }
}
