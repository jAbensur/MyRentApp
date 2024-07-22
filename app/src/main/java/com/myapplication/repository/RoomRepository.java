package com.myapplication.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.myapplication.database.Database;
import com.myapplication.model.Room;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RoomRepository {

    private final RoomDao _roomDao;
    private final ExecutorService _executorService;

    public RoomRepository(Application application) {
        Database database = Database.getInstance(application);
        _roomDao = database.roomDao();
        _executorService = Executors.newSingleThreadExecutor();
    }

    public void insertRoom(Room room)
    {
        _executorService.execute( () -> _roomDao.insertRoom(room));
    }

    public void updateRoom(Room room)
    {
        _executorService.execute( () -> _roomDao.updateRoom(room));
    }

    public void deleteRoom(Room room)
    {
        _executorService.execute( () -> _roomDao.deleteRoom(room));
    }

    public LiveData<List<Room>> getAllRooms(){ return  _roomDao.getAllRooms(); }

    public LiveData<List<Room>> getRooms(String filter) {
        return _roomDao.getRooms("%" + filter + "%");
    }

    public LiveData<Room> getRoomById(int id) {
        final MutableLiveData<Room> roomData = new MutableLiveData<>();
        _executorService.execute(() -> {
            Room room = _roomDao.getRoomById(id);
            roomData.postValue(room);
        });
        return roomData;
    }
}
