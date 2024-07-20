package com.myapplication.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.myapplication.database.Database;
import com.myapplication.model.Chamber;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChamberRepository {
    private ChamberDao chamberDao;
    private LiveData<List<Chamber>> allChambers;
    private ExecutorService executorService;
    public ChamberRepository(Application application){
        Database database = Database.getInstance(application);
        chamberDao = database.chamberDao();
        allChambers = chamberDao.getAllChambers();
        executorService = Executors.newFixedThreadPool(2);
    }
    //public void insert(Chamber chamber){executorService.execute(() ->chamberDao.insert(chamber));}
    public void update(Chamber chamber){executorService.execute(() ->chamberDao.update(chamber));}
    public void delete(Chamber chamber){executorService.execute(() ->chamberDao.delete(chamber));}
    public LiveData<List<Chamber>> getAllChambers() {
        return allChambers;
    }
    public LiveData<List<Chamber>> getChambers(String filter) {
        return chamberDao.getChambers("%" + filter + "%");
    }
    public void insert(Chamber chamber) {
        new insertAsyncTask(chamberDao).execute(chamber);
    }
    private static class insertAsyncTask extends AsyncTask<Chamber, Void, Void> {
        private ChamberDao chamberDao;
        private insertAsyncTask(ChamberDao chamberDao) {
            this.chamberDao = chamberDao;
        }
        @Override
        protected Void doInBackground(final Chamber... chambers) {
            chamberDao.insert(chambers[0]);
            return null;
        }
    }
}
