package com.myapplication.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.myapplication.database.Database;
import com.myapplication.model.User;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;
    private ExecutorService executorService;

    public UserRepository(Application application) {
        Database database = Database.getInstance(application);
        userDao = database.userDao();
        allUsers = userDao.getAllUsers();
        executorService = Executors.newFixedThreadPool(2);
    }

    public void insert(User user) {
        executorService.execute(() -> userDao.insert(user));
    }

    public void update(User user) {
        executorService.execute(() -> userDao.update(user));
    }

    public void delete(User user) {
        executorService.execute(() -> userDao.delete(user));
    }

    public void deleteById(int userId) {
        executorService.execute(() -> userDao.deleteById(userId));
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }
}
