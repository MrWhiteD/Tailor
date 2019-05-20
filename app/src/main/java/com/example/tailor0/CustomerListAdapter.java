package com.example.tailor0;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tailor0.entity.Customer;

import java.util.Collections;
import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerViewHolder> {
//    private final View.OnClickListener mOnClickListener = new MyOnClickListener();
    private final LayoutInflater mInflater;
    private List<Customer> mCustomers = Collections.emptyList(); // Cached copy of words

    public CustomerListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        private final TextView customerName;
        private final TextView customerEmail;
        private final TextView customerPhone;

        private CustomerViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            customerName = itemView.findViewById(R.id.tvName);
            customerEmail = itemView.findViewById(R.id.tvEmail);
            customerPhone = itemView.findViewById(R.id.tvPhone);
        }

        @Override
        public void onClick(View view) {
            TextView itm = view.findViewById(R.id.tvName);
            Toast.makeText(view.getContext(), itm.getText(), Toast.LENGTH_LONG).show();
            Customer current = mCustomers.get(getLayoutPosition());
            Toast.makeText(view.getContext(), current.phone, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(view.getContext(), NewCustomer.class);
            intent.putExtra("REQUEST_MODE", "edit");
            intent.putExtra("fio", current.fio);
            intent.putExtra("phone", current.phone);
            intent.putExtra("notes", current.notes);
            intent.putExtra("email", current.email);
            ((FragmentActivity) view.getContext()).startActivityForResult(intent, 2);
        }
    }

    @NonNull
    @Override
    public CustomerListAdapter.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, viewGroup, false);
        return new CustomerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerListAdapter.CustomerViewHolder customerViewHolder, int position) {
        Customer current = mCustomers.get(position);
        customerViewHolder.customerName.setText(current.getFio());
        customerViewHolder.customerEmail.setText(current.email);
        customerViewHolder.customerPhone.setText(current.phone);
    }

    public void setCustomer(List<Customer> customers) {
        mCustomers = customers;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCustomers.size();
    }
}
