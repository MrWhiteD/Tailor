package com.example.tailor0.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "DirectoryMerok")
public class DirectoryMerok {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String nameMerki;
    public long productType_id; /*Вид изделия*/
    public String foto; /*Не знаю какой тип*/
    public String description; /*Описание*/
}
