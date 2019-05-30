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
import com.example.tailor0.OrderActivity;
import com.example.tailor0.OrdersViewModel;
import com.example.tailor0.R;
import com.example.tailor0.entity.Order;
import com.example.tailor0.entity.ProductType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrdersFragment extends Fragment {
    private OrderAdapter adapter;

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;

    // Это отображаемый список заказов. Вначале он заполняется из базы, а затем отображается в адаптере
    private List<Order> orders;

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

    @Override
    public void onResume() {
        super.onResume();
        OrdersViewModel ordersViewModel = ViewModelProviders.of(this).get(OrdersViewModel.class);
        ordersViewModel.getAllOrders().observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(@Nullable final List<Order> orders) {
//                 Update the cached copy of the words in the adapter.
//                ((MyAdapter) packageTypesAdapter).setCustomer(customers);
                adapter.setOrders(orders);
            }
        });
    }

    private void handleOnClick(Order order) {
        // А в этом месте передается объект заказа из элемента списка, которого коснулись
        // Соответственно мы здесь можем вызвать активити для редактирования заказа
        Intent intent = NewOrder.newIntent(getContext(), order.id);
        startActivity(intent);
    }


    private class OrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Order order;

        TextView dateStart;
        TextView dateEnd;
        TextView comment;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            dateStart = itemView.findViewById(R.id.order_date_start);
            dateEnd = itemView.findViewById(R.id.order_date_end);
            comment = itemView.findViewById(R.id.order_comment);

        }

        @Override
        public void onClick(View v) {

            handleOnClick(order);

        }

        public void bind(Order order){
            this.order = order;
            dateStart.setText(order.dateStart);
            dateEnd.setText(order.dateEnd);
            comment.setText(order.note);
        }
    }

    private class OrderAdapter extends RecyclerView.Adapter<OrderHolder>{
        List<Order> mOrders = Collections.emptyList();
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
            Order order = orders.get(i);
            orderHolder.bind(order);
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        public void setOrders(List<Order> orders) {
            mOrders = orders;
            notifyDataSetChanged();
        }
    }
}