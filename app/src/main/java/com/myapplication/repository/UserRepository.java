package com.myapplication.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import com.myapplication.database.Database;
import com.myapplication.model.User;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;
    private ExecutorService executorService;

    private static final String TAG = "UserRepository";

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

    public void delete(User user) { // no me funciona
        executorService.execute(() -> userDao.delete(user));
    }

    public void deleteById(int userId) {
        executorService.execute(() -> userDao.deleteById(userId));
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    // Método para verificar si el usuario es válido
    public boolean isUserValid(String email, String password) {
        final boolean[] isValid = {false};
        final CountDownLatch latch = new CountDownLatch(1);

        executorService.execute(() -> {
            isValid[0] = userDao.isUserValid(email, password);
//            Log.i(TAG, "1: " + userDao.isUserValid(email, password));
            latch.countDown();
        });

        try {
            latch.await(); // Espera a que la tarea asincrónica termine
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        Log.i(TAG, "2: " + isValid[0]);

        return isValid[0];
    }
}
