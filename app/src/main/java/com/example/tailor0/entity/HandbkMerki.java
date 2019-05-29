package com.example.tailor0.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "handbk_merki")
public class HandbkMerki {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String short_name;
    public String name;
    public long productType_id; /*Вид изделия*/
    public String foto; /*Не знаю какой тип*/
    public String description; /*Описание*/
}
