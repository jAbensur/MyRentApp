package com.myapplication.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.myapplication.database.Database;
import com.myapplication.model.Property;
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

    public void insertProperty(Property property)
    {
        _executorService.execute( () -> _propertyDao.insertProperty(property));
    }

    public void updateProperty(Property property)
    {
        _executorService.execute( () -> _propertyDao.updateProperty(property));
    }

    public void deleteProperty(Property property)
    {
        _executorService.execute( () -> _propertyDao.deleteProperty(property));
    }

    public List<Property> getAllPropertiesFuture() throws ExecutionException, InterruptedException
    {
        Callable<List<Property>> callable = new Callable<List<Property>>() {
            @Override
            public List<Property> call() throws Exception {
                return _propertyDao.getAllPropertiesFuture();
            }
        };

        Future<List<Property>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<Property>> getAllPropertiesLive()
    {
        return _propertyDao.getAllPropertiesLive();
    }

    public Property getPropertyById(int id)
    {
        return  _propertyDao.getPropertyById(id);
    }
}
