package com.example.tailor0;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.tailor0.entity.Customer;

import java.util.Calendar;
import java.util.List;

public class NewOrder extends AppCompatActivity {

    private static final String EXTRA_ORDER_ID = "order_id";
    private int DIALOG_DATE = 1;
    private Long orderId;

    EditText etDateStart, etDateFitting, etDateEnd, etNotes;
    TextView tvCustomer;
    private Spinner spinCustomer, spinProdType;
    private  ArrayAdapter packageTypesAdapter;

    Calendar dateAndTime= Calendar.getInstance();

    public static Intent newIntent(Context context, Long orderId){
        Intent i = new Intent(context, NewOrder.class);
        i.putExtra(EXTRA_ORDER_ID, orderId);
        return i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        orderId = getIntent().getLongExtra(EXTRA_ORDER_ID, -1);

        tvCustomer = findViewById(R.id.tvCustomer);
        spinProdType = findViewById(R.id.spinProdType);
        etDateStart = findViewById(R.id.etDateStart);
        etDateFitting = findViewById(R.id.etDateFitting);
        etDateEnd = findViewById(R.id.etDateEnd);
        etNotes = findViewById(R.id.etNotes);

        Toolbar tool = findViewById(R.id.tbOrderCard);
        spinCustomer = findViewById(R.id.spinCustomer);
        final Button btnSaveOrder = findViewById(R.id.btnSaveOrder);
        if(orderId == -1){
            tool.setTitle("Новый заказ");
            findViewById(R.id.tvCustomer).setVisibility(View.GONE);
            findViewById(R.id.flImages).setVisibility(View.GONE);
            CustomerViewModel customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
//        final ArrayAdapter packageTypesAdapter = new ArrayAdapter(this, R.layout.simple_spinner_item);
            packageTypesAdapter = new MyAdapter(this, R.layout.simple_spinner_item);
//        spinCustomer.setAdapter(new MyAdapter(this, R.layout.simple_spinner_item));
            spinCustomer.setAdapter(packageTypesAdapter);
            customerViewModel.getAllCust().observe(this, new Observer<List<Customer>>() {
                @Override
                public void onChanged(@Nullable final List<Customer> customers) {
                    // Update the cached copy of the words in the adapter.
//                ((MyAdapter) packageTypesAdapter).setCustomer(customers);
                    for(int i=0; i<customers.size(); i++) {
                        packageTypesAdapter.add(customers.get(i));
                    }
                }
            });
            packageTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        } else {
            tool.setTitle("Заказ номер " + orderId.toString());
            spinCustomer.setVisibility(View.GONE);
            findViewById(R.id.tvCustomer).setVisibility(View.VISIBLE);
            tvCustomer.setText(getIntent().getStringExtra("cust_fio"));
            etDateStart.setText(getIntent().getStringExtra("dateStart"));
            etDateFitting.setText(getIntent().getStringExtra("dateFitting"));
            etDateEnd.setText(getIntent().getStringExtra("dateEnd"));
            etNotes.setText(getIntent().getStringExtra("note"));
        }


        btnSaveOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave();
//                packageTypesAdapter.getPosition()
/*
*/
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Вот здесь нужно из базы вытащить этот заказ и по нему заполнить поля, если, конечно, это не создание нового заказа
        if(orderId != null){
            // Вытаскиваем и заполняем
        }
    }

    private void onSave() {
        Intent replyIntent = new Intent();

        if(orderId != null){
            replyIntent.putExtra("id", orderId);
        }else {
            SpinnerAdapter spa = spinCustomer.getAdapter();
            int position = spinCustomer.getSelectedItemPosition();
            Customer customer = (Customer) packageTypesAdapter.getItem(position);
            replyIntent.putExtra("cust_id", customer.id);
        }

        replyIntent.putExtra("dateStart", etDateStart.getText().toString());
        replyIntent.putExtra("dateFitting", etDateFitting.getText().toString());
        replyIntent.putExtra("dateEnd", etDateEnd.getText().toString());
        replyIntent.putExtra("note", etNotes.getText().toString());
//        replyIntent.putExtra("productType_id", prodtype.id);

        setResult(RESULT_OK, replyIntent);

        finish();
    }

    public void OnPhotoClick(View view) {
    }

    public void OnDrawClick(View view) {
    }

    public void OnDateClick(final View view) {
        new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker dpview, int year, int month, int dayOfMonth) {
                        if(view.getId() == R.id.etDateStart){
                            etDateStart.setText(dayOfMonth + "." + month + "." + year);
                        } else if(view.getId() == R.id.etDateFitting){
                            etDateFitting.setText(dayOfMonth + "." + month + "." + year);
                        }if(view.getId() == R.id.etDateEnd){
                            etDateEnd.setText(dayOfMonth + "." + month + "." + year);
                        }
                    }
                },
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
        Log.d("sss", dateAndTime.toString());
    }

    // установка начальных даты и времени
    private void setInitialDateTime(View view) {
        etDateStart.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime(view);
        }
    };

    // Creating an Adapter Class
    class MyAdapter extends ArrayAdapter {

//        private List<Customer> mCustomers = Collections.emptyList(); // Cached copy of words

        public MyAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            // Inflating the layout for the custom Spinner
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.simple_spinner_item, parent, false);

            // Declaring and Typecasting the textview in the inflated layout
            TextView tvLanguage = layout .findViewById(R.id.tvLanguage);

            // Setting the text using the array
            tvLanguage.setText(((Customer) this.getItem(position)).fio);
//            Customer current = mCustomers.get(position);
//            tvLanguage.setText(current.fio);

            return layout;
        }

        // It gets a View that displays in the drop down popup the data at the specified position
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        // It gets a View that displays the data at the specified position
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

//        public void setCustomer(List<Customer> customers) {
//            mCustomers = customers;
//            notifyDataSetChanged();
//        }
    }
}
