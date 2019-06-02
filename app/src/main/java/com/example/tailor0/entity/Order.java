package com.example.tailor0.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "orders")
public class Order {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long cust_id;    /*id клиента*/
    public String dateStart; /*Дата приема*/
    public String dateEnd; /*Дата сдачи*/
    public String dateFitting; /*Дата примерки*/
    public int cost;
    public int costprice;  /*себестоимость*/
    public String note; /*примечание*/
    public long productType_id; /*Вид изделия*/
    public String materials;
    public boolean active;
    public String foto; /*Не знаю какой тип*/
}
