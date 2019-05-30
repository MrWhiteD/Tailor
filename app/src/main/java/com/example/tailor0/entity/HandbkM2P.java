package com.example.tailor0.entity;

//  Таблица для привязки наборов мерок к типам изделий

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "handbk_m2p", primaryKeys = {"prod_id", "merka_id"})
public class HandbkM2P {
    public long prod_id;
    public long merka_id;
}
