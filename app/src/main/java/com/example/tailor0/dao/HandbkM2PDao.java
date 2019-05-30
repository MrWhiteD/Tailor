package com.example.tailor0.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.tailor0.entity.HandbkM2P;
import com.example.tailor0.entity.HandbkMerki;

import java.util.List;

@Dao
public interface HandbkM2PDao {

    @Insert
    void insert(HandbkM2P handbkM2P);

    @Update
    void update(HandbkM2P handbkM2P);

    @Query("Delete from handbk_m2p")
    void deleteAll();
}
