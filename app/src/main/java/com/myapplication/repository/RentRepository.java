package com.myapplication.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.myapplication.model.Rent;
import com.myapplication.model.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RentRepository {
    private RentDao rentDao;
    private LiveData<List<Rent>> allRents;
    private ExecutorService executorService;
    public RentRepository(Application application) {
        Database database = Database.getInstance(application);
        rentDao = database.rentDao();
        allRents = rentDao.getAllRents();
        executorService = Executors.newFixedThreadPool(2);
    }

    public void insert(Rent rent) {
        executorService.execute(() -> rentDao.insert(rent));
    }

    public void update(Rent rent) {
        executorService.execute(() -> rentDao.update(rent));
    }

    public void delete(Rent rent) {
        executorService.execute(() -> rentDao.delete(rent));
    }

    public LiveData<List<Rent>> getAllRents() {
        return allRents;
    }
}
