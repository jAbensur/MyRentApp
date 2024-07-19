package com.myapplication.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.myapplication.model.Rent;
import java.util.List;

@Dao
public interface RentDao {
    @Insert
    void insert(Rent rent);
    @Update
    void update(Rent rent);
    @Delete
    void delete(Rent rent);
    @Query("SELECT * FROM rents WHERE state = 1")
    LiveData<List<Rent>> getAllRents();
    @Query("SELECT * FROM rents WHERE id = :rentId")
    LiveData<Rent> getRentById(int rentId);
}
