package com.example.tailor0.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.tailor0.entity.Cust2Merki;
import com.example.tailor0.entity.FullCust2Merki;
import com.example.tailor0.entity.HandbkM2P;

import java.util.List;

@Dao
public interface Cust2MerkiDao {
    @Query("SELECT * FROM cust2merki WHERE cust_id = :custId")
    LiveData<List<Cust2Merki>> getCust2Merki(long custId);

    @Query("select hm.id as merka_id, hm.short_name as merka_short_name, hm.name as merka_name, cm.val as val from handbk_merki hm left join cust2merki cm on hm.id=cm.merka_id and cm.cust_id=:custId")
    LiveData<List<FullCust2Merki>> getFullCust2Merki(long custId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cust2Merki cust2Merki);

    @Update
    void update(Cust2Merki cust2Merki);

    @Query("Delete from cust2merki")
    void deleteAll();
}
