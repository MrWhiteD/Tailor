package com.example.tailor0.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.tailor0.entity.HandbkMerki;
import com.example.tailor0.entity.ProductType;

import java.util.List;

@Dao
public interface HandbkMerkiDao {
    @Query("SELECT * from handbk_merki ORDER BY id ASC")
    LiveData<List<HandbkMerki>> getAllHandbkMerki();

    @Query("SELECT * FROM handbk_merki")
    List<HandbkMerki> getAll();

    @Insert
    void insert(HandbkMerki handbkMerki);

    @Update
    void update(HandbkMerki handbkMerki);

    @Query("Delete from handbk_merki")
    void deleteAll();
}
