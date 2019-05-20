package com.example.tailor0.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.tailor0.entity.Order;

import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT * from orders where active = 1")
    LiveData<List<Order>> getActiveOrders();

    @Query("SELECT * from orders where active = 0")
    LiveData<List<Order>> getInactiveOrders();

    @Query("DELETE FROM orders")
    void deleteAll();

    @Insert
    void insert(Order order);

    @Update
    void update(Order order);
}
