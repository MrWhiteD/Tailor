package com.example.tailor0;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.tailor0.entity.Order;
import com.example.tailor0.entity.ProductType;

import java.util.List;

public class OrdersViewModel extends AndroidViewModel {
    private OrdersRepository ordersRepository;
    private LiveData<List<Order>> mAllOrders;
    public OrdersViewModel(@NonNull Application application) {
        super(application);
            ordersRepository = new OrdersRepository(application);
    }

    public LiveData<List<Order>> getAllOrders() {
        mAllOrders = ordersRepository.getmAllOrder();
        return mAllOrders;
    }

    public void insert (Order order){
        ordersRepository.insert(order);
    }

    public void update (Order order){
        ordersRepository.update(order);
    }
}
