package com.example.tailor0.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.tailor0.entity.Customer;
import com.example.tailor0.entity.ProductType;

import java.util.List;

@Dao
public interface ProductTypeDao {
    @Query("SELECT * from product_type ORDER BY name ASC")
    LiveData<List<ProductType>> getAlphabetizedProd();

    @Query("SELECT * FROM product_type")
    List<ProductType> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductType productType);

    @Update
    void update(ProductType productType);

    @Query("Delete from product_type")
    void deleteAll();
}
