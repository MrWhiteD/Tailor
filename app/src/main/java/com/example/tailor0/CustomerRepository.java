package com.example.tailor0;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.tailor0.dao.CustomerDao;
import com.example.tailor0.entity.Customer;

import java.util.List;

class CustomerRepository {
    private CustomerDao mCustomerDao;
    private LiveData<List<Customer>> mAllCust;

    CustomerRepository(Application application){
        TailorRoomDatabase db = TailorRoomDatabase.getDatabase(application);
        mCustomerDao = db.customerDao();
        mAllCust = mCustomerDao.getAlphabetizedCust();
    }
    LiveData<List<Customer>> getmAllCust(){
        return mAllCust;
    }

    void insert(Customer customer) {
        new insertAsyncTask(mCustomerDao).execute(customer);
    }

    void update(Customer customer) {
        new updateAsyncTask(mCustomerDao).execute(customer);
    }

    private static class insertAsyncTask extends AsyncTask<Customer, Void, Void> {

        private CustomerDao mAsyncTaskDao;

        insertAsyncTask(CustomerDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Customer... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Customer, Void, Void> {

        private CustomerDao mAsyncTaskDao;

        updateAsyncTask(CustomerDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Customer... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
