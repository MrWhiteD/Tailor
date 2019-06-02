package com.example.tailor0;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tailor0.entity.Cust2Merki;
import com.example.tailor0.entity.FullCust2Merki;

import java.util.Collections;
import java.util.List;

public class Cust2MerkiActivity extends AppCompatActivity {

    private Cust2MerkiAdapter adapter;
    CustomerViewModel customerViewModel;
    private RecyclerView recyclerView;
    private Long custId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cust2merki_activity);

        custId = getIntent().getLongExtra("custId", -1);
        adapter = new Cust2MerkiAdapter();

        recyclerView = findViewById(R.id.Cust2merki_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        customerViewModel.getFullCust2Merki(custId).observe(this, new Observer<List<FullCust2Merki>>() {
            @Override
            public void onChanged(@Nullable final List<FullCust2Merki> fullCust2Merki) {
//                 Update the cached copy of the words in the adapter.
//                ((MyAdapter) packageTypesAdapter).setCustomer(customers);
                adapter.setProduct(fullCust2Merki);
            }
        });
    }

    public void btnSaveClick(View view) {
        for (int i = 0; i < adapter.mFullCust2Merki.size(); i++) {
            if (adapter.mFullCust2Merki.get(i).val != 0){
                Cust2Merki cust2Merki = new Cust2Merki();
                cust2Merki.cust_id = custId;
                cust2Merki.merka_id = adapter.mFullCust2Merki.get(i).merka_id;
                cust2Merki.val = adapter.mFullCust2Merki.get(i).val;
                customerViewModel.insert_c2m(cust2Merki);
            }
        }

        finish();
    }



    private class Cust2MerkiAdapter extends RecyclerView.Adapter<Cust2MerkiAdapter.Cust2MerkiHolder>{

        List<FullCust2Merki> mFullCust2Merki = Collections.emptyList();
        @NonNull
        @Override
        public Cust2MerkiHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.cust2merki_item, viewGroup, false);
            return new Cust2MerkiHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Cust2MerkiHolder cust2MerkiHolder, int i) {
            // А здесь напрямую обращаемся к списку заказов в классе фрагмента
            FullCust2Merki fullCust2Merki = mFullCust2Merki.get(i);
//            cust2MerkiHolder.bind(fullCust2Merki);
            cust2MerkiHolder.tvMerkaName.setText(fullCust2Merki.merka_id + ". " + fullCust2Merki.merka_name + "(" + fullCust2Merki.merka_short_name + ")");
            if (fullCust2Merki.val != 0)
                cust2MerkiHolder.etVal.setText(Long.toString(fullCust2Merki.val));
            else
                cust2MerkiHolder.etVal.setText("");
        }

        @Override
        public int getItemCount() {
            return mFullCust2Merki.size();
//            return 2;
        }

        public void setProduct(List<FullCust2Merki> fullCust2Merki) {
            mFullCust2Merki = fullCust2Merki;
            notifyDataSetChanged();
        }

        private class Cust2MerkiHolder extends RecyclerView.ViewHolder {

            FullCust2Merki fullCust2Merki;

            private final TextView tvMerkaName;
            private final EditText etVal;
            TextView dateEnd;
            TextView comment;

            public Cust2MerkiHolder(@NonNull View itemView) {
                super(itemView);

                tvMerkaName = itemView.findViewById(R.id.tvMerkaName);
                etVal = itemView.findViewById(R.id.etVal);

                etVal.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

//                        mFullCust2Merki.get(getAdapterPosition()).setEditTextValue(etVal.getText().toString());
                        if (!etVal.getText().toString().equals(""))
                            mFullCust2Merki.get(getAdapterPosition()).val = Long.parseLong(etVal.getText().toString());
                        else
                            mFullCust2Merki.get(getAdapterPosition()).val = 0;

//                                .setEditTextValue(etVal.getText().toString());
//                        mFullCust2Merki.
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
//            tvProdType = itemView.findViewById(R.id.order_comment);

            }

            public void bind(FullCust2Merki fullCust2Merki){
                this.fullCust2Merki = fullCust2Merki;
//            tvMerkaName.setText(Long.toString(fullCust2Merki.merka_name));
                tvMerkaName.setText(fullCust2Merki.merka_id + ". " + fullCust2Merki.merka_name + "(" + fullCust2Merki.merka_short_name + ")");
                if (fullCust2Merki.val != 0){
                    etVal.setText(Long.toString(fullCust2Merki.val));
                }
//            tvDateEnd.setText(order.tvDateEnd);
//            tvProdType.setText(order.note);
            }
        }
    }
}
