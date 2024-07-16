package com.myapplication.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.myapplication.model.Rent;
import com.myapplication.repository.RentRepository;
import java.util.List;

public class RentViewModel extends AndroidViewModel {
    private RentRepository repository;
    private LiveData<List<Rent>> allRents;
    public RentViewModel(@NonNull Application application) {
        super(application);
        repository = new RentRepository(application);
        allRents = repository.getAllRents();
    }

    public void insert(Rent rent) {
        repository.insert(rent);
    }

    public void update(Rent rent) {
        repository.update(rent);
    }

    public void delete(Rent rent) {
        repository.delete(rent);
    }

    public LiveData<List<Rent>> getAllUsers() {
        return allRents;
    }
}
