package com.example.tailor0.entity;

public class FullOrder {
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
    public String fio;
    public String prod_name;
}
