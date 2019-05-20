package com.example.tailor0;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

public class NewCustomer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);

        final EditText etFIO = findViewById(R.id.etFio);
        final EditText etPhone = findViewById(R.id.etPhone);
        final EditText etNotes = findViewById(R.id.etNotes);
        final EditText etEmail = findViewById(R.id.etEmail);

        final Button button = findViewById(R.id.btnSaveCust);

        String req_mode = getIntent().getStringExtra("REQUEST_MODE");
        if (req_mode.equals("edit")){
            etFIO.setText(getIntent().getStringExtra("fio"));
            etPhone.setText(getIntent().getStringExtra("phone"));
            etNotes.setText(getIntent().getStringExtra("notes"));
            etEmail.setText(getIntent().getStringExtra("email"));
        }
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                String strFIO = etFIO.getText().toString();
                replyIntent.putExtra("FIO", strFIO);
                String strPhone = etPhone.getText().toString();
                replyIntent.putExtra("Phone", strPhone);
                String strNotes = etNotes.getText().toString();
                replyIntent.putExtra("Notes", strNotes);
                String strEmail = etEmail.getText().toString();
                replyIntent.putExtra("Email", strEmail);

                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }
}
