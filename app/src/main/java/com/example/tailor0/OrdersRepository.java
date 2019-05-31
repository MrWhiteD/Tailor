package com.example.tailor0;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.tailor0.dao.CustomerDao;
import com.example.tailor0.dao.OrderDao;
import com.example.tailor0.entity.Customer;
import com.example.tailor0.entity.FullOrder;
import com.example.tailor0.entity.Order;

import java.util.List;

class OrdersRepository {
    private OrderDao mOrderDao;
    private LiveData<List<Order>> mAllOrders;
    private LiveData<List<FullOrder>> mFullOrders;

    OrdersRepository(Application application){
        TailorRoomDatabase db = TailorRoomDatabase.getDatabase(application);
        mOrderDao = db.orderDao();
    }
    LiveData<List<Order>> getmAllOrder(){
        mAllOrders = mOrderDao.getAllOrders();
        return mAllOrders;
    }

    LiveData<List<FullOrder>> getFullOrder(){
        mFullOrders = mOrderDao.getFullOrders();
        return mFullOrders;
    }

    void insert(Order order) {
        new insertAsyncTask(mOrderDao).execute(order);
    }

    void update(Order order) {
        new updateAsyncTask(mOrderDao).execute(order);
    }

    private static class insertAsyncTask extends AsyncTask<Order, Void, Void> {

        private OrderDao mAsyncTaskDao;

        insertAsyncTask(OrderDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Order... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Order, Void, Void> {

        private OrderDao mAsyncTaskDao;

        updateAsyncTask(OrderDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Order... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
