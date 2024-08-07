package com.myapplication.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.myapplication.model.User;
import com.myapplication.repository.UserRepository;
import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final LiveData<List<User>> allUsers;

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

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public boolean isUserValid(String email, String password) {
        return userRepository.isUserValid(email, password);
    }

    public LiveData<User> getUserById(int userId) {
        return userRepository.getUserById(userId);
    }

}
