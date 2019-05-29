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

import com.example.tailor0.OrderActivity;
import com.example.tailor0.ProductViewModel;
import com.example.tailor0.R;
import com.example.tailor0.entity.ProductType;

import java.util.Collections;
import java.util.List;

public class HandbkProdFragment extends Fragment {
    private HandbkAdapter adapter;
    private String mHandbkType; //Параметр - тип справочника

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;

    // Это отображаемый список заказов. Вначале он заполняется из базы, а затем отображается в адаптере
    private List<ProductType> productTypes;

    public HandbkProdFragment() {
        // Required empty public constructor
    }

    public static HandbkProdFragment newInstance(String arg) {
        HandbkProdFragment fragment = new HandbkProdFragment();
        Bundle args = new Bundle();
        args.putString("handbType", arg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHandbkType = getArguments().getString("handbType", "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_handbk, container, false);

        if (getArguments() != null) {
        }

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = OrderActivity.newIntent(getContext(), null);
                startActivity(intent);
            }
        });
        adapter = new HandbkAdapter();

        recyclerView = view.findViewById(R.id.handbk_recycler);
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

        ProductViewModel productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
//        final ArrayAdapter packageTypesAdapter = new NewOrder.MyAdapter(this, R.layout.simple_spinner_item);
//        spinner.setAdapter(new MyAdapter(this, R.layout.simple_spinner_item));
//        productTypes = productViewModel.getAllProd();
        productViewModel.getAllProd().observe(this, new Observer<List<ProductType>>() {
            @Override
            public void onChanged(@Nullable final List<ProductType> productTps) {
//                 Update the cached copy of the words in the adapter.
//                ((MyAdapter) packageTypesAdapter).setCustomer(customers);
                adapter.setProduct(productTps);
            }
        });
        // Вот здесь идет запрос к базе на выборку всех записей Order
        // я сейчас просто заполню его парой объектов
//        orders = new ArrayList<>();
//        Order o1 = new Order(); o1.id = 1; o1.dateStart = "01.01.2019"; o1.dateEnd = "02.02.2019"; o1.note = "Первый заказ";
//        orders.add(o1);
//        Order o2 = new Order(); o2.id = 1; o2.dateStart = "03.03.2019"; o2.dateEnd = "04.04.2019"; o2.note = "Второй заказ";
//        orders.add(o2);

        // Сообщаем адаптеру, что надо обновить данные
        adapter.notifyDataSetChanged();
    }

    private void handleOnClick(ProductType productType) {
        // А в этом месте передается объект заказа из элемента списка, которого коснулись
        // Соответственно мы здесь можем вызвать активити для редактирования заказа
        Intent intent = OrderActivity.newIntent(getContext(), productType.id);
        startActivity(intent);
    }


    private class HandbkHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ProductType productType;

        TextView dateStart;
        TextView dateEnd;
        TextView comment;

        public HandbkHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            dateStart = itemView.findViewById(R.id.dictName);
//            dateEnd = itemView.findViewById(R.id.order_date_end);
//            comment = itemView.findViewById(R.id.order_comment);

        }

        @Override
        public void onClick(View v) {

            handleOnClick(productType);

        }

        public void bind(ProductType productType){
            this.productType = productType;
            dateStart.setText(productType.name);
//            dateEnd.setText(order.dateEnd);
//            comment.setText(order.note);
        }
    }

    private class HandbkAdapter extends RecyclerView.Adapter<HandbkHolder>{

        List<ProductType> mproductTypes = Collections.emptyList();
        @NonNull
        @Override
        public HandbkHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.handbk_item_layout, viewGroup, false);
            return new HandbkHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HandbkHolder handbkHolder, int i) {
            // А здесь напрямую обращаемся к списку заказов в классе фрагмента
            ProductType productType = mproductTypes.get(i);
            handbkHolder.bind(productType);
        }

        @Override
        public int getItemCount() {
            return mproductTypes.size();
//            return 2;
        }

        public void setProduct(List<ProductType> productTypes) {
            mproductTypes = productTypes;
            notifyDataSetChanged();
        }
    }

}