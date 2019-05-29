package com.example.tailor0.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.tailor0.entity.Customer;

import java.util.List;

@Dao
public interface CustomerDao {
    @Query("SELECT * from customer ORDER BY fio ASC")
    LiveData<List<Customer>> getAlphabetizedCust();

    @Query("SELECT * FROM customer")
    List<Customer> getAll();

    @Insert
    void insert(Customer customer);

    @Update
    void update(Customer customer);

    @Query("Delete from customer")
    void deleteAll();
}
