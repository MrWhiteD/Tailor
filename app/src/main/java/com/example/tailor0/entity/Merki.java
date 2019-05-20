package com.example.tailor0.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Merki")
public class Merki {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long empl_id;
    public long date;
    public long merka_id;
    public long merka_val; /*Не знаю какой тип*/
}
