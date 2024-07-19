package com.myapplication.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProperty(PropertyModel propertyModel);

    @Update
    void updateProperty(PropertyModel propertyModel);

    @Delete
    void deleteProperty(PropertyModel propertyModel);

    @Query("SELECT * FROM property")
    List<PropertyModel> getAllPropertiesFuture();

    @Query("SELECT * FROM property")
    LiveData<List<PropertyModel>> getAllPropertiesLive();

    @Query("SELECT * FROM property WHERE id=:id")
    PropertyModel getPropertyById(int id);


}
