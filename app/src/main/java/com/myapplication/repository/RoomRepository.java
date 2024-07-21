package com.myapplication.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.myapplication.database.Database;
import com.myapplication.model.RoomModel;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RoomRepository {

    //private final RoomDao _roomDao;
    private final ExecutorService _executorService;

    public RoomRepository(Application application) {
        Database database = Database.getInstance(application);
        //_roomDao = database.roomDao();
        _executorService = Executors.newFixedThreadPool(2);
    }

    public void insertRoom(RoomModel roomModel)
    {
        //_executorService.execute( () -> _roomDao.insertRoom(roomModel));
    }

    public void updateRoom(RoomModel roomModel)
    {
        //_executorService.execute( () -> _roomDao.updateRoom(roomModel));
    }

    public void deleteRoom(RoomModel roomModel)
    {
        //_executorService.execute( () -> _roomDao.deleteRoom(roomModel));
    }

    //public LiveData<List<RoomModel>> getAllRooms(){ return  _roomDao.getAllRooms(); }

    //public RoomModel getRoomById(int id){ return  _roomDao.getRoomById(id);}
}
