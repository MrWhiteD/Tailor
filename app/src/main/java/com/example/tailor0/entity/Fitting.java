package com.example.tailor0.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Fitting")
public class Fitting {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long order_id;
    public long date;

}
