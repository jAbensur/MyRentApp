package com.myapplication.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.myapplication.database.Database;
import com.myapplication.model.Tenant;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TenantRepository {
    private TenantDao tenantDao;
    private LiveData<List<Tenant>> allTenants;
    private ExecutorService executorService;
    public TenantRepository(Application application) {
        Database database = Database.getInstance(application);
        tenantDao = database.tenantDao();
        allTenants = tenantDao.getAllTenants();
        executorService = Executors.newFixedThreadPool(2);
    }
    public void update(Tenant tenant) {
        executorService.execute(() -> tenantDao.update(tenant));
    }
    public void delete(Tenant tenant) {
        executorService.execute(() -> tenantDao.delete(tenant));
    }
    public LiveData<List<Tenant>> getAllTenants() {
        return allTenants;
    }
    public LiveData<List<Tenant>> getTenants(String filter) {
        return tenantDao.getTenants("%" + filter + "%");
    }
    public LiveData<Tenant> getTenantById(int tenantId){
        return tenantDao.getTenantById(tenantId);
    }
    public void insert(Tenant tenant) {
        new insertAsyncTask(tenantDao).execute(tenant);
    }
    private static class insertAsyncTask extends AsyncTask<Tenant, Void, Void> {
        private TenantDao tenantDao;
        private insertAsyncTask(TenantDao tenantDao) {
            this.tenantDao = tenantDao;
        }
        @Override
        protected Void doInBackground(final Tenant... tenants) {
            tenantDao.insert(tenants[0]);
            return null;
        }
    }
}
