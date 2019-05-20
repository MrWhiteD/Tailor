package com.example.tailor0.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "customer")
public class Customer {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String fio;
    public String phone;
    public String email;
    public String gender;
    public String notes;

    public String getFio() {
        return fio;
    }
}