package com.myapplication.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.myapplication.model.RoomModel;

import java.util.List;

public interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRoom(RoomModel roomModel);

    @Update
    void updateRoom(RoomModel roomModel);

    @Delete
    void deleteRoom(RoomModel roomModel);

    @Query("SELECT * FROM room")
    LiveData<List<RoomModel>> getAllRooms();

    @Query("SELECT * FROM room WHERE id=:id")
    RoomModel getRoomById(int id);
}
