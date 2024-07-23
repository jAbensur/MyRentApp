package com.myapplication.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.myapplication.model.Room;

import java.util.List;

@Dao
public interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRoom(Room room);

    @Update
    void updateRoom(Room room);

    @Delete
    void deleteRoom(Room room);

    @Query("SELECT * FROM room")
    LiveData<List<Room>> getAllRooms();
    @Query("SELECT * FROM room WHERE r_state = 1")
    LiveData<List<Room>> getAllAvailableRooms();
    @Query("SELECT * FROM room WHERE r_name LIKE :filter")
    LiveData<List<Room>> getRooms(String filter);

    @Query("SELECT * FROM room WHERE id=:id")
    Room getRoomById(int id);

}
