package com.example.tailor0.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.tailor0.entity.FullOrder;
import com.example.tailor0.entity.Order;

import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT * from orders order by id")
    LiveData<List<Order>> getAllOrders();

    @Query("SELECT * from orders where active = 1")
    LiveData<List<Order>> getActiveOrders();

    @Query("SELECT * from orders where active = 0")
    LiveData<List<Order>> getInactiveOrders();

    @Query("SELECT o.*, c.fio as fio, p.name as prod_name FROM orders o, customer c, product_type p WHERE o.cust_id=c.id and o.productType_id=p.id")
    LiveData<List<FullOrder>> getFullOrders();

    @Query("DELETE FROM orders")
    void deleteAll();

    @Insert
    void insert(Order order);

    @Update
    void update(Order order);
}
