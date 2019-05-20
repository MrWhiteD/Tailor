package com.example.tailor0.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ProductType")
public class ProductType {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String productType;

}
