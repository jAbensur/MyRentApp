package com.myapplication.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.myapplication.model.Chamber;
import com.myapplication.model.Rent;
import com.myapplication.model.Tenant;

import java.util.List;

@Dao
public interface ChamberDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Chamber chamber);
    @Update
    void update(Chamber chamber);
    @Delete
    void delete(Chamber chamber);
    @Query("SELECT * FROM chambers")
    LiveData<List<Chamber>> getAllChambers();
    @Query("SELECT * FROM chambers WHERE name LIKE :filter")
    LiveData<List<Chamber>> getChambers(String filter);
    @Query("SELECT * FROM chambers WHERE id = :chamberId")
    LiveData<Chamber> getChamberById(int chamberId);
}
