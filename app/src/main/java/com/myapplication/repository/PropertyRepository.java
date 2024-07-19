package com.myapplication.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.myapplication.database.Database;
import com.myapplication.model.PropertyDao;
import com.myapplication.model.PropertyModel;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PropertyRepository {

    private final PropertyDao _propertyDao;
    private final ExecutorService _executorService;

    public PropertyRepository(Application application)
    {
        Database database = Database.getInstance(application);
        _propertyDao = database.propertyDao();
        _executorService = Executors.newFixedThreadPool(2);
    }

    public void insertProperty(PropertyModel property)
    {
        _executorService.execute( () -> _propertyDao.insertProperty(property));
    }

    public void updateProperty(PropertyModel property)
    {
        _executorService.execute( () -> _propertyDao.updateProperty(property));
    }

    public void deleteProperty(PropertyModel property)
    {
        _executorService.execute( () -> _propertyDao.deleteProperty(property));
    }

    public List<PropertyModel> getAllPropertiesFuture() throws ExecutionException, InterruptedException
    {
        Callable<List<PropertyModel>> callable = new Callable<List<PropertyModel>>() {
            @Override
            public List<PropertyModel> call() throws Exception {
                return _propertyDao.getAllPropertiesFuture();
            }
        };

        Future<List<PropertyModel>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<PropertyModel>> getAllPropertiesLive()
    {
        return _propertyDao.getAllPropertiesLive();
    }

    public PropertyModel getPropertyById(int id)
    {
        return  _propertyDao.getPropertyById(id);
    }
}
