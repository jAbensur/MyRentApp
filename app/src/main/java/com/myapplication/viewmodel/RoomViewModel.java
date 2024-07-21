package com.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.myapplication.model.RoomModel;
import com.myapplication.repository.RoomRepository;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {

    private final RoomRepository _repository;
    //private final LiveData<List<RoomModel>> _allRooms;

    public RoomViewModel(@NonNull Application application) {
        super(application);
        _repository = new RoomRepository(application);
        //_allRooms = _repository.getAllRooms();
    }

    public void insertRoom(RoomModel roomModel){ _repository.insertRoom(roomModel); }

    public void updateRoom(RoomModel roomModel){ _repository.updateRoom(roomModel); }

    public void deleteRoom(RoomModel roomModel){ _repository.deleteRoom(roomModel); }

    //public LiveData<List<RoomModel>> getAllRooms(){ return _allRooms; }

    //public RoomModel getRoomById(int id){ return _repository.getRoomById(id); }
}
