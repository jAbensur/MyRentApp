package com.myapplication.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.myapplication.model.Tenant;
import com.myapplication.repository.TenantRepository;
import java.util.List;

public class TenantViewModel extends AndroidViewModel {
    private TenantRepository tenantRepository;
    private LiveData<List<Tenant>> allTenants;
    public TenantViewModel(@NonNull Application application) {
        super(application);
        tenantRepository = new TenantRepository(application);
        allTenants = tenantRepository.getAllTenants();//
    }
    public void insert(Tenant tenant) {
        tenantRepository.insert(tenant);
    }
    public void update(Tenant tenant) {
        tenantRepository.update(tenant);
    }
    public void delete(Tenant tenant) {
        tenantRepository.delete(tenant);
    }
    public LiveData<List<Tenant>> getTenants(String filter) {
        return tenantRepository.getTenants(filter);
    }
    public LiveData<List<Tenant>> getAllTenants() {
        return allTenants;
    }
    public LiveData<Tenant> getTenantById(int tenantId){
        return tenantRepository.getTenantById(tenantId);
    }
}