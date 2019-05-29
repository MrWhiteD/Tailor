package com.example.tailor0.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tailor0.OrderActivity;
import com.example.tailor0.R;
import com.example.tailor0.entity.Order;

import java.util.ArrayList;
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
                Intent intent = OrderActivity.newIntent(getContext(), null);
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
        // Вот здесь идет запрос к базе на выборку всех записей Order
        // я сейчас просто заполню его парой объектов
        orders = new ArrayList<>();
        Order o1 = new Order(); o1.id = 1; o1.dateStart = "01.01.2019"; o1.dateEnd = "02.02.2019"; o1.note = "Первый заказ";
        orders.add(o1);
        Order o2 = new Order(); o2.id = 1; o2.dateStart = "03.03.2019"; o2.dateEnd = "04.04.2019"; o2.note = "Второй заказ";
        orders.add(o2);

        // Сообщаем адаптеру, что надо обновить данные
        adapter.notifyDataSetChanged();
    }

    private void handleOnClick(Order order) {
        // А в этом месте передается объект заказа из элемента списка, которого коснулись
        // Соответственно мы здесь можем вызвать активити для редактирования заказа
        Intent intent = OrderActivity.newIntent(getContext(), order.id);
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
    }
}