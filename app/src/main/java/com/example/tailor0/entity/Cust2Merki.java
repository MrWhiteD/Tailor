package com.example.tailor0.entity;

import android.arch.persistence.room.Entity;

@Entity(tableName = "cust2merki", primaryKeys = {"cust_id", "merka_id"})
public class Cust2Merki {
    public long cust_id;
    public long merka_id;
    public long val;
}