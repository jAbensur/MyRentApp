package com.myapplication.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.myapplication.model.Property;
import java.util.List;

@Dao
public interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProperty(Property propertyModel);

    @Update
    void updateProperty(Property propertyModel);

    @Delete
    void deleteProperty(Property propertyModel);

    @Query("SELECT * FROM properties")
    List<Property> getAllPropertiesFuture();

    @Query("SELECT * FROM properties")
    LiveData<List<Property>> getAllPropertiesLive();

    @Query("SELECT * FROM properties WHERE id=:id")
    Property getPropertyById(int id);


}
