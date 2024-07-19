package com.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.myapplication.model.PropertyModel;
import com.myapplication.repository.PropertyRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PropertyViewModel extends AndroidViewModel {

    private final PropertyRepository _repository;
    private final LiveData<List<PropertyModel>> _allProperties;

    public PropertyViewModel(@NonNull Application application) {
        super(application);
        _repository = new PropertyRepository(application);
        _allProperties = _repository.getAllPropertiesLive();
    }

    public void insertProperty(PropertyModel property)
    {
        _repository.insertProperty(property);
    }

    public void updateProperty(PropertyModel property)
    {
        _repository.updateProperty(property);
    }

    public void deleteProperty(PropertyModel property)
    {
        _repository.deleteProperty(property);
    }

    public List<PropertyModel> getAllPropertiesFuture() throws ExecutionException, InterruptedException {
        return _repository.getAllPropertiesFuture();
    }

    public LiveData<List<PropertyModel>> getAllPropertiesLive()
    {
        return  _allProperties;
    }

    public PropertyModel getPropertyById(int id)
    {
        return  _repository.getPropertyById(id);
    }
}
