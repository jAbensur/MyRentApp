package com.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.myapplication.model.Room;
import com.myapplication.repository.RoomRepository;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {

    private final RoomRepository _repository;
    private final LiveData<List<Room>> _allRooms;

    public RoomViewModel(@NonNull Application application) {
        super(application);
        _repository = new RoomRepository(application);
        _allRooms = _repository.getAllRooms();
    }

    public void insertRoom(Room room){ _repository.insertRoom(room); }

    public void updateRoom(Room room){ _repository.updateRoom(room); }

    public void deleteRoom(Room room){ _repository.deleteRoom(room); }

    public LiveData<List<Room>> getAllRooms(){ return _allRooms; }

    public Room getRoomById(int id){ return _repository.getRoomById(id); }
}
