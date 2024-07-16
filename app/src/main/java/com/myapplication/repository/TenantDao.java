package com.myapplication.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.myapplication.model.Tenant;
import java.util.List;

@Dao
public interface TenantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Tenant tenant);
    @Update
    void update(Tenant tenant);
    @Delete
    void delete(Tenant tenant);
    @Query("SELECT * FROM tenants")
    LiveData<List<Tenant>> getAllTenants();
    @Query("SELECT * FROM tenants WHERE name LIKE :filter")
    LiveData<List<Tenant>> getTenants(String filter);
}
