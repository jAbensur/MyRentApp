package com.myapplication.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.myapplication.model.Rent;
import com.myapplication.repository.RentRepository;
import java.util.List;

public class RentViewModel extends AndroidViewModel {
    private RentRepository rentRepository;
    private LiveData<List<Rent>> allRents;
    public RentViewModel(@NonNull Application application) {
        super(application);
        rentRepository = new RentRepository(application);
        allRents = rentRepository.getAllRents();
    }
    public void insert(Rent rent) {
        rentRepository.insert(rent);
    }
    public void update(Rent rent) {
        rentRepository.update(rent);
    }
    public void delete(Rent rent) {
        rentRepository.delete(rent);
    }
    public LiveData<List<Rent>> getAllRents() {
        return allRents;
    }
    public LiveData<Rent> getRentById(int rentId) {
        return rentRepository.getRentById(rentId);
    }
}
