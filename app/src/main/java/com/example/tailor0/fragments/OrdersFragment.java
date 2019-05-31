package com.example.tailor0.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tailor0.NewOrder;
import com.example.tailor0.OrdersViewModel;
import com.example.tailor0.R;
import com.example.tailor0.entity.FullOrder;
import com.example.tailor0.entity.Order;

import java.util.Collections;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class OrdersFragment extends Fragment {
    private OrderAdapter adapter;

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private OrdersViewModel ordersViewModel;
    private static final int NEW_ORDER_ACTIVITY_REQUEST_CODE = 1;
    private static final int UPD_ORDER_ACTIVITY_REQUEST_CODE = 2;
    // Это отображаемый список заказов. Вначале он заполняется из базы, а затем отображается в адаптере
//    private List<FullOrder> fullOrders;

    public OrdersFragment() {
        // Required empty public constructor
    }

    public static OrdersFragment newInstance() {
        OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = NewOrder.newIntent(getContext(), null);
                startActivity(intent);
            }
        });
        adapter = new OrderAdapter();

        recyclerView = view.findViewById(R.id.orders_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        ordersViewModel = ViewModelProviders.of(this).get(OrdersViewModel.class);
        ordersViewModel.getFullOrders().observe(this, new Observer<List<FullOrder>>() {
            @Override
            public void onChanged(@Nullable final List<FullOrder> fullOrders) {
//                 Update the cached copy of the words in the adapter.
//                ((MyAdapter) packageTypesAdapter).setCustomer(customers);
                adapter.setOrders(fullOrders);
            }
        });
// Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void handleOnClick(FullOrder fullOrder) {
        // А в этом месте передается объект заказа из элемента списка, которого коснулись
        // Соответственно мы здесь можем вызвать активити для редактирования заказа
        Intent intent = NewOrder.newIntent(getContext(), fullOrder.id);
        intent.putExtra("dateStart", fullOrder.dateStart);
        intent.putExtra("dateFitting", fullOrder.dateFitting);
        intent.putExtra("dateEnd", fullOrder.dateEnd);
        intent.putExtra("note", fullOrder.note);
        intent.putExtra("cust_id", fullOrder.cust_id);
        intent.putExtra("cust_fio", fullOrder.fio);
        intent.putExtra("productType_id", fullOrder.productType_id);
        intent.putExtra("productType_name", fullOrder.prod_name);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == NEW_ORDER_ACTIVITY_REQUEST_CODE || requestCode == UPD_ORDER_ACTIVITY_REQUEST_CODE) && resultCode == RESULT_OK) {
            Order order = new Order();
            order.dateStart = data.getStringExtra("dateStart");
            order.dateFitting = data.getStringExtra("dateFitting");
            order.dateEnd = data.getStringExtra("dateEnd");
            order.note = data.getStringExtra("note");
            order.productType_id = data.getLongExtra("productType_id", 0);

            if (requestCode == NEW_ORDER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
                order.cust_id = data.getLongExtra("cust_id", 0);
                ordersViewModel.insert(order);
            }
            if (requestCode == UPD_ORDER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
                order.id = data.getLongExtra("orderId", 0);
                ordersViewModel.update(order);
            }
        }
    }


    private class OrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        FullOrder fullOrder;

        TextView tvDateStart, tvDateEnd, tvProdType, tvOrderId, tvCustomer;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvCustomer = itemView.findViewById(R.id.tvCustomer);
            tvDateStart = itemView.findViewById(R.id.tvDateStart);
            tvDateEnd = itemView.findViewById(R.id.tvDateEnd);
            tvProdType = itemView.findViewById(R.id.tvProdtype);

        }

        @Override
        public void onClick(View v) {
            handleOnClick(fullOrder);
        }

        public void bind(FullOrder fullOrder){
            this.fullOrder = fullOrder;
            tvOrderId.setText(Long.toString(fullOrder.id));
            tvCustomer.setText(fullOrder.fio);
            tvDateStart.setText(fullOrder.dateStart);
            tvDateEnd.setText(fullOrder.dateEnd);
            tvProdType.setText(fullOrder.prod_name);
        }
    }

    private class OrderAdapter extends RecyclerView.Adapter<OrderHolder>{
        List<FullOrder> mOrders = Collections.emptyList();
        @NonNull
        @Override
        public OrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.order_item_layout, viewGroup, false);
            return new OrderHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderHolder orderHolder, int i) {
            // А здесь напрямую обращаемся к списку заказов в классе фрагмента
            FullOrder fullOrder = mOrders.get(i);
            orderHolder.bind(fullOrder);
        }

        @Override
        public int getItemCount() {
            return mOrders.size();
        }

        public void setOrders(List<FullOrder> fullOrders) {
            mOrders = fullOrders;
            notifyDataSetChanged();
        }
    }
}