package com.example.tailor0;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.tailor0.dao.Cust2MerkiDao;
import com.example.tailor0.dao.CustomerDao;
import com.example.tailor0.entity.Cust2Merki;
import com.example.tailor0.entity.Customer;
import com.example.tailor0.entity.FullCust2Merki;

import java.util.List;

class CustomerRepository {
    private CustomerDao mCustomerDao;
    private Cust2MerkiDao mCust2MerkiDao;
    private LiveData<List<Customer>> mAllCust;
    private LiveData<List<FullCust2Merki>> mFullCust2Merki;
    private List<Customer> mAllCust1;

    CustomerRepository(Application application){
        TailorRoomDatabase db = TailorRoomDatabase.getDatabase(application);
        mCustomerDao = db.customerDao();
        mCust2MerkiDao = db.cust2MerkiDao();
    }
    LiveData<List<Customer>> getmAllCust(){
        mAllCust = mCustomerDao.getAlphabetizedCust();
        return mAllCust;
    }

    LiveData<List<FullCust2Merki>> getFullCust2Merki(Long custId){
        mFullCust2Merki = mCust2MerkiDao.getFullCust2Merki(custId);
        return mFullCust2Merki;
    }

    void insert_c2m(Cust2Merki cust2Merki) {
        new insertAsyncTask_c2m(mCust2MerkiDao).execute(cust2Merki);
    }

    void insert(Customer customer) {
        new insertAsyncTask(mCustomerDao).execute(customer);
    }

    void update(Customer customer) {
        new updateAsyncTask(mCustomerDao).execute(customer);
    }

    private static class insertAsyncTask_c2m extends AsyncTask<Cust2Merki, Void, Void> {

        private Cust2MerkiDao mAsyncTaskDao;

        insertAsyncTask_c2m(Cust2MerkiDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Cust2Merki... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
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
