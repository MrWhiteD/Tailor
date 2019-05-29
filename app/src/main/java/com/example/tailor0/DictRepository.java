package com.example.tailor0;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.tailor0.dao.HandbkMerkiDao;
import com.example.tailor0.dao.ProductTypeDao;
import com.example.tailor0.entity.HandbkMerki;
import com.example.tailor0.entity.ProductType;

import java.util.List;

class DictRepository {
    private ProductTypeDao mProductTypeDao;
    private HandbkMerkiDao mhandbkMerkiDao;
    private LiveData<List<ProductType>> mAllProd;
    private LiveData<List<HandbkMerki>> mAllMerki;
//        private List<Customer> mAllCust1;

    DictRepository(Application application){
        TailorRoomDatabase db = TailorRoomDatabase.getDatabase(application);
        mProductTypeDao = db.productTypeDao();
        mhandbkMerkiDao = db.handbkMerkiDao();
    }
    LiveData<List<ProductType>> getmAllProd(){
        mAllProd = mProductTypeDao.getAlphabetizedProd();
        return mAllProd;
    }

    LiveData<List<HandbkMerki>> getmAllMerki(){
        mAllMerki = mhandbkMerkiDao.getAllHandbkMerki();
        return mAllMerki;
    }

    void insert(ProductType productType) {
        new insertAsyncTask(mProductTypeDao).execute(productType);
    }

    void update(ProductType productType) {
        new updateAsyncTask(mProductTypeDao).execute(productType);
    }

    private class insertAsyncTask extends AsyncTask<ProductType, Void, Void> {

        private ProductTypeDao mAsyncTaskDao;

        insertAsyncTask(ProductTypeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ProductType... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private class updateAsyncTask extends AsyncTask<ProductType, Void, Void> {

        private ProductTypeDao mAsyncTaskDao;

        updateAsyncTask(ProductTypeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ProductType... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}