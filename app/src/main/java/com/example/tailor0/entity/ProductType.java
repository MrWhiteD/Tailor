package com.example.tailor0.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "product_type")
public class ProductType {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public String notes;
    public String images;
}
