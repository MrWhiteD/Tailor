package com.example.tailor0;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class NewProdType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_prodtype);
        Toolbar tbProdType = findViewById(R.id.tbProdTypeCard);
        tbProdType.setTitle("1");
    }

}
