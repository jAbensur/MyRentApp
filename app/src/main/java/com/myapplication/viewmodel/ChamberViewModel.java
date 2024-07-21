package com.myapplication.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.myapplication.model.Chamber;
import com.myapplication.repository.ChamberRepository;
import java.util.List;

public class ChamberViewModel extends AndroidViewModel {
    private ChamberRepository chamberRepository;
    private LiveData<List<Chamber>> allChambers;
    public ChamberViewModel(@NonNull Application application){
        super(application);
        chamberRepository = new ChamberRepository(application);
        allChambers = chamberRepository.getAllChambers();
    }
    public void insert(Chamber chamber){chamberRepository.insert(chamber);}
    public void update(Chamber chamber){chamberRepository.insert(chamber);}
    public void delete(Chamber chamber){chamberRepository.insert(chamber);}
    public LiveData<List<Chamber>> getAllChambers() {
        return allChambers;
    }
    public LiveData<List<Chamber>> getChambers(String filter) {
        return chamberRepository.getChambers(filter);
    }
}
