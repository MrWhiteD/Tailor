package com.example.tailor0;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewProdType extends AppCompatActivity {
    private Long prodTypeId;
    private EditText etProdTypeName, etProdTypeNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_prodtype);
        Toolbar tbProdType = findViewById(R.id.tbProdTypeCard);

        etProdTypeName = findViewById(R.id.etProdTypeName);
        etProdTypeNotes = findViewById(R.id.etProdTypeNotes);
        prodTypeId = getIntent().getLongExtra("prodTypeId", -1);
        if (prodTypeId != -1){
            tbProdType.setTitle("Изделие " + getIntent().getStringExtra("prodTypeName"));
            prodTypeId = getIntent().getLongExtra("prodTypeId", 0);
            etProdTypeName.setText(getIntent().getStringExtra("prodTypeName"));
            etProdTypeNotes.setText(getIntent().getStringExtra("prodTypeNotes"));
        }

    }

    public void btSaveProdClick(View view) {
        Intent replyIntent = new Intent();
        replyIntent.putExtra("prodTypeId", prodTypeId);
        replyIntent.putExtra("prodTypeName", etProdTypeName.getText().toString());
        replyIntent.putExtra("prodTypeNotes", etProdTypeNotes.getText().toString());

        setResult(RESULT_OK, replyIntent);
        finish();

    }
}
